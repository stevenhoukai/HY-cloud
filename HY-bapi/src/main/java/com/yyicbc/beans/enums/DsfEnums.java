package com.yyicbc.beans.enums;

import com.yyicbc.beans.utils.YyStringUtils;
import lombok.AllArgsConstructor;

/**
 * @ClassNameDsfEnums
 * @Description
 * @Author vic
 * @Date2020/3/26 14:02
 * @Version V1.0
 **/
@AllArgsConstructor
public enum  DsfEnums {

    DSF("dsf", "财政局");


    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**功能描述: 校验是否财政局
     * @param value
    * @return: {@link boolean}
    * @Author: vic
    * @Date: 2020/3/26 14:05
    */
    public static boolean checkDsf(Object value){
        if(DSF.code.equals(YyStringUtils.toString(value).toLowerCase())){
            return true;
        }else{
            return false;
        }
    }
}
