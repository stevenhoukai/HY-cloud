package com.yyicbc.utils.freemarker.pdf.cem;

import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.enums.FovaPayStatus;
import com.yyicbc.beans.enums.ServiceStatusEnums;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.AmountUtil;
import com.yyicbc.beans.utils.CCustomDate;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.freemarker.pdf.AbstractPdfExport;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vic fu
 */
@Component
public class CemExport extends AbstractPdfExport {


    @Override
    protected List exportPdfList(List<FovaUpdateDataPO> detailData) {
        return null;
    }

    @Override
    public List exportTxtList(List<FovaUpdateDataPO> detailData) {


        //设置金额不转换
        setAmountUnit(false);
        List fileList = new ArrayList<File>();
        //先進行匯總
        handleTotalMap(detailData);
        /***导出如果有新申請，則同時生成如下三種格式NDR/DEL/NEW**/
        //NDR
        File ndrFile = exportNdrTxt(filterData(detailData, TYPE_NDR));
        //DEL
        File delFile = exportDelTxt(filterData(detailData, TYPE_CANCEL));
        //NEW
        File newFile = exportNewTxt(filterData(detailData, TYPE_NEW));
        //BAD
        File badFile = exportBadTxt(filterData(detailData, TYPE_BAD));
        //PAY
        File payFile = exportPayTxt(filterData(detailData, TYPE_PAY));

        //ADV 要根据金额单元转换
        isAmountUnit = true;
        handleTotalMap(detailData);
        //TYPE_PAY
        File advFile = exportAdvTxt(filterData(detailData, TYPE_PAY));
        //TYPE_PBR
        File pbrFile = exportPbrTxt(filterData(detailData, TYPE_PBR));

        addFileList(fileList, ndrFile);
        addFileList(fileList, delFile);
        addFileList(fileList, newFile);
        addFileList(fileList, badFile);
        addFileList(fileList, payFile);
        addFileList(fileList, advFile);
        addFileList(fileList, pbrFile);

        return fileList;
    }

    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) {
        return null;
    }


    /**
     * A、	SHB-0902.BAD （失敗）
     * Header
     * 固定位數(0000390060000000)+固定零 +ICBC+（日期+失敗的筆數+失敗的金額）
     * Detail（失敗的詳細清單）
     * 第一列：00020390060000000+合同號碼+00000000+客戶名稱
     * 第二列：失敗金額+日期
     *
     * @param detailData
     * @return
     */
    private File exportBadTxt(List<FovaUpdateDataPO> detailData) {
        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();
        //固定位數+固定0+ICBC+日期+筆數
        txtBuffer.append(YyStringUtils.addSpaceByRight("000003900600000000", 28));
        txtBuffer.append(YyStringUtils.addSpaceByRight("0000000000000000", 24));
        txtBuffer.append(YyStringUtils.addSpaceByRight("ICBC", 40));
//        txtBuffer.append("190902");
        String date = "datenull";
        if (StringUtils.isNotEmpty(transDate) && transDate.length() == 8) {
            date = transDate.substring(transDate.length() - 6, transDate.length());
        }
        txtBuffer.append(date);
        String rejectRecord = getStringValue(totalMap, TOTAL_KEY_REJECTRECORD);
        txtBuffer.append(YyStringUtils.addZero(rejectRecord, 6));
        //第二列：固定0
        String rejectAmount = getStringValue(totalMap, TOTAL_KEY_REJECTAMOUNT);
        txtBuffer.append(YyStringUtils.addZero(rejectAmount, 15));

        //表体
        String finalDate = date;
        detailData.forEach(detail -> {
            //第一列：固定位數+合同號碼+固定0+客戶名稱
            txtBuffer.append("\r\n000203900600000000");
            txtBuffer.append(detail.getCustomerContract());
            txtBuffer.append("000000000000000000000000");
            txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getCustomerName(), 40));
            //第二列：失敗金額+日期
            txtBuffer.append(YyStringUtils.addSpaceByRight(YyStringUtils.addZero(detail.getAmount(), 11), 35));
            txtBuffer.append(finalDate + finalDate);
        });

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getExportFileName("BAD"));

    }

    /**
     * 成功的总金额
     *
     * @param detailData
     * @return
     */
    private File exportAdvTxt(List<FovaUpdateDataPO> detailData) {
        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        String companyAccount = "";
        if (companyAgreementPO != null) {
            companyAccount = companyAgreementPO.getCompanyAccount();
        }

        FovaUpdateDataPO firstVO = detailData.get(0);

        String ccy = firstVO.getCurrType();

        StringBuffer txtBuffer = new StringBuffer();
        txtBuffer.append("\r\n");
        txtBuffer.append("                  *****ICBC - INDUSTRIAL AND COMMERCIAL BANK OF CHINA (MACAU BRANCH)*****");
        txtBuffer.append("\r\n\r\n\r\n");
        txtBuffer.append("TO : COMPANHIA DE ELECTRICIDADE DE MACAU (CEM)                   ADVICE DATE    : " + CCustomDate.getPrintDateTime());
        txtBuffer.append("\r\n\r\n");
        txtBuffer.append("                                                                 CCY            : " + ccy);
        txtBuffer.append("\r\n\r\n");
        txtBuffer.append("                                                                 ACCOUNT NUMBER : " + companyAccount);
        txtBuffer.append("\r\n\r\n\r\n\r\n");
        txtBuffer.append("PLEASE BE INFORMED THAT THE AMOUNT SHOWN BELOW IS CREDITED TO YOUR ACCOUNT " + companyAccount);
        txtBuffer.append("\r\n茲通知 - 以下款項已存入閣下之賬戶號碼 " + companyAccount);
        txtBuffer.append("\r\n\r\n\r\n\r\n");
        txtBuffer.append("AUTOPAY DATE 日期     PARTICULARS 摘要                            CREDIT MOVEMENT 存入");
        txtBuffer.append("\r\n");
        txtBuffer.append("-----------------     ---------------------------------------     --------------------");
        txtBuffer.append("\r\n");
        txtBuffer.append(YyStringUtils.addSpaceByRight(CCustomDate.getFormatDateDef(firstVO.getTranDate()), 22));
        txtBuffer.append("CEM AUTOPAY TRANSACTION ADVICE              ");
        txtBuffer.append(ccy);
        txtBuffer.append(YyStringUtils.addSpaceByLeft(getStringValue(totalMap, TOTAL_KEY_PAYMENTAMOUNT), 17));

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getExportFileName("ADV"));

    }


    /**
     * C、SHB-0902.PAY(成功)
     * Header
     * 固定位數+固定零 +ICBC+（日期+成功的筆數+成功的金額）
     * Detail（成功的詳細清單）
     * 第一列：00020290060000000+合同號碼+00000000+客戶名稱
     * 第二列：成功金額+日期
     *
     * @param detailData
     * @return
     */
    private File exportPayTxt(List<FovaUpdateDataPO> detailData) {
        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();
        //固定位數+固定0+ICBC+日期+筆數
        txtBuffer.append(YyStringUtils.addSpaceByRight("000002900600000000", 28));
        txtBuffer.append(YyStringUtils.addSpaceByRight("0000000000000000", 24));
        txtBuffer.append(YyStringUtils.addSpaceByRight("ICBC", 40));
//        txtBuffer.append("190902");
        String date = "datenull";
        if (StringUtils.isNotEmpty(transDate) && transDate.length() == 8) {
            date = transDate.substring(transDate.length() - 6, transDate.length());
        }
        txtBuffer.append(date);
        String goodDebit = getStringValue(totalMap, TOTAL_KEY_GOODDEBIT);
        txtBuffer.append(YyStringUtils.addZero(goodDebit, 6));
        //第二列：固定0
        String paymentAmount = getStringValue(totalMap, TOTAL_KEY_PAYMENTAMOUNT);
        txtBuffer.append(YyStringUtils.addZero(paymentAmount, 15));

        //表体
        String finalDate = date;
        detailData.forEach(detail -> {
            //第一列：固定位數+合同號碼+固定0+客戶名稱
            txtBuffer.append("\r\n000202900600000000");
            txtBuffer.append(detail.getCustomerContract());
            txtBuffer.append("000000000000000000000000");
            txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getCustomerName(), 40));
            //第二列：成功的金額+日期
            txtBuffer.append(YyStringUtils.addZero(detail.getAmount(), 11));
            txtBuffer.append(finalDate + finalDate);
        });

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getExportFileName("PAY"));

    }


    /**
     * @param detailData
     * @return
     */
    private File exportDelTxt(List<FovaUpdateDataPO> detailData) {
        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();
        //固定位數+固定0+ICBC+日期+筆數
        txtBuffer.append(YyStringUtils.addSpaceByRight("000004900600000000", 28));
        txtBuffer.append(YyStringUtils.addSpaceByRight("0000000000000000", 24));
        txtBuffer.append(YyStringUtils.addSpaceByRight("ICBC", 40));
//        txtBuffer.append("190902");
        if (StringUtils.isNotEmpty(transDate) && transDate.length() == 8) {
            txtBuffer.append(transDate.substring(transDate.length() - 6, transDate.length()));
        } else {
            txtBuffer.append("datenull");
        }

        String cancelserviec = getStringValue(totalMap, TOTAL_KEY_CANCELSERVICE);
        txtBuffer.append(YyStringUtils.addZero(cancelserviec, 6));
        //第二列：固定0
        txtBuffer.append("000000000000000");

        //表体
        detailData.forEach(detail -> {
            //第一列：固定位數+合同號碼+固定0+客戶名稱
            txtBuffer.append("\r\n000204900600000000");
            txtBuffer.append(detail.getCustomerContract());
            txtBuffer.append("000000000000000000000000");
            txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getCustomerName(), 40));
            //第二列：固定0
            txtBuffer.append("00000000000");

        });

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getExportFileName("DEL"));

    }

    /**
     * @param detailData
     * @return
     */
    private File exportNewTxt(List<FovaUpdateDataPO> detailData) {

            if (CollectionUtils.isEmpty(detailData)) {
                return null;
            }
            StringBuffer txtBuffer = new StringBuffer();
            //固定位數+固定0+ICBC+日期+筆數
            txtBuffer.append(YyStringUtils.addSpaceByRight("000001900600000000", 28));
            txtBuffer.append(YyStringUtils.addSpaceByRight("0000000000000000", 24));
            txtBuffer.append(YyStringUtils.addSpaceByRight("ICBC", 40));
//        txtBuffer.append("190902");
            if (StringUtils.isNotEmpty(transDate) && transDate.length() == 8) {
                txtBuffer.append(transDate.substring(transDate.length() - 6, transDate.length()));
            } else {
                txtBuffer.append("datenull");
            }
            String cancelserviec = getStringValue(totalMap, TOTAL_KEY_NEWSETUP);
            txtBuffer.append(YyStringUtils.addZero(cancelserviec, 6));
            //第二列：固定0
            txtBuffer.append("000000000000000NEWHDR");

            //表体
            detailData.forEach(detail -> {
                //第一列：固定位數+合同號碼+固定0+客戶名稱
                txtBuffer.append("\r\n000201900600000000");
                txtBuffer.append(detail.getCustomerContract());
                txtBuffer.append("000000000000000000000000");
                txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getCustomerName(), 40));
                //第二列：固定0
                txtBuffer.append("00000000000");

            });

            return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getExportFileName("NEW"));
    }

    /**
     * 导出交易记录 包括成功和失败，不包括NEW 和Cancel
     *
     * @param detailData
     * @return
     */
    private File exportPbrTxt(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();

        List<Map> pageDataList = getPageDataList(detailData, false);

        pageDataList.forEach(dataMap -> {
            txtBuffer.append("****ICBC****                                        SYS DATE " + getStringValue(dataMap, KEY_SYSDATETIME) + "      PAGE   " + getStringValue(dataMap, KEY_PAGE));
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("                         LIST OF COMPANHIA DE ELECTRICIDADE DE MACAU (CEM)  AUTO-PAY TRANSACTIONS");
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("AUTO-PAY DEBIT DATE: ".concat(getStringValue(dataMap, KEY_TRANDATE)));
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("CONTRACT NUMBER    PAYMENT AMOUNT    AMOUNT DUE    BILL DUE DATE    AUTOPAY DATE    ACTION           REJECT REASON");
            txtBuffer.append("\r\n");
            txtBuffer.append("***************    **************    **********    *************    ************    ******           *************");
            List<FovaUpdateDataPO> childList = dataMap.get(KEY_CHILDLIST) == null ? new ArrayList<FovaUpdateDataPO>()
                    : (List<FovaUpdateDataPO>) dataMap.get(KEY_CHILDLIST);
            childList.forEach(child -> {
                txtBuffer.append("\r\n");
                txtBuffer.append(YyStringUtils.addSpaceByLeft(child.getCustomerContract(), 15));
                //实时交易金额
                txtBuffer.append(YyStringUtils.addSpaceByLeft(AmountUtil.replaceTh(child.getBustAmt()), 18));
                //应处理金额
                txtBuffer.append(YyStringUtils.addSpaceByLeft(AmountUtil.replaceTh(child.getAmount()), 14));
                //单据日期
                txtBuffer.append(YyStringUtils.addSpaceByLeft(CCustomDate.getFormatDateDef(child.getSendDate()), 17));
                //交易日期
                txtBuffer.append(YyStringUtils.addSpaceByLeft(CCustomDate.getFormatDateDef(child.getTranDate()), 16));

                txtBuffer.append(YyStringUtils.addSpaceByLeft("", 4));
                //成功失败名称
                if (StringUtils.isNotEmpty(child.getStatus()) && child.getStatus().equals(FovaPayStatus.PAY.getStatusCode())) {
                    txtBuffer.append(YyStringUtils.addSpaceByRight(FovaPayStatus.PAY.getStatusName(), 17));
                } else {
                    txtBuffer.append(YyStringUtils.addSpaceByRight(FovaPayStatus.BAD.getStatusName(), 17));
                }
                //错误内容
                txtBuffer.append(YyStringUtils.toString(child.getErrMessage()));
            });
        });

        //Total
        txtBuffer.append("\r\n\r\n");
        txtBuffer.append("TOTAL");
        txtBuffer.append("\r\n");
        txtBuffer.append("***********************************************************************************************************");
        txtBuffer.append("\r\n");
        txtBuffer.append("AMOUNT DUE    GOOD DEBIT    PAYMENT AMOUNT    REJECT RECORD    REJECT AMOUNT    NEW SETUP    CANCEL SERVICE");
        txtBuffer.append("\r\n");
        txtBuffer.append("**********    **********    **************    *************    *************    *********    **************");
        txtBuffer.append("\r\n");
        txtBuffer.append(YyStringUtils.addSpaceByRight(AmountUtil.replaceTh(getStringValue(totalMap, TOTAL_KEY_AMOUNTDUE)), 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_GOODDEBIT), 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight(AmountUtil.replaceTh(getStringValue(totalMap, TOTAL_KEY_PAYMENTAMOUNT)), 18));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_REJECTRECORD), 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight(AmountUtil.replaceTh(getStringValue(totalMap, TOTAL_KEY_REJECTAMOUNT)), 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight("0", 13));
        txtBuffer.append(YyStringUtils.addSpaceByRight("0", 13));
        txtBuffer.append("\r\n\r\n                                                                                      *** END OF REPORT ***");

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getExportFileName("PBR"));

    }

    /**
     * 导出文件名称
     *
     * @param suffix
     * @return
     */
    private String getExportFileName(String suffix) {

        return getPath() + "SHB-" + transDateFileName + "." + suffix;
    }

    /**
     * 导出NDR
     *
     * @param detailData
     * @return
     */
    private File exportNdrTxt(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();
        List<Map> pageDataList = getPageDataList(detailData, false);

        pageDataList.forEach(dataMap -> {
            txtBuffer.append("****ICBC****                    SYS DATE ".concat(getStringValue(dataMap, KEY_SYSDATETIME)).concat("      PAGE   ").concat(getStringValue(dataMap, KEY_PAGE)));
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("               COMPANHIA DE ELECTRICIDADE DE MACAU (CEM)  APPLICATION TRANSACTIONS");
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("AUTO-PAY APPLICATION DATE: ".concat(getStringValue(dataMap, KEY_TRANDATE)));
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("CONTRACT NUMBER    PAYMENT AMOUNT    AMOUNT DUE    EXPIRY DATE    ACTION");
            txtBuffer.append("\r\n");
            txtBuffer.append("***************    **************    **********    ***********    ******");
            List<FovaUpdateDataPO> childList = dataMap.get(KEY_CHILDLIST) == null ? new ArrayList<FovaUpdateDataPO>()
                    : (List<FovaUpdateDataPO>) dataMap.get(KEY_CHILDLIST);
            childList.forEach(child -> {
                txtBuffer.append("\r\n");
                txtBuffer.append(YyStringUtils.addSpaceByLeft(child.getCustomerContract(), 15));
                //固定
                txtBuffer.append("              ----          ----           ----    ");
                //显示服务名称
                String customerAccount = child.getCustomerAccount();
                if (StringUtils.isNotBlank(customerAccount)) {
                    CusetomerAgreementPO custPo = customerMap.get(customerAccount);
                    if (custPo != null && StringUtils.isNotBlank(custPo.getServiceStatus())) {
                        if (custPo.getServiceStatus().equals(ServiceStatusEnums.NEW.getValue())) {
                            txtBuffer.append(ServiceStatusEnums.NEW.getName());
                        } else {
                            txtBuffer.append(ServiceStatusEnums.CANCEL.getName());
                        }
                    }
                }
            });

        });

        //Total
        txtBuffer.append("\r\n\r\n");
        txtBuffer.append("TOTAL");
        txtBuffer.append("\r\n");
        txtBuffer.append("***********************************************************************************************************");
        txtBuffer.append("\r\n");
        txtBuffer.append("AMOUNT DUE    GOOD DEBIT    PAYMENT AMOUNT    REJECT RECORD    REJECT AMOUNT    NEW SETUP    CANCEL SERVICE");
        txtBuffer.append("\r\n");
        txtBuffer.append("**********    **********    **************    *************    *************    *********    **************");
        txtBuffer.append("\r\n");
        txtBuffer.append("      0.00             0              0.00                0             0.00");
        txtBuffer.append(YyStringUtils.addSpaceByLeft(getStringValue(totalMap, TOTAL_KEY_NEWSETUP), 13));
        txtBuffer.append(YyStringUtils.addSpaceByLeft(getStringValue(totalMap, TOTAL_KEY_CANCELSERVICE), 18));
        txtBuffer.append("\r\n\r\n                                                        *** END OF REPORT ***");
        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getExportFileName("NDR"));


    }

}
