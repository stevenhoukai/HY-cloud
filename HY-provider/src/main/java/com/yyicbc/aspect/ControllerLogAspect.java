package com.yyicbc.aspect;

import com.google.gson.Gson;
import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.service.impl.SysLogServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

/**
 * @author stv
 * controller通用日志切面类
 */

@Slf4j
@Aspect
@Component
public class ControllerLogAspect {

    @Autowired
    SysLogServiceImpl sysLogServiceImpl;

    private Gson gson = new Gson();

    @Around("within(com.yyicbc.controller..*)")
    public Object around(ProceedingJoinPoint joinPoint
                         ) throws Throwable {
        //获取request的相关信息
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
//        String url = request.getRequestURL().toString();
//        String method = request.getMethod();
        String uri = request.getRequestURI();
//        String queryString = request.getQueryString();
        String userInfo = request.getHeader("UserInfo");

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
            sysLogServiceImpl.addNewRequiredSysLog(uri,userInfo,params);

        } catch (Exception e) {
            // 记录异常日志
//            log.error(e.getMessage());
            //add by fmm
            String msg = e.getMessage();
            if( e instanceof BusinessException){
                msg = ((BusinessException) e).getMsg();
            }else if( e instanceof com.yyicbc.Exception.BusinessException){
                msg = (( com.yyicbc.Exception.BusinessException) e).getMsg();
            }
            log.error("className={},methodName={},exception={}",className, methodName, msg);
            throw e;
        }
        // 记录方法结果
        log.info("className={},methodName={},results={}",className, methodName, gson.toJson(result));
        return result;
    }


}