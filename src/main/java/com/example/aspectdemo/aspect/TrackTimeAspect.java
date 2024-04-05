package com.example.aspectdemo.aspect;

import com.example.aspectdemo.model.type.MethodExecutionType;
import com.example.aspectdemo.service.TrackerService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@Slf4j
public class TrackTimeAspect {

    private final TrackerService trackerService;

    public TrackTimeAspect(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @Pointcut(value = "@annotation(com.example.aspectdemo.annotation.TrackTime)")
    public void trackSyncTimeMethods() {}

    @Around(value = "trackSyncTimeMethods()")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        String methodSignature = joinPoint.getSignature().toShortString();
        trackerService.save(methodSignature, timeTaken, MethodExecutionType.SYNC);
        log.info("Method {} executed synchronously in {} ms", methodSignature, timeTaken);
        return result;
    }

    @Pointcut(value = "@annotation(com.example.aspectdemo.annotation.TrackTimeAsync)")
    public void trackAsyncTimeMethods() {}

    @Around(value = "trackAsyncTimeMethods()")
    public Object trackAsyncTime(ProceedingJoinPoint joinPoint) {
        CompletableFuture<Object> future = new CompletableFuture<>();
        long startTime = System.currentTimeMillis();
        CompletableFuture.runAsync(() -> {
            try {
                Object result = joinPoint.proceed();
                future.complete(result);
            } catch (Throwable e) {
                future.completeExceptionally(e);
            }
        });
        long timeTaken = System.currentTimeMillis() - startTime;
        String methodSignature = joinPoint.getSignature().toShortString();
        trackerService.save(methodSignature, timeTaken, MethodExecutionType.ASYNC);
        log.info("Method {} executed asynchronously in {} ms", methodSignature, timeTaken);
        return future;
    }
}