package com.yyicbc.beans.enums;

import lombok.AllArgsConstructor;

/**
 *@author vic fu
 */

@AllArgsConstructor
public enum FovaPayStatus {


    PAY("GOOD DEBIT", "002"),
    BAD("REJECT RECORD", "999");


    private String statusName;
    private String statusCode;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
