package com.fsoft.erp.common.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsoft.erp.common.annotation.LogAllocation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class AllocationLogAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("@annotation(logAllocation)")
    public Object logAllocationOperation(ProceedingJoinPoint joinPoint, LogAllocation logAllocation) throws Throwable {
        String operation = logAllocation.operation();
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        // Log entry
        log.info("[ALLOCATION_AUDIT] >>> Operation={} | Method={} | Params={}",
                operation, methodName, serializeArgs(args));

        Instant start = Instant.now();
        try {
            Object result = joinPoint.proceed();
            long duration = Instant.now().toEpochMilli() - start.toEpochMilli();

            // Log success
            log.info("[ALLOCATION_AUDIT] <<< Operation={} | Method={} | Status=SUCCESS | Duration={}ms | Result={}",
                    operation, methodName, duration, serializeResult(result));

            return result;
        } catch (Throwable ex) {
            long duration = Instant.now().toEpochMilli() - start.toEpochMilli();

            // Log failure
            log.error("[ALLOCATION_AUDIT] <<< Operation={} | Method={} | Status=FAILED | Duration={}ms | Error={}",
                    operation, methodName, duration, ex.getMessage(), ex);

            throw ex;
        }
    }

    private String serializeArgs(Object[] args) {
        try {
            return objectMapper.writeValueAsString(args);
        } catch (Exception e) {
            return Arrays.toString(args);
        }
    }

    private String serializeResult(Object result) {
        try {
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            return String.valueOf(result);
        }
    }
}
