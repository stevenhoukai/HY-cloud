package com.yyicbc.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.yyicbc.beans.business.PO.CompanyAgreementPO;
import com.yyicbc.beans.business.PO.CompanyBasePO;
import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.utils.freemarker.pdf.Test;
import com.yyicbc.utils.freemarker.pdf.aia.AiaPdf;
import com.yyicbc.utils.freemarker.pdf.ctm.CtmPdf;
import com.yyicbc.utils.freemarker.pdf.esp.EspPdf;
import com.yyicbc.utils.freemarker.pdf.manulife.ManulifePdf;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class PDFtemplateForDownLoad {

    static Test test = new Test();

    @Resource
    CtmPdf ctmPdf;
    @Resource
    EspPdf espPdf;
    @Resource
    ManulifePdf manulifePdf;
    @Resource
    AiaPdf aiaPdf;



    public static Document getDocument(String templateCode, List<FovaUpdateDataPO> data, Document document) {
        switch (templateCode) {
            case "ETCNewAppplicatants":
               return CTMNewAppplicatants(data, document);
            default:
                return commomPDF(data, document);
        }
    }

    /**
     * add by fmm
     * @param data
     * @param companyDetail
     * @param companyAgreementPO
     * @param
     * @return
     */
    public  File getPdfFile(List<FovaUpdateDataPO> data,CompanyBasePO companyDetail, CompanyAgreementPO companyAgreementPO) throws Exception {

        String templateCode =  companyDetail.getExportPdfTemplateType();
        if(StringUtils.isBlank(templateCode)){
            return null;
        }
        //全部轉成小寫
        templateCode = templateCode.toLowerCase();
        int fielType = 2;
        switch (templateCode) {
            case "ctm":
                return ctmPdf.exportFile(data,"ctm_pdf",companyDetail,companyAgreementPO,fielType);
            case "esp":
                return espPdf.exportFile(data,"esp_pdf",companyDetail,companyAgreementPO,fielType);
            case "manulife":
                return manulifePdf.exportFile(data,"manulife_pdf",companyDetail,companyAgreementPO,fielType);
            case "aia":
                return aiaPdf.exportFile(data,"aia_pdf",companyDetail,companyAgreementPO,fielType);
            default:
                return ctmPdf.exportFile(data,"default_pdf",companyDetail,companyAgreementPO,fielType);

        }
    }

    private static Document commomPDF(List<FovaUpdateDataPO> detailData, Document document) {
        return document;
    }

    private static Document CTMNewAppplicatants(List<FovaUpdateDataPO> detailData, Document document) {
        try {
            document.open();
            String head = "INDUSTRIAL AND COMMERCIAL BANK OF CHINA (MACAU) LIMITED.\n" +
                    "DEBIT AUTHORIZATION LIST -NEW APPLICANTS";
            Paragraph headParagraph = new Paragraph(head);
            headParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(headParagraph);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date(System.currentTimeMillis());
            String nowDate = formatter.format(date);
            //pdf头写入
            String title = "CREDIT PARTY : " + "CTM" + "                  " +
                    "AUTO-PAY APPLICATION DATE : " + nowDate;
            Paragraph titleParagraph = new Paragraph(title);
            document.add(titleParagraph);
            String column = "PAYMENT REF     " + "DEBIT LIMIT    " + "DR ACCOUNT NO   " +
                    "APPLICATION DATE   " + "ACCOUNT NAME\n" +
                    "******************** " + "*********** " + "******************* " +
                    "**************** " + "****************************";
            Paragraph columnParagraph = new Paragraph(column);
            document.add(columnParagraph);
            //数据写入
            String dataLine = "";
            for (FovaUpdateDataPO item : detailData) {
                if (StringUtils.isBlank(item.getAgenpsn())) {
                    dataLine += "-----";
                } else {
                    dataLine += item.getAgenpsn();
                }
                dataLine += "      ";
                if (StringUtils.isBlank(item.getBankCode())) {
                    dataLine += "-----";
                } else {
                    dataLine += item.getAgenpsn();
                }
                dataLine += "      ";
                if (StringUtils.isBlank(item.getSummary())) {
                    dataLine += "-----";
                } else {
                    dataLine += item.getAgenpsn();
                }
                dataLine += "      ";
                dataLine += nowDate;
                dataLine += "      ";
                if (StringUtils.isBlank(item.getShotName())) {
                    dataLine += "-----";
                } else {
                    dataLine += item.getAgenpsn();
                }
                dataLine += "\r\n";
            }
            // 5.关闭文档
            document.add(new Paragraph(dataLine));
            document.close();
            return document;
        } catch (Exception e) {
            log.error("生成pdf文件异常");
        }
        return document;
    }
}


