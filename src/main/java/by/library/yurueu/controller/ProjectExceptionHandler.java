package by.library.yurueu.controller;

import by.library.yurueu.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@ControllerAdvice
public class ProjectExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiCallError handleExceptionServerError(
            HttpServletRequest request,
            Exception ex
    ) {
        log.error("handleServiceExceptionServerError {}\n", request.getRequestURI(), ex);
        return new ApiCallError(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiCallError handleAccessDeniedException(
            HttpServletRequest request,
            AccessDeniedException ex
    ) {
        log.error("handleAccessDeniedException {}\n", request.getRequestURI(), ex);
        return new ApiCallError("Access denied!");
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiCallError handleUnauthorizedException(
            HttpServletRequest request,
            BadCredentialsException ex
    ) {
        log.error("handleUnauthorizedException {}\n", request.getRequestURI(), ex);
        return new ApiCallError("Incorrect username or password!");
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiCallDetailedError<?> handleServiceExceptionServerError(
            HttpServletRequest request,
            ServiceException ex
    ) {
        log.error("handleServiceExceptionServerError {}\n", request.getRequestURI(), ex);
        List<Map<String, String>> details = new ArrayList<>();
        Map<String, String> detail = new HashMap<>();
        detail.put("info", ex.getMessage());
        details.add(detail);
        return new ApiCallDetailedError<>("Parameters is not valid", details);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiCallDetailedError<T> {
        private String message;
        private List<T> details;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiCallError {
        private String message;
    }
}