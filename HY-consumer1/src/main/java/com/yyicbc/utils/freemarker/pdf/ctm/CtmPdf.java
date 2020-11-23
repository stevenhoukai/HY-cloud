package com.yyicbc.utils.freemarker.pdf.ctm;

import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.freemarker.pdf.AbstractPdfExport;
import com.yyicbc.utils.freemarker.pdf.PdfExportUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

/**
 * 导出ctm Pdf文件
 *
 * @author vic fu
 * @date 2020-01-07
 */
@Component
public class CtmPdf extends AbstractPdfExport {

    /**
     * 导出多个PDF
     *
     * @param detailData
     * @return
     */
    @Override
    public List exportPdfList(List<FovaUpdateDataPO> detailData) {

        List fileList = new ArrayList<File>();
        setAmountUnit(true);
        //先進行匯總
        handleTotalMap(detailData);
        //导出pay
        File payFile = exportPayPdf(filterData(detailData, TYPE_PAY));
        //导出bad
        File badFile = exportBadPdf(filterData(detailData, TYPE_BAD));
        //导出new
        File newFile = exportNewPdf(filterData(detailData, TYPE_NEW));
        //导出cancel
        File cancelFile = exportCancelPdf(filterData(detailData, TYPE_CANCEL));

        addFileList(fileList, payFile);
        addFileList(fileList, badFile);
        addFileList(fileList, newFile);
        addFileList(fileList, cancelFile);

        return fileList;
    }

