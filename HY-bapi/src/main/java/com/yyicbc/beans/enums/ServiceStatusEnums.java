package com.yyicbc.beans.enums;

import lombok.AllArgsConstructor;

/**
 * @author vic fu
 **/
@AllArgsConstructor
public enum ServiceStatusEnums {

    NEW("NEW SETUP", "0"),
    CANCEL("CANCEL SERVICE", "1");

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }




}
