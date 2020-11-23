package com.yyicbc.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ste
 * 模块(节点)与前台枚举对应的关系
 */
public class ModuleMap {

    public static Map<String,Integer> modulesMap = null;
    public static Map<String,String>  modulesShowMap = null;
    public static Map<String,String>  typeShowMap = null;


    static{
        modulesMap = new HashMap<String,Integer>();
        modulesShowMap = new HashMap<String,String>();
        typeShowMap = new HashMap<String,String>();
        modulesMap.put("/collection/currencyfile",0);
        modulesMap.put("/collection/businessfile",1);
        modulesMap.put("/collection/companyfile",2);
        modulesMap.put("/collection/companyagreementfile",3);
        modulesMap.put("/collection/companycustomerfile",4);
        modulesMap.put("/imports/formatfield",5);
        modulesMap.put("/imports/txttemplate",6);
        modulesMap.put("/imports/exceltemplate",7);
        modulesMap.put("/imports/pdftemplate",8);
        modulesMap.put("/imports/userimport",9);
        modulesMap.put("/compensation",10);
        modulesMap.put("/system/user",11);
        modulesMap.put("/system/role",12);
        modulesMap.put("/system/task",13);
        modulesMap.put("/system/interfaceconfiguration",14);

        typeShowMap.put("0","新增");
        typeShowMap.put("1","修改");
        typeShowMap.put("2","删除");

        modulesShowMap.put("0","币种");
        modulesShowMap.put("1","业务种类");
        modulesShowMap.put("2","公司档案信息");
        modulesShowMap.put("3","公司协议档案信息");
        modulesShowMap.put("4","公司客户档案信息");
        modulesShowMap.put("5","模板格式字段档案");
        modulesShowMap.put("6","Txt模板管理");
        modulesShowMap.put("7","Excel模板管理");
        modulesShowMap.put("8","Pdf模板管理");
        modulesShowMap.put("9","客户文件导入");
        modulesShowMap.put("10","代发薪资");
        modulesShowMap.put("11","用户管理");
        modulesShowMap.put("12","权限管理");
        modulesShowMap.put("13","定时任务管理");
        modulesShowMap.put("14","接口配置管理");

    }

}
