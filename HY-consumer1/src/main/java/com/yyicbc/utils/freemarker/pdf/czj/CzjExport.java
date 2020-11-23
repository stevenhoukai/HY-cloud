package com.yyicbc.utils.freemarker.pdf.czj;

import com.alibaba.fastjson.JSONObject;
import com.yyicbc.beans.business.PO.UserImportPO;
import com.yyicbc.beans.enums.FovaPayStatus;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.querycondition.UserImportQuestVO;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.freemarker.pdf.AbstractPdfExport;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassNameCjzExport
 * @Description 财政局
 * @Author vic
 * @Date2020/2/27 9:39
 * @Version V1.0
 **/
@Component
public class CzjExport extends AbstractPdfExport {

    @Override
    protected List exportPdfList(List<FovaUpdateDataPO> detailData) {
        return null;
    }

    @Override
    protected List exportTxtList(List<FovaUpdateDataPO> detailData) {
        List fileList = new ArrayList<File>();
        setAmountUnit(false);
        //先進行匯總
        handleTotalMap(detailData);
        //Cjz 包括所有
        File smtFile = exportCjzTxt(detailData);

        addFileList(fileList, smtFile);

        return fileList;
    }

    /**
     * 功能描述:
     *
     * @param detailData
     * @return: {@link File}
     * @Author: vic
     * @Date: 2020/2/27 9:43
     */
    private File exportCjzTxt(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        String fileName = "";
        StringBuffer txtBuffer = new StringBuffer();
        HashMap<String, String> dataMap = getMapByUnused(detailData.get(0).getUnused());
        //银行代号
        String bankNum = dataMap.get("dsf_yhdh");
        String bankCode = getBankCode(bankNum);

        //Detail
        detailData.forEach(detail -> {
            txtBuffer.append("D");
            //银行代码
            txtBuffer.append(bankNum);
            //财政局参考编号
            HashMap<String, String> detailMap = getMapByUnused(detail.getUnused());
            String dsf_czjckbh = detailMap.get("dsf_czjckbh");
            //财政局参考编码未知
            txtBuffer.append(YyStringUtils.addZero(dsf_czjckbh, 30));
            //銀行帳戶號碼
//            txtBuffer.append(YyStringUtils.addZero(detail.getCustomerAccount(), 30));
            txtBuffer.append(YyStringUtils.addZero(detail.getCustomerAccountDsf(), 30));
            //金额
            txtBuffer.append(YyStringUtils.addZero(detail.getAmount(), 15));
            String result = "";
            if (FovaPayStatus.PAY.getStatusCode().equals(YyStringUtils.toString(detail.getStatusDsf()))) {//结果code未知
                result += "OK";
            } else {
                result += "ER";
            }
            txtBuffer.append(result);
            //預留欄位
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 120));
            txtBuffer.append("\r\n");

        });
        //最后一行总数
        txtBuffer.append("C");
        txtBuffer.append(bankNum);
        //文件编号 按照以下格式进行截取
        //送交國際銀行之資料檔名稱為202003201503504_DSF2BLI_20150624_1-DAIJ-DAJT-2015-001.TXT，
        //送交中國銀行之資料檔名稱為202003201503504_DSF2BOC_20150624_99999-DAIJ-DAJT-2015-001.TXT。
        UserImportQuestVO request = new UserImportQuestVO();
        request.setId(Long.valueOf(detailData.get(0).getUserImportId()));
        String userImportJson = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/getUserImportById", request, String.class);
        UserImportPO userImportPO = JSONObject.parseObject(userImportJson, UserImportPO.class);
        String filef = "";
        if (userImportPO != null) {
            filef = userImportPO.getImportFileName();
        }
        //获取导入文件名称

        String fileCode = "";
       /* if(StringUtils.isNotBlank(filef)){
            int startIndex = filef.lastIndexOf("_");
            int endIndex = filef.lastIndexOf("-");
            if(startIndex != -1&& endIndex != -1){
                String subFilef = filef.substring(startIndex,endIndex);
                if(StringUtils.isNotBlank(subFilef)){
                    fileCode = subFilef.replace("-","/");
                }
            }

        }*/
        //根据导入文件最后一条汇总拆分文件编号
        String dsfTotal = userImportPO.getDsfTotal();
        String num = "1";
        if (StringUtils.isNotBlank(dsfTotal)) {
            fileCode = dsfTotal.substring(1, dsfTotal.indexOf(" "));
            String[] splits = fileCode.split("/");
            if (splits != null && splits.length > 0) {
                num = splits[0];
            }
        }
        txtBuffer.append(YyStringUtils.addSpaceByRight(fileCode, 50));
        //交易日期
        txtBuffer.append(transDate);
        //总的交易数量
        txtBuffer.append(YyStringUtils.addZero(totalMap.get(TOTAL_KEY_RECORD), 7));
        //总交易金额
        txtBuffer.append(YyStringUtils.addZero(totalMap.get(TOTAL_KEY_AMOUNTDUE), 17));
        //預留欄位
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 115));
        //最后一行也换行
        txtBuffer.append("\r\n");

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + bankCode + "2DSF_" + transDate + "_01.TXT");
    }


    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) {
        return null;
    }


    /**
     * 功能描述: 获取对应银行简称代码
     *
     * @param bank
     * @return: {@link String}
     * @Author: vic
     * @Date: 2020/2/27 9:40
     */
    private String getBankCode(String bank) {
        if (StringUtils.isNotBlank(bank)) {
            switch (bank) {
                case "03":
                    return "BLI";
                case "04":
                    return "BOC";
                case "08":
                    return "BTF";
                case "09":
                    return "BWH";
                case "10":
                    return "BNU";
                case "13":
                    return "BCM";
                case "35":
                    return "BEA";
                case "37":
                    return "ICC";
                default:
                    return bank;
            }
        }
        return bank;

    }
}
