package com.yyicbc.utils.freemarker.pdf;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;
import com.yyicbc.utils.freemarker.Exporter;
import com.yyicbc.utils.freemarker.PdfExporter;
import com.yyicbc.utils.freemarker.TextUtils;
import com.yyicbc.utils.freemarker.template.FreemarkerTemplateLoader;
import com.yyicbc.utils.freemarker.template.TemplateLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

/**
 *@author vic fu
 */
@Slf4j
public class PdfExportUtil {

    private  static TemplateLoader templateLoader = new FreemarkerTemplateLoader();

    private static Exporter exporter  = new PdfExporter();

    /**
     * 導出PDF
     * @param data 數據格式
     * @param templateFile html模板
     * @param targetPdfFile 生成Pfd路徑+文件名
     * @return
     */
    public static File exprotPdf(Map data,String templateFile,String targetPdfFile){
        try {
            log.info("===生成PDF START===");
            String content = templateLoader.render(data, templateFile+".html");
//            log.info("content="+content);
            File file  =  exporter.exportContent(TextUtils.htmlReplace(content), targetPdfFile+".pdf");
            log.info("===生成PDF END===");
            return file;

        } catch (Exception e) {
            log.error("===生成PDF失敗==="+e.getMessage());
            e.printStackTrace();
        }
        return null;

    }


    /**
     *
     * @param writer
     * @param fileName
     */
    public static void readPdf(PrintWriter writer, String fileName){
        String pageContent = "";
        PdfReader reader =  null;
        try {
             reader = new PdfReader(fileName);
            int pageNum = reader.getNumberOfPages();
            for(int i=1;i<=pageNum;i++){
                String textFromPage = PdfTextExtractor.getTextFromPage(reader, i);
                pageContent += textFromPage;//读取第i页的文档内容
                //读取第i页的文档内容
//                pageContent += PdfTextExtractor.getTextFromPage(reader, i);
            }
            writer.write(pageContent);

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            writer.close();
            if(reader != null){
                reader.close();
            }
        }
    }

    /**
     *
     * @param writer
     * @param fileName
     */
    public static void readPdfFilter(PrintWriter writer,String fileName){
        String pageContent = "";
        try {
            Rectangle rect = new Rectangle(90, 0, 450, 40);
            RenderFilter filter = new RegionTextRenderFilter(rect);
            PdfReader reader = new PdfReader(fileName);
            int pageNum = reader.getNumberOfPages();
            TextExtractionStrategy strategy;
            for (int i = 1; i <= pageNum; i++) {
                strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
                pageContent +=PdfTextExtractor.getTextFromPage(reader, i, strategy);
            }
			/*String[] split = pageContent.split(" ");
			for(String ss : split){
				System.out.println(ss.substring(ss.lastIndexOf("：")+1, ss.length()));
			}*/
            writer.write(pageContent);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            writer.close();
        }
    }
}
