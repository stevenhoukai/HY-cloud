package com.yyicbc.utils.freemarker;

import java.util.regex.Pattern;

/**
 *@author vic fu
 */
public class TextUtils {

	private static Pattern pattern = Pattern.compile("\\{(\\w+?)\\}");

	public static String htmlReplace(String str){
        str = str.replace("&ldquo;", "“");
        str = str.replace("&rdquo;", "”");
        str = str.replace("&nbsp;", "\u00a0");
        str = str.replace("&", "&amp;");
        str = str.replace("&#39;", "'");
        str = str.replace("&rsquo;", "’");
        str = str.replace("&mdash;", "—");
        str = str.replace("&ndash;", "–");
        return str;
    }

}
