package com.yyicbc.utils.freemarker.pdf.manulife;

import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.enums.FovaPayStatus;
import com.yyicbc.beans.enums.ServiceStatusEnums;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.freemarker.pdf.AbstractPdfExport;
import com.yyicbc.utils.freemarker.pdf.PdfExportUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

/**
 * 导出ctm Pdf文件
 *
 * @author vic fu
 * @date 2020-01-07
 */
@Component
public class ManulifePdf extends AbstractPdfExport {


    @Override
    protected List exportPdfList(List<FovaUpdateDataPO> detailData) throws Exception {

        List fileList = new ArrayList<File>();
        //先進行匯總
        setAmountUnit(true);
        handleTotalMap(detailData);
        //导出Manulife 成功+失败
        File acceptFile = exportPdfFile(filterData(detailData, TYPE_NDR), getTemplateFile(), getPath() + getTemplateFile());
        if (acceptFile != null) {
            fileList.add(acceptFile);
        }
        return fileList;
    }

    @Override
    protected List exportTxtList(List<FovaUpdateDataPO> detailData) throws Exception {
        List fileList = new ArrayList<File>();
        setAmountUnit(false);
        //先進行匯總
        handleTotalMap(detailData);
        //导出所有
        File allFile = exportReturnTxt(detailData);
        if (allFile != null) {
            fileList.add(allFile);
        }
        return fileList;
    }

    /**
     * Header:
     * 扣賬日期+筆數（新申請的筆數） +筆數（取消）+筆數（成功）+金額（成功金額）+失敗筆數+失敗金額
     * Detail
     * 第一列：合同號碼
     * 第二列：賬號
     * 第三列：金額
     * 第四列：狀態（A成功、R01、R03、R04、R05、R06代表FOVA系統返回的其他;S新申請）
     * 注:如果為新申請，則金額為零和S是一列。
     *
     * @param detailData
     * @return
     */
    private File exportReturnTxt(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();
        //Header
        txtBuffer.append(detailData.get(0).getTranDate());
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_NEWSETUP), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_CANCELSERVICE), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_GOODDEBIT), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_PAYMENTAMOUNT), 12));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_REJECTRECORD), 6));
        txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_REJECTAMOUNT), 12));

        //Detail
        detailData.forEach(detail -> {

            txtBuffer.append("\r\n");
            txtBuffer.append(detail.getCustomerContract());
            txtBuffer.append(YyStringUtils.addSpaceByLeft("", 10));
            txtBuffer.append(detail.getCustomerAccount());
            txtBuffer.append(YyStringUtils.addSpaceByLeft("", 1));
            //交易状态
            String customerAccount = detail.getCustomerAccount();
            if (StringUtils.isNotBlank(customerAccount)) {
                CusetomerAgreementPO custPo = customerMap.get(customerAccount);
                if (custPo != null && StringUtils.isNotBlank(custPo.getServiceStatus()) &&
                        custPo.getServiceStatus().equals(ServiceStatusEnums.NEW.getValue())) {//new
                    txtBuffer.append("0000000000S");
                } else if (custPo != null && StringUtils.isNotBlank(custPo.getServiceStatus()) &&
                        custPo.getServiceStatus().equals(ServiceStatusEnums.CANCEL.getValue())) {//cancel
                    txtBuffer.append("0000000000C");
                } else {
                    if ((!StringUtils.isBlank(detail.getStatus()) && detail.getStatus().equals(FovaPayStatus.PAY.getStatusCode()))) {//PAY
                        txtBuffer.append(YyStringUtils.addZero(getAmountByUnit(detail.getAmount()), 10) + " A");
                    } else {//BAD
                        txtBuffer.append(YyStringUtils.addZero(getAmountByUnit(detail.getAmount()), 10) + " " + detail.getStatus());
                    }
                }
            }
        });

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + "ICBCRETURN.TXT");
    }

    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) throws Exception {
        return null;
    }

    protected String getTemplateFile() {
        return "Manulife";
    }

    /**
     * @param detailData
     * @return
     */
    protected File exportPdfFile(List<FovaUpdateDataPO> detailData, String templateFile, String targetPdfFile) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        Map data = new HashMap();
        //所有分頁後的明細數據
        data.put(RTN_KEY_DETAIL, getPageDataList(detailData, false));
        data.put(RTN_KEY_TOTAL, totalMap);
        return PdfExportUtil.exprotPdf(data, templateFile, targetPdfFile);
    }

    @Override
    public int getPageSize() {
        return 20;
    }

}
