package com.yyicbc.beans.utils.distribute.method;

public class StringFormatUtils {

    //删除分隔符
    public static String delSpace(String str) {
        if (str == null) {
            return "";
        }

        str = str.trim();//删空格
        String strDelSpace = str.replaceFirst("^\\|*", "");//删分隔符
        return strDelSpace;
    }
    /**功能描述: 指定了长度不去空格
     * @param str
    * @return: {@link String}
    * @Author: vic
    * @Date: 2020/3/10 18:25
    */
    public static String delSpace2(String str) {
        if (str == null) {
            return "";
        }
        //modify by fmm 不要删除空格
//        str = str.trim();//删空格
        String strDelSpace = str.replaceFirst("^\\|*", "");//删分隔符
        return strDelSpace;
    }

    /**
     * Added by xiep 只删除第一个 | 分隔符
     *
     * @param str
     * @return
     */
    public static String deleteFirstSeparator(String str) {
        if (str == null || "".equals(str)) {
            return "";
        }

        str = str.trim();//删空格
        String strDelSpace = str.replaceFirst("^\\|", "");
        return strDelSpace;
    }
    //左侧补0
    public static String leftAddZeroForNum(String str, int strLength) {
        if(str==null)
            str = "";
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);// 左补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }
    //右侧补0
    public static String rightAddZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append(str).append("0");//右补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

}
