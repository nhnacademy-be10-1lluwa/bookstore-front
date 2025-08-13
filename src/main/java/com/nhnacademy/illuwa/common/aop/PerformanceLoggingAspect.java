package com.nhnacademy.illuwa.common.aop;


import com.nhnacademy.illuwa.common.aop.annotation.PerformanceLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceLoggingAspect {

    @Around("@annotation(performanceLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, PerformanceLog performanceLog) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;
        String methodName = joinPoint.getSignature().toShortString();

        log.info("[수행시간측정] {} took {} ms", methodName, duration);

        return result;
    }
}