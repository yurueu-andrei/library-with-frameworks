package by.library.yurueu.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private static final String USER_ID_PATTERN = "(\"id\": )(\\d+)";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        if (!request.getRequestURI().equals("/users")
                || request.getRequestURI().equals("/users")
                && !request.getMethod().equals("PUT")) {
            chain.doFilter(request, response);
            return;
        }

        CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                new CachedBodyHttpServletRequest(request);

        final String header = cachedBodyHttpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        final String token = header.split(" ")[1].trim();
        Long tokenUserId = Long.parseLong(jwtTokenUtil.getUserId(token));

        String requestBodyJson = cachedBodyHttpServletRequest.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        Long id = parseId(requestBodyJson);

        if (tokenUserId.equals(id)) {
            chain.doFilter(cachedBodyHttpServletRequest, response);
            return;
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Access is denied!");
    }

    public Long parseId(String requestBodyJson) {
        Pattern pattern = Pattern.compile(USER_ID_PATTERN);
        Matcher matcher = pattern.matcher(requestBodyJson);
        if (matcher.find()) {
            String result = matcher.group(2);
            return Long.parseLong(result);
        }
        return -1L;
    }
}