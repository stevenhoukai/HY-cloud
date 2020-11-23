package com.yyicbc.aspect;

import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import io.jsonwebtoken.ExpiredJwtException;
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
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RetData handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return RetData.build(StatusCode.ERROR, e.getMessage());
    }

    /**
     * 处理自定义业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RetData handleTextException(BusinessException e) {
        LOGGER.error(e.getMsg(), e);
        return RetData.build(e.getCode(), e.getMsg());
    }

    /**
     * 处理自定义业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(com.yyicbc.Exception.BusinessException.class)
    @ResponseBody
    public RetData handleTextException(com.yyicbc.Exception.BusinessException e) {
        LOGGER.error(e.getMsg(), e);
        return RetData.build(e.getCode(), e.getMsg());
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
     * jwt过期
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public RetData handleExpiredJwtException(ExpiredJwtException e) {
        LOGGER.error(e.getMessage(), e);
        return RetData.build(StatusCode.TOKENEXPIRE, "token已过期，请重新登录");
    }

}
