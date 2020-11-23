package com.yyicbc.aspect;


import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 *
 * @author stv
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理所有不可知的异常,返回异常信息
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RetData handleException(Exception e) {
        log.error(e.getMessage(), e);
        return RetData.build(StatusCode.ERROR, e.getMessage());
    }


    /**
     * 处理业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RetData handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return RetData.build(StatusCode.ERROR, e.getMsg());
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
        log.error(e.getMessage(), e);
        return RetData.build(StatusCode.EXCEPTIONERROR, e.getMessage());
    }


}
