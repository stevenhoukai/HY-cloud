package com.yyicbc.utils.freemarker.pdf.standard;

import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.CCustomDate;
import com.yyicbc.beans.utils.ExcelUtils;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.freemarker.pdf.PdfExportUtil;
import com.yyicbc.utils.freemarker.pdf.manulife.ManulifePdf;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameStandardExport
 * @Description 标准格式导出
 * @Author vic
 * @Date2020/3/2 15:11
 * @Version V1.0
 **/
@Component
public class StandardExport extends ManulifePdf {

    @Override
    protected List exportPdfList(List<FovaUpdateDataPO> detailData) throws Exception {
        List fileList = new ArrayList<File>();

        //处理合同号
        handleContract(detailData);
        //先進行匯總
        setAmountUnit(true);
        //汇总
        handleTotalMap(detailData);

        //根据公司编码和交易日期生成文件
        String targetName = getCompanyAgreementPO().getCompanyCode() + "-" + transDate;
        //包含所有数据

        String templateFile = getTemplateFile();

        if (!isout) {
            templateFile = "AIAHKD_IN";
        }

        File allFile = super.exportPdfFile(detailData, templateFile, getPath() + targetName);

        if (allFile != null) {
            fileList.add(allFile);
        }
        return fileList;
    }

    @Override
    protected List exportTxtList(List<FovaUpdateDataPO> detailData) throws Exception {
        List fileList = new ArrayList<File>();
        //先進行匯總
        setAmountUnit(true);

        handleTotalMap(detailData);

        //根据公司编码和交易日期生成文件
        String targetName = getCompanyAgreementPO().getCompanyCode() + "-" + transDate;
        Map data = new HashMap();
        //所有分頁後的明細數據
        data.put(RTN_KEY_TOTAL, totalMap);
        //交易日期
        //转成指定格式
        data.put(KEY_TRANDATE, CCustomDate.getFormatDateDef(transDate));
        //公司账号,companyAgreementPO.getCompanyAccount()
        data.put(KEY_ACCOUNT_NO, getCompanyAccount());
        data.put("company_name", getCompanyName());
        //币种
        data.put(KEY_CURRTYPE, currType);
        //公司代码
        data.put(KEY_COMPANY_CODE, getCompanyAgreementPO().getCompanyCode());

        //包含所有数据
        File allFile = PdfExportUtil.exprotPdf(data, "Customer", getPath() + targetName);

        if (allFile != null) {
            fileList.add(allFile);
        }
        return fileList;
    }

    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) throws Exception {
        List fileList = new ArrayList<File>();
        //先進行匯總
        setAmountUnit(true);
        handleTotalMap(detailData);

        //根据公司编码和交易日期生成文件
        String targetName = getCompanyAgreementPO().getCompanyCode() + "-" + transDate;
        //标准
        File excelFile = exportStandardExcel(detailData);

        addFileList(fileList, excelFile);

        return fileList;
    }

    /**
     * 功能描述: 标准excel导出
     *
     * @param detailData
     * @return: {@link File}
     * @Author: vic
     * @Date: 2020/3/13 9:28
     */
    private File exportStandardExcel(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        List pageList = getPageDataList(detailData, false);
        //根据公司编码和交易日期生成文件
        String targetName = getCompanyAgreementPO().getCompanyCode() + "-" + transDate;
        /***1、生成标题***/
        HSSFWorkbook workbook = new HSSFWorkbook();
        //1、先创建sheet
        HSSFSheet sheet = workbook.createSheet(targetName);
        sheet.setDefaultColumnWidth(20);
        int rownum = 0;
        int colnum = 0;
        HSSFRow row = sheet.createRow(rownum);
        //创建一个单元格
        HSSFCell cell;
        //数字样式
        HSSFCellStyle numberCellStyle = ExcelUtils.getNumberCellStyle(workbook, false, 0, false, 10, false, false, false, true, false);
        //标题居中
        HSSFCellStyle centerCellStyle = ExcelUtils.getStrCellStyleAlign(workbook, false, 0, true, 10, false, false, false, false, VerticalAlignment.CENTER, HorizontalAlignment.CENTER);
        HSSFCellStyle rightCellStyle = ExcelUtils.getStrCellStyleAlign(workbook, false, 0, true, 10, false, false, false, false, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
        HSSFCellStyle leftCellStyle = ExcelUtils.getStrCellStyleAlign(workbook, false, 0, false, 10, false, false, false, false, VerticalAlignment.CENTER, HorizontalAlignment.LEFT);

        //列样式
        HSSFCellStyle colCellStyle = ExcelUtils.getStrCellStyleAlign(workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), true, 10, true, true, true, true, VerticalAlignment.CENTER, HorizontalAlignment.CENTER);

        //表体单元格样式
        HSSFCellStyle bLeftCellstyle = ExcelUtils.getStrCellStyleAlign(workbook, false, 0, false, 10, false, false, false, true, VerticalAlignment.CENTER, HorizontalAlignment.LEFT);
        HSSFCellStyle bRightCellstyle = ExcelUtils.getStrCellStyleAlign(workbook, false, 0, false, 10, false, false, false, true, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);


        //合计样式
        HSSFCellStyle tRightCellStyle = ExcelUtils.getStrCellStyleAlign(workbook, false, 0, true, 10, false, false, true, false, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
        //有下边框
        HSSFCellStyle tRightCellStyle2 = ExcelUtils.getStrCellStyleAlign(workbook, false, 0, true, 10, false, false,false , true, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);

        //创建标题
        cell = row.createCell(colnum);
        cell.setCellValue("INDUSTRIAL AND COMMERCIAL BANK OF CHINA (MACAU) LIMITED.");
        cell.setCellStyle(centerCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, colnum, 5));
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(colnum);
        cell.setCellValue("AUTO-PAYMENT OF " + getCompanyAgreementPO().getCompanyName() + " INTERNATIONAL LIMITED");
        cell.setCellStyle(centerCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, colnum, 5));
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(colnum);
        cell.setCellValue("ACCEPTED AND REJECTED TRANSACTIONS REPORT");
        cell.setCellStyle(centerCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, colnum, 5));
        rownum++;
        row = sheet.createRow(rownum);
        colnum = 4;
        cell = row.createCell(colnum);
        cell.setCellValue("PAYMENT DATE :");
        cell.setCellStyle(rightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue(CCustomDate.getFormatDateDef(transDate));

        rownum++;
        row = sheet.createRow(rownum);
        colnum = 4;
        cell = row.createCell(colnum);
        cell.setCellValue("CR ACCOUNT NO:");
        cell.setCellStyle(rightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue(getCompanyAccount());

        //创建表头
        rownum++;
        row = sheet.createRow(rownum);
        colnum = 0;
        String[] heads = new String[]{"POLICY NO REMARK", "ACCOUNT NO", "ACCOUNT NAME", "CCY", "DR AMOUNT", "RESULT DESC"};
        for (String head : heads) {
            cell = row.createCell(colnum);
            cell.setCellValue(head);
            cell.setCellStyle(colCellStyle);
            colnum++;
        }

        //穿件标题
        for (FovaUpdateDataPO detail : detailData) {
            rownum++;
            row = sheet.createRow(rownum);
            colnum = 0;
            cell = row.createCell(colnum);
            cell.setCellStyle(bLeftCellstyle);
            cell.setCellValue(detail.getCustomerContract());
            colnum++;
            cell = row.createCell(colnum);
            cell.setCellStyle(bLeftCellstyle);
            cell.setCellValue(detail.getCustomerAccount());
            colnum++;
            cell = row.createCell(colnum);
            cell.setCellStyle(bLeftCellstyle);
            cell.setCellValue(detail.getCustomerName());
            colnum++;
            cell = row.createCell(colnum);
            cell.setCellStyle(bLeftCellstyle);
            cell.setCellValue(detail.getCurrType());
            colnum++;
            cell = row.createCell(colnum);
            cell.setCellStyle(bRightCellstyle);
            cell.setCellValue(getAmountByUnit(detail.getAmount()));
            colnum++;
            cell = row.createCell(colnum);
            cell.setCellStyle(bLeftCellstyle);
            cell.setCellValue(detail.getErrMessageDsf());
        }

        rownum++;
        rownum++;
        rownum++;
        row = sheet.createRow(rownum);
        colnum = 0;
        cell = row.createCell(colnum);
        cell.setCellValue("TOTAL REC");
        cell.setCellStyle(tRightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue("TOTAL PAYMENT AMOUNT");
        cell.setCellStyle(tRightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue("ACCEPTED REC");
        cell.setCellStyle(tRightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue("ACCEPTED AMOUNT");
        cell.setCellStyle(tRightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue("REJECTED REC");
        cell.setCellStyle(tRightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue("REJECTED AMOUNT");
        cell.setCellStyle(tRightCellStyle);
        //合计值
        rownum++;
        row = sheet.createRow(rownum);
        colnum = 0;
        cell = row.createCell(colnum);
        cell.setCellValue(YyStringUtils.toString(totalMap.get(TOTAL_KEY_RECORD)));
        cell.setCellStyle(tRightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue(YyStringUtils.toString(totalMap.get(TOTAL_KEY_AMOUNTDUE)));
        cell.setCellStyle(tRightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue(YyStringUtils.toString(totalMap.get(TOTAL_KEY_GOODDEBIT)));
        cell.setCellStyle(tRightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue(YyStringUtils.toString(totalMap.get(TOTAL_KEY_PAYMENTAMOUNT)));
        cell.setCellStyle(tRightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue(YyStringUtils.toString(totalMap.get(TOTAL_KEY_REJECTRECORD)));
        cell.setCellStyle(tRightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue(YyStringUtils.toString(totalMap.get(TOTAL_KEY_REJECTAMOUNT)));
        cell.setCellStyle(tRightCellStyle);

        //签名
        rownum++;
        rownum++;
        rownum++;
        row = sheet.createRow(rownum);
        colnum=0;
        cell = row.createCell(colnum);
        cell.setCellValue("Enter :");
        cell.setCellStyle(rightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue("");
        cell.setCellStyle(tRightCellStyle2);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue("Checker :");
        cell.setCellStyle(rightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue("");
        cell.setCellStyle(tRightCellStyle2);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue("Approval :");
        cell.setCellStyle(rightCellStyle);
        colnum++;
        cell = row.createCell(colnum);
        cell.setCellValue("");
        cell.setCellStyle(tRightCellStyle2);

        return ExportDownLoad.genereateExcel(workbook, getPath() + targetName);
    }

    @Override
    protected String getTemplateFile() {
        return "AIAHKD";
    }
}
