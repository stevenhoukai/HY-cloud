package com.yyicbc.beans;

/**
 * 状态码实体类
 */
public class StatusCode {

    public static final int OK = 20000;//成功

    public static final int ERROR = 20001;//失败

    public static final int LOGINERROR = 20002;//没有该用户

    public static final int PWDERROR = 20003;//密码错误

    public static final int ACCESSERROR = 20004;//权限不足

    public static final int REMOTEERROR = 20005;//远程调用失败

    public static final int REPERROR = 20006;//重复操作

    public static final int EXCEPTIONERROR = 20007;//发生异常

    public static final int TOKENEXPIRE = 20008;//token过期请重新登录

    public static final int USERONLINE = 20009;//用户已在线

    public static final int VIOLATEUNIQUE = 20010;//编码违反唯一性

    public static final int TEMPLATE_WORKING = 30001;//已引用模板不可删除

    public static final int COMPANY_ENCODE_IN_WORKING = 30002;//已引用公司档案不可删除




}