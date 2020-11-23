package com.yyicbc.utils;

/**
 * 错误类型枚举
 * @author stv
 *
 */
public enum EnumCodeMessage {
    SUCCESS(0, "成功!"),
    FAIL(-1, "失败!"),
    EXCEPTION(-2, "服务器异常，请稍后再试"),
    DATEFORMATERR(1000,"日期格式异常"),
    MISSINGSERVLETREQUESTPARAMETER(-3,"参数缺失"),
    ERR(-4,"业务异常");

    private final int code;

    private final String message;

    private EnumCodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
