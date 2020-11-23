package com.yyicbc.beans.enums;

import lombok.AllArgsConstructor;

/**
 * @ClassNameExportTypeEnums
 * @Description 导出文件类型
 * @Author vic
 * @Date2020/3/2 14:43
 * @Version V1.0
 **/
@AllArgsConstructor
public enum  ExportTypeEnums {

    EXCEL(0, "标准Excel"),
    PDF_OUT(1, "标准PDF (对外)"),
    CUSTOMER(2, "客户通知书"),
    PDF_IN(3, "标准PDF (对内)"),
    /*包含PDF /TXT/ EXCEL/ZIP 等样式，全部合并导出一个ZIP*/
    SPECIAL(4, "特定格式");


    public int code;

    public String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
