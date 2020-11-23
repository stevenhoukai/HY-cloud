package com.yyicbc.utils;

import com.yyicbc.beans.business.PO.CompanyAgreementPO;
import com.yyicbc.beans.business.PO.CompanyBasePO;
import com.yyicbc.beans.enums.ExportTypeEnums;
import com.yyicbc.beans.enums.FileTypeEnums;
import com.yyicbc.beans.enums.SpecialTemplateEnums;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.utils.freemarker.pdf.aia.AiaPdf;
import com.yyicbc.utils.freemarker.pdf.cem.CemExport;
import com.yyicbc.utils.freemarker.pdf.ctcc.CtccExport;
import com.yyicbc.utils.freemarker.pdf.ctm.CtmPdf;
import com.yyicbc.utils.freemarker.pdf.czj.CzjExport;
import com.yyicbc.utils.freemarker.pdf.esp.EspPdf;
import com.yyicbc.utils.freemarker.pdf.macauins.MacauInsExport;
import com.yyicbc.utils.freemarker.pdf.manulife.ManulifePdf;
import com.yyicbc.utils.freemarker.pdf.massmutu.MassmutuExport;
import com.yyicbc.utils.freemarker.pdf.saam.SaamExport;
import com.yyicbc.utils.freemarker.pdf.sib.SibNewExport;
import com.yyicbc.utils.freemarker.pdf.sib.SibOldExport;
import com.yyicbc.utils.freemarker.pdf.smartone.SmartOneExport;
import com.yyicbc.utils.freemarker.pdf.standard.StandardExport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @author vic fu
 */
@Component
@Slf4j
public class ExportDownLoad {

    @Resource
    CtmPdf ctmPdf;
    @Resource
    EspPdf espPdf;
    @Resource
    ManulifePdf manulifePdf;
    @Resource
    AiaPdf aiaPdf;
    @Resource
    CemExport cemExport;
    @Resource
    MassmutuExport massmutuExport;
    @Resource
    SaamExport saamExport;
    @Resource
    SmartOneExport smartOneExport;
    @Resource
    CtccExport ctccExport;
    @Resource
    SibOldExport sibOldExport;
    @Resource
    SibNewExport sibNewExport;
    @Resource
    CzjExport czjExport;
    @Resource
    MacauInsExport macauInsExport;

    @Resource
    StandardExport standardExport;


    /**
     * @param data
     * @param companyDetail
     * @param companyAgreementPO
     * @return
     */
    public File exportFile(List<FovaUpdateDataPO> data,
                           CompanyBasePO companyDetail,
                           CompanyAgreementPO companyAgreementPO, int fileType) throws Exception {

        String templateCode = companyDetail.getExportTxtTemplateType();
        if (StringUtils.isBlank(templateCode)) {
            throw new Exception("当前公司未配置导出模板");
        }
        if (ExportTypeEnums.SPECIAL.getCode() == fileType) {//特定格式，必须指定模板
            if("standard".equals(templateCode.toLowerCase())){
                throw new Exception("当前公司不属于特定格式");
            }
        }else{
            //默认标准格式
            templateCode = "standard";
        }

       /* else if (fileType == FileTypeEnums.PDF.getFileCode()) {
            templateCode = companyDetail.getExportPdfTemplateType();
        } else {
            templateCode = companyDetail.getExportExcelTemplateType();
        }*/

        //全部轉成小寫
        templateCode = templateCode.toLowerCase();
        switch (templateCode) {
            case "ctm":
                return ctmPdf.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "esp":
                return espPdf.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "manulife":
                return manulifePdf.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "aia":
                return aiaPdf.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "cem":
                return cemExport.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "massmutu":
                return massmutuExport.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "saam":
                return saamExport.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "smartone":
                return smartOneExport.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "ctcc":
                return ctccExport.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "sib_old":
                return sibOldExport.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "sib_new":
                return sibNewExport.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "czj":
                return czjExport.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            case "macauins":
                return macauInsExport.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);
            default:
                return standardExport.exportFile(data, templateCode, companyDetail, companyAgreementPO, fileType);

        }
    }

    /**
     * 功能描述: 生成TXT
     *
     * @param tmp
     * @param targetPath 目标路径
     * @return: {@link File}
     * @Author: vic
     * @Date: 2020/2/28 14:12
     */
    public static File generateTxtFile(String tmp, String targetPath) {
        File output = new File(targetPath);
        try {
            File parentFile = output.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(output));
            out.write(tmp);
            out.flush();
            out.close();
            return output;
        } catch (Exception e) {
            log.error("生成txt文件异常");
        }
        return null;
    }


    /**
     * 功能描述: 生成TXT 字符集ANSI
     *
     * @param tmp
     * @param targetPath 目标路径
     * @return: {@link File}
     * @Author: vic
     * @Date: 2020/2/28 14:12
     */
    public static File generateTxtFileANSI(String tmp, String targetPath) {
        File output = new File(targetPath);
        try {
            File parentFile = output.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(targetPath)),"GB2312"));
            out.write(tmp);
            out.flush();
            out.close();
            return output;
        } catch (Exception e) {
            log.error("生成txt文件异常");
        }
        return null;
    }
    /**
     * 功能描述:生成Excel
     *
     * @param workbook
     * @param targetPath 目标路径
     * @return: {@link File}
     * @Author: vic
     * @Date: 2020/2/28 14:13
     */
    public static File genereateExcel(HSSFWorkbook workbook, String targetPath) {

        FileOutputStream out = null;
        try {
            File file = new File(targetPath + ".xls");
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            out = new FileOutputStream(file);
            workbook.write(out);
            out.flush();
            return file;
        } catch (Exception e) {
            log.error("生成excel文件异常");
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
