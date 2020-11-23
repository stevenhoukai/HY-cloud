package com.yyicbc.utils;

import com.yyicbc.beans.business.PO.CompanyBasePO;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.distribute.method.StringFormatUtils;
import com.yyicbc.utils.freemarker.pdf.ctm.CtmTxt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class TXTtemplateForDownload {

    @Resource
    CtmTxt ctmTxt ;

    public static byte[] getTxt(String templateCode, List<FovaUpdateDataPO> data) {
        switch (templateCode) {
            case "UTYREJPF":
                return CTMUTYREJPF(data);
            case "AIA":
                return AIA(data);
            case "SmartOne":
                return SmartOne(data);
            case "czjMaterial":
                return czjMaterial(data);
            default:
                return commonTXT(data);
        }
    }

    /**
     *
     * @param data
     * @param companyDetail
     * @return
     * @throws Exception
     */
    public  byte[] getTxt2(List<FovaUpdateDataPO> data,CompanyBasePO companyDetail) throws Exception {

        String templateCode =  companyDetail.getExportTxtTemplateType();

        if(StringUtils.isBlank(templateCode)){
            return null;
        }
        templateCode =  templateCode.toLowerCase();

        File output = null;

        switch (templateCode) {
            case "UTYREJPF":
                return CTMUTYREJPF(data);
            case "AIA":
                return AIA(data);
            case "SmartOne":
                return SmartOne(data);
            case "czjMaterial":
                return czjMaterial(data);
            case "ctm":
                output = ctmTxt.exportText(data,companyDetail);
                break;
            default:
                return commonTXT(data);
        }
        return FileUtil.fileToBytes(output);
    }

    private static byte[] commonTXT(List<FovaUpdateDataPO> data) {
        String result = "";
        return generateFile(result);
    }

    private static byte[] CTMUTYREJPF(List<FovaUpdateDataPO> data) {
        String result = "";
        //模板生成过程
        result += "IN :TMFDEBTORS SHBREVERSAL" + "\r\n";
        BigDecimal total = new BigDecimal("0");
        String DataString = "";
        for (FovaUpdateDataPO item : data) {
            total = total.add(new BigDecimal(item.getAmount()));
            DataString += "FS0001FS" +
                    StringFormatUtils.leftAddZeroForNum(item.getAgenpsn(), 13) +
                    StringFormatUtils.leftAddZeroForNum(item.getAmount(), 8) + "\n";

        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
        Date date = new Date(System.currentTimeMillis());
        String nowDate = formatter.format(date);
        result += "ZS000102" +
                nowDate +
                StringFormatUtils.leftAddZeroForNum(data.size() + "", 4) +
                StringFormatUtils.leftAddZeroForNum(total.toString() + "", 10) + "\n";
        result += DataString;
        result += "****";
        return generateFile(result);
    }

    private static byte[] SmartOne(List<FovaUpdateDataPO> data) {
        String result = "";

        return generateFile(result);
    }

    private static byte[] AIA(List<FovaUpdateDataPO> data) {
        String result = "";
        for (FovaUpdateDataPO item : data) {
            if (StringUtils.isNotBlank(item.getErrNo())) {//只返回失败的
                int length = item.getErrNo().length();
                String status = item.getErrNo().substring(length - 2, length);
                if (!status.equals("01") && !status.equals("02") && !status.equals("03") &&
                        !status.equals("04") && !status.equals("05")) {
                    status = "99";
                }
                result += status + item.getAgenpsn() + " " +
                        StringFormatUtils.leftAddZeroForNum(item.getAmount(),13) + "\n";
            }
        }
        return generateFile(result);
    }

    private static byte[] czjMaterial(List<FovaUpdateDataPO> data) {
        String result = "";
        for (FovaUpdateDataPO item : data) {
            String czjCode = StringUtils.isBlank(item.getAgentNo())? "":item.getAgentNo();
           result += "D" +
                   getBankCode(item.getBankCode()) +
                   StringFormatUtils.leftAddZeroForNum(czjCode,30) +//财政局参考编码未知
                   StringFormatUtils.leftAddZeroForNum(item.getAgenpsn(),30) +
                   StringFormatUtils.leftAddZeroForNum(item.getAmount(),15);
           if(StringUtils.isBlank(item.getErrNo())){//结果code未知
               result += "OK";
           }else{
               result += "ER";
           }
            result += "\n";
        }
        return generateFile(result);
    }

    private static String getBankCode(String bank){
        if(StringUtils.isNotBlank(bank)){
            switch(bank){
                case "BLI": return "03";
                case "BOC": return "04";
                case "BTF": return "08";
                case "BWH": return "09";
                case "BNU": return "10";
                case "BCM": return "13";
                case "BEA": return "35";
                case "ICC": return "37";
                default: return "";
            }
        }else {
            return "";
        }

    }

    private static byte[] generateFile(String tmp) {
        File output = new File("\\output.txt");
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(output));
            out.write(tmp);
            out.flush();
            out.close();
            byte[] bFile = new byte[(int) output.length()];
            FileInputStream fis = new FileInputStream(output);
            fis.read(bFile);
            fis.close();
            output.delete();
            return bFile;
        } catch (Exception e) {
            log.error("txt文件生成异常 exception:" + e.getMessage());
        }
        return null;
    }

}
