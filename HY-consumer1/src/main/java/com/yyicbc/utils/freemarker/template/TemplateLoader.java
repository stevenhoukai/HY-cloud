package com.yyicbc.utils.freemarker.template;

import java.util.Map;

/**
 *@author vic fu
 */
public interface TemplateLoader {
    void setCharset(String charset);

    /**
     * generate text base on data and template String
     * @param data
     * @return text
     * @throws Exception
     */
    String render(Map<String,Object> data, String template);
}
