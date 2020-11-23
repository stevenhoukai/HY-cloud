package com.yyicbc.beans.enums;

import lombok.AllArgsConstructor;

/**导出特定模板编码
 *@author vic fu
 */

@AllArgsConstructor
public enum SpecialTemplateEnums {


    CZJ("czj", "czj");

    private String name;

    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
