package com.yyicbc.utils.freemarker.pdf.ctm;
import com.yyicbc.beans.business.PO.CompanyBasePO;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.utils.FileUtil;
import com.yyicbc.utils.freemarker.pdf.PdfExportUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

/**
 *@author vic fu
 */
@Component
public class CtmTxt {

    @Resource
    CtmPdf ctmPdf;
    @Value("${export.path.txt}")
    private String exportPathTxt;


    //公司编码文件夹
    private String comFilePath  ="";

    private String getPath(){
        return exportPathTxt + "/"+comFilePath+"/";
    }

    public File exportText(List<FovaUpdateDataPO> detailData, CompanyBasePO companyDetail) throws FileNotFoundException {

        comFilePath = UUID.randomUUID().toString();

        ctmPdf.setCompanyDetail(companyDetail);

        List pdfFileList = ctmPdf.exportPdfList(detailData);

        if(CollectionUtils.isNotEmpty(pdfFileList)){

            File txtFile = new File(getPath());
            if(!txtFile.exists()){
                txtFile.mkdirs();
            }
            for (Object file:pdfFileList) {
                //PDF 转成TXT
                File pdfFile = (File)file;
//                String outputPath = "D:\\pdffile\\ctm\\PdfContent_3.txt";
                String outputPath = getPath()+pdfFile.getName().replace("pdf","txt");
                PrintWriter writer = new PrintWriter(new FileOutputStream(outputPath));
//                String fileName = "D:\\pdffile\\ctm\\PAY.pdf";//
                //直接读全PDF面
                 PdfExportUtil.readPdf(writer, pdfFile.getPath());
            }

            //TXT文件进行压缩
            FileUtil.fileToZip(getPath(),getPath(),"ctm_txt.zip");
        }
        File zipFile = new File(getPath()+"ctm_txt.zip");

        return  zipFile;
    }

}
