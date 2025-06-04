package org.example.learningsystem.core.logging;

import org.example.learningsystem.core.exception.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Logs all handled exceptions after returning from the {@link GlobalExceptionHandler} methods.
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * org.example.learningsystem.core.exception.handler.*.*(..))")
    public void handlerPointcut() {
    }

    @AfterReturning(value = "handlerPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.error("Error: {}", result);
    }
}
