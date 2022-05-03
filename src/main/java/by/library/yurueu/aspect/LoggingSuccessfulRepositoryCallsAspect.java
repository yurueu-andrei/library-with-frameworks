package by.library.yurueu.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingSuccessfulRepositoryCallsAspect {
    @AfterReturning(
            pointcut = "execution(public * by.library.yurueu.repository.*.find*(..)) || " +
                    "execution(public * by.library.yurueu.repository.*.get*(..))",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String message = "Result of executing method {}({}) in {} is {}";
        String method = joinPoint.getSignature().getName();
        String arguments = getArguments(joinPoint);
        String className = findClassName(joinPoint.getSignature().getDeclaringTypeName());
        String resultToString = result == null ? null : result.toString();

        log.info(message, method, arguments, className, resultToString);
    }

    private String getArguments(JoinPoint joinPoint) {
        String arguments = Arrays.toString(joinPoint.getArgs());
        arguments = arguments.substring(1, arguments.length()-1);
        return arguments;
    }

    private String findClassName(String pass) {
        return pass.substring(pass.lastIndexOf(".") + 1);
    }
}