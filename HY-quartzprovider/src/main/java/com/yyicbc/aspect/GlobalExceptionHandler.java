package com.yyicbc.aspect;

import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 *
 * @author stv
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SchedulerException.class)
    @ResponseBody
    public RetData handleException(SchedulerException e) {
        LOGGER.error(e.getMessage(), e);
        return RetData.build(StatusCode.ERROR, e.getMessage());
    }

    /**
     * 处理所有业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RetData handleException(BusinessException e) {
        LOGGER.error(e.getMessage(), e);
        return RetData.build(e.getCode(), e.getMsg());
    }


    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RetData handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return RetData.build(StatusCode.ERROR, e.getMessage());
    }


    /**
     * 编码重复异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public RetData handleTextException(DataIntegrityViolationException e) {
        LOGGER.error(e.getMessage(), e);
        return RetData.build(StatusCode.VIOLATEUNIQUE, "编码不能重复");
    }

    /**
     * 请求参数缺失异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public RetData handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        LOGGER.error(e.getMessage(), e);
        return RetData.build(StatusCode.EXCEPTIONERROR, e.getMessage());
    }



    /**
     * job类 not found exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ClassNotFoundException.class)
    @ResponseBody
    public RetData handleMissingServletRequestParameterException(ClassNotFoundException e) {
        LOGGER.error(e.getMessage(), e);
        return RetData.build(StatusCode.EXCEPTIONERROR, "ClassNotFoundException : "+e.getMessage());
    }
}
