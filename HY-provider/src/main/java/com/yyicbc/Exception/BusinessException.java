package com.yyicbc.Exception;

import com.yyicbc.utils.EnumCodeMessage;

public class BusinessException extends RuntimeException  {


    /**
     *
     * 自定义业务异常
     * @auther stv
     * 可以按照业务需求定制不同的异常类型
     */
    private static final long serialVersionUID = 1;

    public BusinessException(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }


}
