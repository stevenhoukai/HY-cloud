package com.yyicbc.beans.enums;

public enum StatusEnums {
    SUCCESS(20000, "成功"),
    ERROR(20001, "失败"),
    LOGIN_ERROR(20002, "没有该用户"),
    PWD_ERROR(20003, "密码错误"),
    ACCESS_ERROR(20004, "权限不足"),
    REMOTE_ERROR(20005, "远程调用失败"),
    REP_ERROR(20006, "重复操作"),
    EXCEPTION_ERROR(20007, "发生异常"),
    TOKEN_EXPIRE(20008, "token过期请重新登录"),
    USER_ON_LINE(20009, "用户已在线"),
    VIOLATE_UNIQUE(20010, "编码违反唯一性"),
    COMPANYCODEUNIQUE(20011, "公司代码已存在，请重新输入"),
    RUNBATCH_SUCCESS(20012, "跑批成功，不能重传数据"),
    ID_NOT_NULL(20013, "ID不能为空"),
    COMPENSATION_NOT_DELETE(20014, "跑批成功,不能删除数据"),
    COMPANYCODENOTEXIST(20015, "账号对应的公司代码不存在"),
    DATADELETE(20016, "审核后的数据不允许删除！"),
    FOVAUPDATEDATA_ERROR(20017, "更新Fova数据失败"),
    FOVA_ACCOUNT_REPEAT(20017, "fova账号重复"),
    FOVA_BILLNUMBER_REPEAT(20017, "账单编号重复"),
    SAVE_FAIL(20018, "文件保存到服务器失败"),
    DATABASE_FAIL(20019, "数据保存到服务器失败"),
    NO_TEMPLATE(20020, "编码对应的模板不存在，请确认是否已配置"),
    NO_COMPANYENCODE(20021, "编码对应的公司信息不存在，请确认编码是否正确"),
    TRANS_FILE_ERROE(20022, "上传数据解析出错，请检查数据格式"),
    SAVE_IMPORT_FAIL(20023, "保存导入流水失败"),
    NOT_FOUND_IMPORT_FILE_INFO(20024, "未找到导入文件信息"),
    IN_BATCH_RUNNING(20025, "数据跑批中，不允许重新推送"),
    BUSINESS_TYPE_CODE_AND_CCY_AND_COMPANY_ACCOUNT_UNIQUE(20026, "相同的业务类型、相同的CCY(币种)、相同的公司账号不能新增"),
    NOT_ALLOW_DELETE_TEMPLATE(20027, "TXT 模板不可删除"),
    NOT_ALLOW_REVIEW(20028, "无权限复核"),
    NOT_FOUND_FOVA_DATA(20029, "未找到 fova 明细数据"),
    IMPORT_INVALID_AMOUNT_FORMAT(20030, "导入金额数据格式不对"),
    ;

    private int statusCode;
    private String msg;

    StatusEnums(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
