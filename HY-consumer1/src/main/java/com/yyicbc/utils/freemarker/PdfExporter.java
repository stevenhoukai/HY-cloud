package com.yyicbc.utils.freemarker;

import com.lowagie.text.pdf.BaseFont;
import com.yyicbc.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *@author vic fu
 */
@Slf4j
@Component
public class PdfExporter implements Exporter {


    @Override
    public void exportHtml(String htmlFile, String targetFile) {
        try {
            OutputStream os = new FileOutputStream(targetFile);
            ITextRenderer render = new ITextRenderer();
            ITextFontResolver fontResolver = render.getFontResolver();
            ClassPathResource classPathResource = new ClassPathResource("template/font/MINGLIU.TTC");
            fontResolver.addFont(classPathResource.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            render.setDocument(new File(htmlFile));
            render.layout();
            render.createPDF(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @Override
    public void exportContent(String htmlContent, OutputStream stream) {
        try {
            ITextRenderer render = new ITextRenderer();
            ITextFontResolver fontResolver = render.getFontResolver();
            ClassPathResource classPathResource = new ClassPathResource("template/font/MINGLIU.TTC");
            fontResolver.addFont(classPathResource.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            render.setDocumentFromString(htmlContent);
            render.layout();
            render.createPDF(stream);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @Override
    public File exportContent(String htmlContent, String targetFile)  {

        OutputStream os = null;
        try {
            File file = new File(targetFile);

            File parentFile = file.getParentFile();
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
             os = new FileOutputStream(file);
            ITextRenderer render = new ITextRenderer();
            ITextFontResolver fontResolver = render.getFontResolver();
            ClassPathResource classPathResource = new ClassPathResource("template/font/MINGLIU.TTC");
            fontResolver.addFont(classPathResource.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            render.setDocumentFromString(htmlContent);
            render.layout();
            render.createPDF(os);
            return file;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.info(e.getMessage());
            e.printStackTrace();
        }finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