    /**
     * 导出多个TXT
     *
     * @param detailData
     * @return
     */
    @Override
    public List exportTxtList(List<FovaUpdateDataPO> detailData) {

        List fileList = new ArrayList<File>();
        setAmountUnit(false);
        //先進行匯總
        handleTotalMap(detailData);
        //导出pay
        File payFile = exportPayTxt(filterData(detailData, TYPE_PAY));
        //导出bad
        File badFile = exportBadTxt(filterData(detailData, TYPE_BAD));
        //导出bad UTYREJPF
        File badUtyrejpfFile = exportBadTxtUtyrejpf(filterData(detailData, TYPE_BAD));

        addFileList(fileList, payFile);
        addFileList(fileList, badFile);
        addFileList(fileList, badUtyrejpfFile);

        return fileList;
    }

    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) {
        return null;
    }



    /**
     * @param detailData
     * @return
     */
    private File exportBadPdf(List<FovaUpdateDataPO> detailData) {

        Map data = getBadData(detailData);

        if (data == null || data.size() == 0) {
            return null;
        }

        return PdfExportUtil.exprotPdf(data, "CTM-BAD", getPath() + "ICBC" + transDateFileName + ".BAD");
    }

    /**
     * @param detailData
     * @return
     */
    private File exportNewPdf(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        Map data = new HashMap();
        //所有分頁後的明細數據
        data.put(RTN_KEY_DETAIL, getPageDataList(detailData, false));
        //合計值
        data.put(RTN_KEY_TOTAL, totalMap);
        return PdfExportUtil.exprotPdf(data, "CTM-NEWAPPLICANTS", getPath() + "NEWAPPLICANTS-" + transDateFileName);

    }

    /**
     * @param detailData
     * @return
     */
    private File exportCancelPdf(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        Map data = new HashMap();
        //所有分頁後的明細數據
        data.put(RTN_KEY_DETAIL, getPageDataList(detailData, false));
        //合計值
        data.put(RTN_KEY_TOTAL, totalMap);
        return PdfExportUtil.exprotPdf(data, "CTM-CANCELLATIONS", getPath() + "CANCELLATIONS-" + transDateFileName);

    }


    /**
     * @param detailData
     * @return
     */
    private File exportPayPdf(List<FovaUpdateDataPO> detailData) {
        Map data = getPayData(detailData);
        if (data == null || data.size() == 0) {
            return null;
        }
        return PdfExportUtil.exprotPdf(data, "CTM-PAY", getPath() + "ICBC" + transDateFileName + ".PAY");
    }


    /**
     * 导出txt
     *
     * @param detailData
     * @return
     */
    private File exportPayTxt(List<FovaUpdateDataPO> detailData) {
        Map data = getPayData(detailData);
        if (data == null || data.size() == 0) {
            return null;
        }

        StringBuffer txtBuffer = new StringBuffer();

        List<Map> pageDataList = data.get(RTN_KEY_DETAIL) == null ? null : (List<Map>) data.get(RTN_KEY_DETAIL);
        Map totalMap = (Map) data.get(RTN_KEY_TOTAL);

        pageDataList.forEach(dataMap -> {
            txtBuffer.append("****ICBC****");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("SYS DATE ".concat(getStringValue(dataMap, KEY_SYSDATETIME)), 49));
            txtBuffer.append(YyStringUtils.addSpaceByLeft("PAGE   ".concat(getStringValue(dataMap, KEY_PAGE)), 38));
            txtBuffer.append("\r\n");
            txtBuffer.append("\r\n");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("INDUSTRIAL AND COMMERCIAL BANK OF CHINA (MACAU) LIMITED.", 81));
            txtBuffer.append("\r\n");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("AUTO-PAY -COM. DE TELE. DE MACAU S.A.R.L.", 71));
            txtBuffer.append("\r\n");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("ACCEPTED TRANSACTIONS REPORT", 63));
            txtBuffer.append("\r\n");
            txtBuffer.append("COMPANY ID :COM. DE TELE. DE MA");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("DEBIT DATE: ".concat(getStringValue(dataMap, KEY_TRANDATE)), 62));
            txtBuffer.append("\r\n");
            txtBuffer.append(YyStringUtils.addSpaceByRight("CONTRACT NUMBER", 15));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append(YyStringUtils.addSpaceByRight("PAYMENT AMOUNT", 14));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append(YyStringUtils.addSpaceByRight("DR ACCOUNT NO", 19));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append("A/C NAME");
            txtBuffer.append("\r\n");
            txtBuffer.append(YyStringUtils.addSpaceByRight("***************", 15));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append(YyStringUtils.addSpaceByRight("**************", 14));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append(YyStringUtils.addSpaceByRight("*******************", 19));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append("*********************************************");

            List<FovaUpdateDataPO> childList = dataMap.get(KEY_CHILDLIST) == null ? new ArrayList<FovaUpdateDataPO>()
                    : (List<FovaUpdateDataPO>) dataMap.get(KEY_CHILDLIST);

            childList.forEach(child -> {
                txtBuffer.append("\r\n");
                txtBuffer.append(YyStringUtils.addSpaceByRight(child.getCustomerContract(), 15));
                txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
                txtBuffer.append(YyStringUtils.addSpaceByLeft(child.getAmount(), 14));
                txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
                txtBuffer.append(YyStringUtils.addSpaceByRight(child.getCustomerAccount(), 19));
                txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
                txtBuffer.append(child.getCustomerName());
            });

        });

        //Total
        txtBuffer.append("\r\n\r\n");
        txtBuffer.append("TOTAL");
        txtBuffer.append("\r\n");
        txtBuffer.append("***********************************************************************************************************");
        txtBuffer.append("\r\n");
        txtBuffer.append(YyStringUtils.addSpaceByRight("AMOUNT DUE", 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight("GOOD DEBIT", 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight("PAYMENT AMOUNT", 18));
        txtBuffer.append(YyStringUtils.addSpaceByRight("REJECT RECORD", 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight("REJECT AMOUNT", 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight("NEW SETUP", 13));
        txtBuffer.append(YyStringUtils.addSpaceByRight("CANCEL SERVICE", 13));

        txtBuffer.append("\r\n");
        txtBuffer.append(YyStringUtils.addSpaceByRight("**********", 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight("**********", 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight("**************", 18));
        txtBuffer.append(YyStringUtils.addSpaceByRight("*************", 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight("*************", 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight("*********", 13));
        txtBuffer.append(YyStringUtils.addSpaceByRight("**************", 13));

        txtBuffer.append("\r\n");
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_AMOUNTDUE), 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_GOODDEBIT), 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_PAYMENTAMOUNT), 18));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_REJECTRECORD), 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_REJECTAMOUNT), 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_NEWSETUP), 13));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_CANCELSERVICE), 13));

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + "ICBC" + transDateFileName + ".PAY");

    }

    /**
     * 导出 BADtxt
     *
     * @param detailData
     * @return
     */
    private File exportBadTxt(List<FovaUpdateDataPO> detailData) {
        Map data = getBadData(detailData);
        if (data == null || data.size() == 0) {
            return null;
        }

        StringBuffer txtBuffer = new StringBuffer();

        List<Map> pageDataList = data.get(RTN_KEY_DETAIL) == null ? null : (List<Map>) data.get(RTN_KEY_DETAIL);
        Map totalMap = (Map) data.get(RTN_KEY_TOTAL);

        pageDataList.forEach(dataMap -> {
            txtBuffer.append("****ICBC****");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("SYS DATE ".concat(getStringValue(dataMap, KEY_SYSDATETIME)), 49));
            txtBuffer.append(YyStringUtils.addSpaceByLeft("PAGE   ".concat(getStringValue(dataMap, KEY_PAGE)), 38));
            txtBuffer.append("\r\n");
            txtBuffer.append("\r\n");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("INDUSTRIAL AND COMMERCIAL BANK OF CHINA (MACAU) LIMITED.", 81));
            txtBuffer.append("\r\n");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("AUTO-PAY -COM. DE TELE. DE MACAU S.A.R.L.", 71));
            txtBuffer.append("\r\n");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("REJECTED TRANSACTIONS REPORT", 63));
            txtBuffer.append("\r\n");
            txtBuffer.append("COMPANY ID :COM. DE TELE. DE MA");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("DEBIT DATE: ".concat(getStringValue(dataMap, KEY_TRANDATE)), 62));
            txtBuffer.append("\r\n");
            txtBuffer.append(YyStringUtils.addSpaceByRight("CONTRACT NUMBER", 15));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append(YyStringUtils.addSpaceByRight("PAYMENT AMOUNT", 14));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append(YyStringUtils.addSpaceByRight("A/C NAME", 45));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append("REMARK");
            txtBuffer.append("\r\n");
            txtBuffer.append(YyStringUtils.addSpaceByRight("***************", 15));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append(YyStringUtils.addSpaceByRight("**************", 14));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append(YyStringUtils.addSpaceByRight("*********************************************", 45));
            txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
            txtBuffer.append("******************************");

            List<FovaUpdateDataPO> childList = dataMap.get(KEY_CHILDLIST) == null ? new ArrayList<FovaUpdateDataPO>()
                    : (List<FovaUpdateDataPO>) dataMap.get(KEY_CHILDLIST);

            childList.forEach(child -> {
                txtBuffer.append("\r\n");
                txtBuffer.append(YyStringUtils.addSpaceByRight(child.getCustomerContract(), 15));
                txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
                txtBuffer.append(YyStringUtils.addSpaceByLeft(child.getAmount(), 14));
                txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
                txtBuffer.append(YyStringUtils.addSpaceByRight(child.getCustomerName(), 45));
                txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
                txtBuffer.append(YyStringUtils.toString(child.getErrMessage()));
            });

        });

        //Total
        txtBuffer.append("\r\n\r\n");
        txtBuffer.append("TOTAL");
        txtBuffer.append("\r\n");
        txtBuffer.append("***********************************************************************************************************");
        txtBuffer.append("\r\n");
        txtBuffer.append(YyStringUtils.addSpaceByRight("AMOUNT DUE", 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight("GOOD DEBIT", 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight("PAYMENT AMOUNT", 18));
        txtBuffer.append(YyStringUtils.addSpaceByRight("REJECT RECORD", 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight("REJECT AMOUNT", 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight("NEW SETUP", 13));
        txtBuffer.append(YyStringUtils.addSpaceByRight("CANCEL SERVICE", 13));

        txtBuffer.append("\r\n");
        txtBuffer.append(YyStringUtils.addSpaceByRight("**********", 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight("**********", 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight("**************", 18));
        txtBuffer.append(YyStringUtils.addSpaceByRight("*************", 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight("*************", 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight("*********", 13));
        txtBuffer.append(YyStringUtils.addSpaceByRight("**************", 13));

        txtBuffer.append("\r\n");
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_AMOUNTDUE), 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_GOODDEBIT), 14));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_PAYMENTAMOUNT), 18));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_REJECTRECORD), 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_REJECTAMOUNT), 17));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_NEWSETUP), 13));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getStringValue(totalMap, TOTAL_KEY_CANCELSERVICE), 13));

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + "ICBC" + transDateFileName + ".BAD");

    }


    /**
     * 导出失败的UTYREJPF.txt
     *
     * @param detailData
     * @return
     */
    private File exportBadTxtUtyrejpf(List<FovaUpdateDataPO> detailData) {
        Map data = getBadData(detailData);
        if (data == null || data.size() == 0) {
            return null;
        }

        StringBuffer txtBuffer = new StringBuffer();
        List<Map> pageDataList = data.get(RTN_KEY_DETAIL) == null ? null : (List<Map>) data.get(RTN_KEY_DETAIL);
        Map totalMap = (Map) data.get(RTN_KEY_TOTAL);

        txtBuffer.append("IN :TMFDEBTORS SHBREVERSAL");
        //第二行：zs000102 + 日期+筆數+金額

        txtBuffer.append("\r\nzs000102".concat(transDateFileName)
                .concat(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_REJECTRECORD), 4))
                .concat(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_REJECTAMOUNT), 10)));
        detailData.forEach(detail -> {
            //固定（FS0001fs）+合同號碼（13位）+金額
            txtBuffer.append("\r\n");
            txtBuffer.append("FS0001fs" + (detail.getCustomerContract()) + (detail.getAmount()));
        });
        txtBuffer.append("\r\n****");

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + "UTYREJPF");
    }

    /**
     * @param detailData
     * @return
     */
    private Map getBadData(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        Map data = new HashMap();
        //所有分頁後的明細數據
        data.put(RTN_KEY_DETAIL, getPageDataList(detailData, false));
        //合計值
        Map payMap = new HashMap();
        payMap.put(TOTAL_KEY_AMOUNTDUE, totalMap.get(TOTAL_KEY_AMOUNTDUE));
        payMap.put(TOTAL_KEY_GOODDEBIT, 0);
        payMap.put(TOTAL_KEY_PAYMENTAMOUNT, getAmountByUnit(0));
        payMap.put(TOTAL_KEY_REJECTRECORD, totalMap.get(TOTAL_KEY_REJECTRECORD));
        payMap.put(TOTAL_KEY_REJECTAMOUNT, totalMap.get(TOTAL_KEY_REJECTAMOUNT));
        payMap.put(TOTAL_KEY_NEWSETUP, 0);
        payMap.put(TOTAL_KEY_CANCELSERVICE, 0);
        data.put(RTN_KEY_TOTAL, payMap);
        return data;
    }

    /**
     * @param detailData
     * @return
     */
    private Map getPayData(List<FovaUpdateDataPO> detailData) {
        Map data = new HashMap();
        if (CollectionUtils.isEmpty(detailData)) {
            return data;
        }

        //所有分頁後的明細數據
        data.put(RTN_KEY_DETAIL, getPageDataList(detailData, false));
        //合計值
        Map payMap = new HashMap();
        payMap.put(TOTAL_KEY_AMOUNTDUE, totalMap.get(TOTAL_KEY_AMOUNTDUE));
        payMap.put(TOTAL_KEY_GOODDEBIT, totalMap.get(TOTAL_KEY_GOODDEBIT));
        payMap.put(TOTAL_KEY_PAYMENTAMOUNT, totalMap.get(TOTAL_KEY_PAYMENTAMOUNT));
        payMap.put(TOTAL_KEY_REJECTRECORD, 0);
        payMap.put(TOTAL_KEY_REJECTAMOUNT, getAmountByUnit(0));
        payMap.put(TOTAL_KEY_NEWSETUP, 0);
        payMap.put(TOTAL_KEY_CANCELSERVICE, 0);
        data.put(RTN_KEY_TOTAL, payMap);

        return data;
    }

    @Override
    public int getPageSize() {
        return 29;
    }

}

