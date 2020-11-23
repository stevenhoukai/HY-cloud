package com.yyicbc.beans.enums;


import com.yyicbc.beans.business.PO.UserImportPO;

public enum TxTHeadEnums {
    CTM("CTM", 0),
    VERIFIED("已审核", 1),
    HANDED("已提交FOWA", 2),
    RESOLVED("FOWA已处理", 3),
    ;

    private String headName;
    private int headCode;

    TxTHeadEnums(String headName, int headCode) {
        this.headName = headName;
        this.headCode = headCode;
    }

    public String getStatusName() {
        return headName;
    }

    public void setStatusName(String statusName) {
        this.headName = statusName;
    }

    public int getStatusCode() {
        return headCode;
    }

    public void setStatusCode(int statusCode) {
        headCode = statusCode;
    }

}
