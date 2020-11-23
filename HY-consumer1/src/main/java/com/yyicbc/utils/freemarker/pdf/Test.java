package com.yyicbc.utils.freemarker.pdf;

import com.yyicbc.utils.freemarker.Exporter;
import com.yyicbc.utils.freemarker.PdfExporter;
import com.yyicbc.utils.freemarker.TextUtils;
import com.yyicbc.utils.freemarker.template.FreemarkerTemplateLoader;
import com.yyicbc.utils.freemarker.template.TemplateLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author vic fu
 */
@Slf4j
public class Test {

    /**
     * PDF相关
     */
    private TemplateLoader templateLoader = new FreemarkerTemplateLoader();

    private Exporter exporter = new PdfExporter();
    
    public File exprotPdf(){
        try {
            log.info("===测试生成PDF START===");
            Map data = new HashMap();
            data.put("username", "test");
            data.put("fova_addr", "test2345");
            List list = new ArrayList();
            Map listData1 = new HashMap();
            listData1.put("user","1");
            listData1.put("pwd","2");
            list.add(listData1);

            Map listData2 = new HashMap();
            listData2.put("user","3");
            listData2.put("pwd","4");
            list.add(listData2);

            List userinfo =new ArrayList();
            userinfo.add("周美玲");
            userinfo.add("32");
            userinfo.add("女");
            userinfo.add("1985");
            userinfo.add("19850014665");

            data.put("userlist",userinfo);
            String content = templateLoader.render(data, "testmodel1.html");
            log.info("content="+content);
            File file  =  exporter.exportContent(TextUtils.htmlReplace(content), "D://testmodel1.pdf");
//            exporter.exportContent(TextUtils.htmlReplace(content), "/nfs/SHARE_FILES/OUT/PAY_NOTE/TEST1.pdf");
            log.info("===测试生成PDF END===");
            return file;

        } catch (Exception e) {
            log.info(e.getMessage());
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

}
