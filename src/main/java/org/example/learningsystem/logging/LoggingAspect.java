package org.example.learningsystem.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * org.example.learningsystem.exception.handler.*.*(..))")
    public void handlerPointcut() {}

    @AfterReturning(value = "handlerPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.error("Error: {}", result);
    }
}
