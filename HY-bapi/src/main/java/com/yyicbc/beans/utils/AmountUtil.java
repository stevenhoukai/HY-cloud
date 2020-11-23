package com.yyicbc.beans.utils;

import com.yyicbc.beans.business.PO.CompanyBasePO;
import com.yyicbc.beans.business.PO.UserImportPO;
import com.yyicbc.beans.enums.CurrencyUnitEnums;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金額處理工具類
 *@author vic fu
 */
public class AmountUtil {


    /**
     * add by fmm
     * 根據金額單位轉換成最終的顯示金額
     * @param currencyUnitCode
     * @param oldAmount
     * @return
     */
    public static BigDecimal handleUnit(int currencyUnitCode,Object oldAmount){

        if(oldAmount == null){
           return new BigDecimal("0");
        }
        BigDecimal amount = new BigDecimal(oldAmount.toString());
        if(currencyUnitCode == CurrencyUnitEnums.CENT.getFileCode()){
            amount = amount.divide(new BigDecimal("100"));
        }if(currencyUnitCode == CurrencyUnitEnums.DIME.getFileCode()){
            amount = amount.divide(new BigDecimal("10"));

        }if(currencyUnitCode == CurrencyUnitEnums.PENNY.getFileCode()){
            amount = amount.divide(new BigDecimal("1000"));

        }if(currencyUnitCode == CurrencyUnitEnums.MILLI.getFileCode()){
            amount = amount.divide(new BigDecimal("10000"));
        }
        return amount;
    }


    /**
     * 保留兩位顯示並且顯示千分位
     * @param amount
     * @return
     */
    public static String formatAmount(BigDecimal amount){

        if(amount == null){
            amount = new BigDecimal(0);
        }
        DecimalFormat df4 = new DecimalFormat("#,##0.00");

        return  df4.format(amount);
    }

    /**
     * 替換掉千分位符號
     * @param amount
     * @return
     */
    public static String replaceTh(Object amount){

        return YyStringUtils.toString(amount).replace(",","");
    }

    /**
     * 根據金額單位轉換成最終的顯示金額 並且格式化
     * @param oldAmount
     * @param oldAmount
     * @return
     */
    public static String getAmountByUnit(CompanyBasePO companyBasePO, Object oldAmount){

        return formatAmount(handleUnit(Integer.valueOf(companyBasePO.getExportTemplateMinunit()),oldAmount));
    }

    public static Double formatToNumber(BigDecimal obj)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        if (obj.compareTo(BigDecimal.ZERO) == 0) {
            return 0.00;
        } else if (obj.compareTo(BigDecimal.ZERO) > 0 && obj.compareTo(new BigDecimal(1)) < 0) {
            return Double.parseDouble("0" + df.format(obj).toString());
        } else {
            return Double.parseDouble(df.format(obj).toString());
        }
    }


}
