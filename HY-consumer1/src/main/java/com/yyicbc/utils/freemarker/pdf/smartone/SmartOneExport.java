package com.yyicbc.utils.freemarker.pdf.smartone;

import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.enums.FovaPayStatus;
import com.yyicbc.beans.enums.ServiceStatusEnums;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.AmountUtil;
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
 * 功能描述:數碼通
 *
 * @Author: vic
 * @Date: 2020/2/26 17:10
 */
@Component
public class SmartOneExport extends AbstractPdfExport {

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
        //SmartOne  包括所有
        File smtFile = exportSmtTxt(detailData, "ICBC_"+transDate+"_rst");

        addFileList(fileList, smtFile);

        return fileList;
    }

    /*功能描述:
     * @param detailData
     * @param fileName
     * @param type
     * @return: {@link java.io.File}
     * @Author: vic
     * @Date: 2020/2/26 17:07
     */
    private File exportSmtTxt(List<FovaUpdateDataPO> detailData, String fileName) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();
        //Header
        txtBuffer.append(detailData.get(0).getTranDate());
        txtBuffer.append("0000");
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_NEWSETUP), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_CANCELSERVICE), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_GOODDEBIT), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_PAYMENTAMOUNT), 10));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_REJECTRECORD), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_REJECTAMOUNT), 10));

        //Detail
        detailData.forEach(detail -> {
            txtBuffer.append("\r\r\n");
            txtBuffer.append(YyStringUtils.addZero(detail.getCustomerContract(), 14));
            //交易状态
            String customerAccount = detail.getCustomerAccount();
            if (StringUtils.isNotBlank(customerAccount)) {
                CusetomerAgreementPO custPO = customerMap.get(customerAccount);
                if (custPO != null && StringUtils.isNotBlank(custPO.getServiceStatus()) &&
                        custPO.getServiceStatus().equals(ServiceStatusEnums.NEW.getValue())) {//new
                    txtBuffer.append("00000000S");
                } else if (custPO != null && StringUtils.isNotBlank(custPO.getServiceStatus()) &&
                        custPO.getServiceStatus().equals(ServiceStatusEnums.CANCEL.getValue())) {//cancel
                    txtBuffer.append("00000000 C");
                } else {
                    txtBuffer.append(YyStringUtils.addZero(detail.getAmount(), 8));
                    if ((!StringUtils.isBlank(detail.getStatus()) && detail.getStatus().equals(FovaPayStatus.PAY.getStatusCode()))) {//PAY
                        txtBuffer.append("  G");
                    } else {//BAD
                        txtBuffer.append("   R"+detail.getErrNo());
                    }
                }
            }
        });

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + fileName);
    }

    /**
     * 导出New
     *
     * @param detailData
     * @return
     */
    private File exportNewTxt(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();
        List<Map> pageDataList = getPageDataList(detailData, false);

        pageDataList.forEach(dataMap -> {
            txtBuffer.append("****ICBC****                                        SYS DATE ".concat(getStringValue(dataMap, KEY_SYSDATETIME)).concat("      PAGE   ").concat(getStringValue(dataMap, KEY_PAGE)));
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("               LIST OF SAAM AUTO-PAY TRANSACTIONS");
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("AUTO-PAY DEBIT DATE: ".concat(getStringValue(dataMap, KEY_TRANDATE)));
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("CONTRACT NUMBER    PAYMENT AMOUNT    AMOUNT DUE    EXPIRY DATE    ACTION");
            txtBuffer.append("\r\n");
            txtBuffer.append("***************    **************    **********    ***********    ******");
            List<FovaUpdateDataPO> childList = dataMap.get(KEY_CHILDLIST) == null ? new ArrayList<FovaUpdateDataPO>()
                    : (List<FovaUpdateDataPO>) dataMap.get(KEY_CHILDLIST);
            childList.forEach(child -> {
                txtBuffer.append("\r\n");
                txtBuffer.append(YyStringUtils.addSpaceByRight(child.getCustomerContract(), 15));
                //固定
                txtBuffer.append("             ----          ----           ----     ");
                //显示服务名称
                String customerAccount = child.getCustomerAccount();
                if (StringUtils.isNotBlank(customerAccount)) {
                    CusetomerAgreementPO custPO = customerMap.get(customerAccount);
                    if (custPO != null && StringUtils.isNotBlank(custPO.getServiceStatus())) {
                        if (custPO.getServiceStatus().equals(ServiceStatusEnums.NEW.getValue())) {
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
        txtBuffer.append("0.00          0             0.00              0                0.00             ");
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_NEWSETUP), 9));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_CANCELSERVICE), 18));
        txtBuffer.append("\r\n\r\n                                                  *** END OF REPORT ***");
        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + "ICBC-NEW_" + transDateFileName6 + ".DAT");


    }

    /**
     * 导出交易记录 包括成功和失败，不包括NEW 和Cancel
     *
     * @param detailData
     * @return
     */
    private File exportAcceptTxt(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();

        List<Map> pageDataList = getPageDataList(detailData, false);

        pageDataList.forEach(dataMap -> {
            txtBuffer.append("****ICBC****                                        SYS DATE " + getStringValue(dataMap, KEY_SYSDATETIME) + "      PAGE   " + getStringValue(dataMap, KEY_PAGE));
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("                         LIST OF MACAO WATER AUTO-PAY TRANSACTIONS");
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("AUTO-PAY DEBIT DATE: ".concat(getStringValue(dataMap, KEY_TRANDATE)));
            txtBuffer.append("\r\n\r\n");
            txtBuffer.append("POLICY NUMBER    PAYMENT AMOUNT    AMOUNT DUE    EXPIRY DATE    ACTION");
            txtBuffer.append("\r\n");
            txtBuffer.append("*************    **************    **********    ***********    ******");
            List<FovaUpdateDataPO> childList = dataMap.get(KEY_CHILDLIST) == null ? new ArrayList<FovaUpdateDataPO>()
                    : (List<FovaUpdateDataPO>) dataMap.get(KEY_CHILDLIST);
            childList.forEach(child -> {
                txtBuffer.append("\r\n");
                txtBuffer.append(YyStringUtils.addSpaceByRight(child.getCustomerContract(), 17));
                //实时交易金额
                txtBuffer.append(YyStringUtils.addSpaceByLeft(AmountUtil.replaceTh(child.getBustAmt()), 14));
                //应处理金额
                txtBuffer.append(YyStringUtils.addSpaceByLeft(AmountUtil.replaceTh(child.getAmount()), 14));
                //单据日期
                txtBuffer.append(YyStringUtils.addSpaceByLeft("----", 15));
                txtBuffer.append(YyStringUtils.addSpaceByLeft("", 4));
                //成功失败名称
                if (StringUtils.isNotEmpty(child.getStatus())
                        && child.getStatus().equals(FovaPayStatus.PAY.getStatusCode())) {
                    txtBuffer.append(FovaPayStatus.PAY.getStatusName());
                } else {
                    txtBuffer.append(FovaPayStatus.BAD.getStatusName());
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
        txtBuffer.append(YyStringUtils.addSpaceByRight(AmountUtil.replaceTh(getStringValue(totalMap, TOTAL_KEY_AMOUNTDUE)), 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_GOODDEBIT), 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight(AmountUtil.replaceTh(getStringValue(totalMap, TOTAL_KEY_PAYMENTAMOUNT)), 18));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_REJECTRECORD), 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight(AmountUtil.replaceTh(getStringValue(totalMap, TOTAL_KEY_REJECTAMOUNT)), 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight("0", 13));
        txtBuffer.append(YyStringUtils.addSpaceByRight("0", 13));
        txtBuffer.append("\r\n\r\n                                                                                      *** END OF REPORT ***");

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + "ICBC-ACCEPT_" + transDateFileName6 + ".DAT");

    }

    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) {
        return null;
    }
}
