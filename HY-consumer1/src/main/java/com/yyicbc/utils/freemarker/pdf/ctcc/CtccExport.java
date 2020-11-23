package com.yyicbc.utils.freemarker.pdf.ctcc;

import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.enums.FovaPayStatus;
import com.yyicbc.beans.enums.ServiceStatusEnums;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.freemarker.pdf.AbstractPdfExport;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassNameCtccExport
 * @Description 中国电信
 * @Author vic
 * @Date2020/2/26 17:39
 * @Version V1.0
 **/
@Component
public class CtccExport extends AbstractPdfExport {


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
        //Ctcc  包括所有
        File smtFile = exportCtccTxt(detailData, "A0158OUT");

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
    private File exportCtccTxt(List<FovaUpdateDataPO> detailData, String fileName) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();
        //Header
        txtBuffer.append(transDate);
        txtBuffer.append("0158");
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_NEWSETUP), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_CANCELSERVICE), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_GOODDEBIT), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_PAYMENTAMOUNT), 10));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_REJECTRECORD), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_REJECTAMOUNT), 10));

        //Detail
        detailData.forEach(detail -> {
            txtBuffer.append("\r\n");
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
                    txtBuffer.append(YyStringUtils.addZero(getAmountByUnit(detail.getAmount()), 8));
                    if ((!StringUtils.isBlank(detail.getStatus()) && detail.getStatus().equals(FovaPayStatus.PAY.getStatusCode()))) {//PAY
                        txtBuffer.append("  G");
                    } else {//BAD
                        txtBuffer.append("   R");
                    }
                }
            }
        });

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + fileName);
    }
    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) {
        return null;
    }
}
