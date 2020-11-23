package com.yyicbc.aspect;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

/**
 * @author stv
 * Service通用日志切面类
 */

@Slf4j
@Aspect
@Component
public class ServiceLogAspect {

    private Gson gson = new Gson();

    @Around("within(com.yyicbc.service.impl..*)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 目标类的名称
        String className = joinPoint.getTarget().getClass().getName();
        // 方法名称
        String methodName = joinPoint.getSignature().getName();
        // 参数列表
        Parameter[] parameters = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameters();
        Object[] args = joinPoint.getArgs();

        StringBuilder params = new StringBuilder("[");
        for (int i = 0; i < parameters.length; i++) {
            params.append(parameters[i].getName()).append(":").append(args[i]).append("; ");
        }
        if (parameters.length > 0) {
            int length = params.length();
            params.delete(length - 2, length);
        }
        params.append("]");
        log.info("className={},methodName={},params={}",className,methodName,params);
        Object result = null;
        try {
            // 执行方法
            result = joinPoint.proceed();
        } catch (Exception e) {
            // 记录异常日志
//            log.error(e.getMessage());
            log.error("className={},methodName={},exception={}",className, methodName, e.getMessage());
            throw e;
        }
        // 记录方法结果
        log.info("className={},methodName={},results={}",className, methodName, gson.toJson(result));
        return result;
    }

    
}