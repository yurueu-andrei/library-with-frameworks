package by.library.yurueu.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingRepositoryExceptionsAspect {
    @AfterThrowing(
            pointcut = "execution(* by.library.yurueu.repository.*.*(..))",
            throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        String message = "Exception in {} of {}({}) with cause = {}";
        String method = joinPoint.getSignature().getDeclaringTypeName();
        String className = findClassName(joinPoint.getSignature().getDeclaringTypeName());
        String arguments = getArguments(joinPoint);
        String exceptionMessage = exception.getCause().getMessage();

        log.error(message, method, className, arguments, exceptionMessage);
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