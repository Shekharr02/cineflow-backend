package com.cineflow.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(com.cineflow.controller..*)")
    public void controllerPointcut(){

    }

    @Pointcut("within(com.cineflow.service..*)")
    public void servicePointcut(){

    }

    @Around("controllerPointcut() || servicePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArguments = joinPoint.getArgs();

        if(log.isDebugEnabled()){
            log.debug("ENTER: {}.{}() with argument[s] = {}", className, methodName, Arrays.toString(methodArguments));
        }

        long startTime = System.currentTimeMillis();

        try{
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - startTime;
            if(log.isDebugEnabled()){
                log.debug("EXIT: {}.{}() with result = {}, Execution Time: {} ms", className, methodName, result, elapsedTime);
            }
            return result;
        }
        catch (IllegalArgumentException e){
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(methodArguments), className, methodName);
            throw e;
        }
    }
}
