package com.yyicbc.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果封装
 * @author ste
 *
 */
public class JsonResult {

    public final static int SUCCESS = 0;//0,成功;其他为失败
    public final static Map<String,Object> EMPTY = new HashMap<>();
    private int status = SUCCESS;//默认为成功
    private String msg = "";
    private Object data;

    /**
     * 封装一个正常的返回对象.
     *
     * @param resultData result object
     * @return {@link JsonResult}
     */
    public static JsonResult build(Object resultData) {
        JsonResult result = new JsonResult();
        result.setData(resultData);
        return result;
    }

    /**
     * 封装返回一个指定的对象. btw:Data对象为空字符串
     *
     * @param status int value of return code
     * @param msg     String value of message
     * @return {@link JsonResult}
     */
    public static JsonResult build(int status, String msg) {
        return build(status, msg, EMPTY);
    }

    /**
     * 返回通用对象.
     *
     * @param status    返回码
     * @param msg        返回信息
     * @param resultData 对象
     * @return {@link JsonResult}
     */
    public static JsonResult build(int status, String msg, Object resultData) {
        JsonResult result = new JsonResult();
        result.setStatus(status);
        result.setMsg(msg);
        result.setData(resultData);
        return result;
    }

    /**
     * 封装返回一个指定的枚举类型
     *
     * @param codeMessage
     * @return {@link JsonResult}
     */
    public static JsonResult build(EnumCodeMessage codeMessage) {
        return build(codeMessage.getCode(), codeMessage.getMessage(), EMPTY);
    }

    /**
     * 封装返回一个指定的枚举类型和数据类型
     *
     * @param codeMessage
     * @param resultData
     * @return {@link JsonResult}
     */
    public static JsonResult build(EnumCodeMessage codeMessage, Object resultData) {
        return build(codeMessage.getCode(), codeMessage.getMessage(), resultData);
    }

    /**
     * 封装返回一个成功的对象.
     */
    public static JsonResult buildSuccess() {
        return JsonResult.build(SUCCESS, "successed");
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
