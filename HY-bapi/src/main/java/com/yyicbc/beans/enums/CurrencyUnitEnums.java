package com.yyicbc.beans.enums;


import org.apache.commons.lang3.StringUtils;

public enum CurrencyUnitEnums {
    CENT("分", 1),
    DIME("角", 2),
    YUAN("元",3),
    PENNY("厘", 4),
    MILLI("毫", 5),
    ;

    private String currencyUnit;
    private int currencyUnitCode;

    CurrencyUnitEnums(String currencyUnit, int currencyUnitCode) {
        this.currencyUnit = currencyUnit;
        this.currencyUnitCode = currencyUnitCode;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public int getFileCode() {
        return currencyUnitCode;
    }

    public void setFileCode(int currencyUnitCode) {
        this.currencyUnitCode = currencyUnitCode;
    }

    public static int getMultiple(String exportTemplateMinunit){
        if(StringUtils.isNotBlank(exportTemplateMinunit)){
            int currencyUnitCode = Integer.parseInt(exportTemplateMinunit);
            if(currencyUnitCode == CurrencyUnitEnums.CENT.getFileCode()){
                return 1;
            }if(currencyUnitCode == CurrencyUnitEnums.DIME.getFileCode()){
                return 10;
            }if(currencyUnitCode == CurrencyUnitEnums.YUAN.getFileCode()){
                return 100;
            }
        }
        return 0;
    }
}
