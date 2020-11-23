package com.yyicbc.beans.enums;


public enum FileStatusEnums {
    UPLOADED("上传成功", "0"),
    VERIFIED("已审核", "1"),
    HANDED("已提交FOWA", "2"),
    FOVAFAIL("FOWA上传失败", "3"),
    FOVASUCCESS("FOWA处理成功", "4"),
    ABANDON("已弃审", "5"),
    ;

    private String statusName;
    private String StatusCode;

    FileStatusEnums(String statusName, String StatusCode) {
        this.statusName = statusName;
        this.StatusCode = StatusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }
}
