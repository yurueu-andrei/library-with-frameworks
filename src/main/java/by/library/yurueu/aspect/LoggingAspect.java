package by.library.yurueu.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..))")
    public void repositoriesMethods() {}

    @Pointcut("execution(public * org.springframework.data.repository.Repository+.findAll(..))")
    public void findAllMethod() {}

    @Pointcut("execution(public * org.springframework.data.repository.Repository+.findById(..))")
    public void findByIdMethod() {}

    @AfterThrowing(
            pointcut = "repositoriesMethods()",
            throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Exception e) {
        log.error("Exception in {}.{}() with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getCause().getMessage());
    }

    @AfterReturning(
            pointcut = "findAllMethod() || findByIdMethod()",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        log.info("Result of executing method {}() in {} is {}",
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringTypeName()
                        .substring(joinPoint.getSignature().getDeclaringTypeName().lastIndexOf(".") + 1),
                result);
    }
}