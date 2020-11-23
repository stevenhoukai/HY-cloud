package com.yyicbc.beans.utils;


import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @author vic fu
 */
public class YyStringUtils {


    /**
     * 日期替换-
     *
     * @param date
     * @return
     */
    public static String repleacDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }
        return date.replace("-", "");
    }

    /**
     * 固定长度3
     *
     * @param value
     * @return
     */
    public static String addZeroByStatus(Object value) {
        return addZero(value, 3);
    }

    /**
     * 补0
     *
     * @param value
     * @param length 字符长度
     * @return
     */
    public static String addZero(Object value, int length) {
        String str = value == null ? "" : value.toString();
        int strLength = str.length();
        StringBuffer addStr = new StringBuffer();
        for (int i = 0; i < length - strLength; i++) {
            addStr.append("0");
        }
        return addStr.toString() + str;
    }


    public static String addSpaceByLeft(Object value, int length) {
        return addSpace(value, length, true);
    }

    public static String addSpaceByRight(Object value, int length) {
        return addSpace(value, length, false);
    }

    public static String toString(Object value) {
        return value == null ? "" : value.toString();
    }


    /**
     * 补充空格
     *
     * @param value
     * @param length
     * @param left
     * @return
     */
    public static String addSpace(Object value, int length, boolean left) {
        String str = value == null ? "" : value.toString();
        int strLength = str.length();
        StringBuffer addStr = new StringBuffer();
        for (int i = 0; i < length - strLength; i++) {
            addStr.append(" ");
        }
        if (left) {
            return addStr.toString() + str;
        } else {
            return str + addStr.toString();
        }

    }

    /**
     * 功能描述: 添加换行符号
     *
     * @param accName
     * @return: {@link String}
     * @Author: vic
     * @Date: 2020/4/1 11:51
     */
    public static String addHtmlBrByName(String accName, int pageSize) {

        StringBuffer nameBuffer = new StringBuffer();
        String accountName = YyStringUtils.toString(accName);
        int accNameLength = accountName.length();
        //15个字符换行
        //int pageSize = 15;
        int count = accNameLength / pageSize;
        int m = accNameLength % pageSize;
        int pageCount = 0;
        if (m > 0) {
            pageCount = count + 1;
        }
        for (int j = 1; j <= pageCount; j++) {
            String subStr = "";
            if (j == pageCount) {
                subStr = accountName.substring((j - 1) * pageSize, accNameLength);
            } else {
                subStr = accountName.substring((j - 1) * pageSize, pageSize * (j));
            }
            if (j > 1) {
                subStr = "<br>" + subStr + "</br>";
            }
            nameBuffer.append(subStr);
        }

        return nameBuffer.toString();
    }

    /**功能描述:解析导入字段
     * @param unused
    * @return: {@link HashMap< String, String>}
    * @Author: vic
    * @Date: 2020/4/16 17:49
    */
    public static HashMap<String, String> getMapByUnused(String unused) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (!StringUtils.isEmpty(unused)) {
            String[] splits = unused.trim().split(",");
            for (String str : splits) {
                String[] values = str.split(":");
                if (values != null && values.length == 2) {
                    map.put(values[0], values[1]);
                }
            }
        }
        return map;
    }
}
