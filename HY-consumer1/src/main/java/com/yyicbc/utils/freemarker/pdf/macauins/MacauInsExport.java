package com.yyicbc.utils.freemarker.pdf.macauins;

import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.ExcelUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.freemarker.pdf.AbstractPdfExport;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassNameMacauInsExport
 * @Description 3.4.6 Macau Ins（澳門保險）
 * @Author vic
 * @Date2020/2/28 10:01
 * @Version V1.0
 **/
@Component
public class MacauInsExport extends AbstractPdfExport {
    @Override
    protected List exportPdfList(List<FovaUpdateDataPO> detailData) throws Exception {
        return null;
    }


    @Override
    protected List exportTxtList(List<FovaUpdateDataPO> detailData) throws Exception {
        return null;
    }


    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) throws Exception {
        List fileList = new ArrayList<File>();
        //先進行匯總
        setAmountUnit(true);
        handleTotalMap(detailData);
        //成功
        File accpetFile = exportAcceptExcel(filterData(detailData, TYPE_PAY), TYPE_PAY);
        //失败
        File rejectFile = exportAcceptExcel(filterData(detailData, TYPE_NEW), TYPE_NEW);

        addFileList(fileList, accpetFile);

        addFileList(fileList, rejectFile);

        return fileList;
    }


    /**
     * 功能描述:
     * 1.	MAC_ACCEPT20110211（Excel成功返回給客戶的格式）
     * Remark6(舊的賬號)+icbc_ac_no（新賬號）+icbc_ac_nam(客戶名稱)+txn_ccy(幣種)+txn_amt(金額)+ rxn_ind(G成功)+remarks
     * 2.	MAC_REJECT20110211（Excel失敗返回給客戶的格式）
     * Remark6(舊的賬號)+icbc_ac_no（新賬號）+icbc_ac_nam(客戶名稱)+txn_ccy(幣種)+txn_amt(金額)+ rxn_ind(失敗標誌)+remarks
     *
     * @param detailData
     * @param type
     * @return: {@link File}
     * @Author: vic
     * @Date: 2020/2/28 10:03
     */
    private File exportAcceptExcel(List<FovaUpdateDataPO> detailData, int type) {
        //创建表头
        String[] headColNames = new String[]{"remark6", "icbc_ac_no", "icbc_ac_name", "txn_ccy", "txn_amt", "txn_ind", "remarks"};
        /***生成Excel***/
        /***1、生成标题***/
        HSSFWorkbook workbook = new HSSFWorkbook();
        //1、先创建标题
        String fileName = "MAC_REJECT" + transDate;
        if (TYPE_PAY == type) {
            fileName = "MAC_ACCEPT" + transDate;
        }
        HSSFSheet sheet = workbook.createSheet(fileName);
        sheet.setDefaultColumnWidth(20);
        int rownum = 0;
        HSSFRow row = sheet.createRow(rownum);
        //创建一个单元格
        HSSFCell cell;
        for (int i = 0; i < headColNames.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headColNames[i]);
        }
        //数字样式
        HSSFCellStyle numberCellStyle = ExcelUtils.getNumberCellStyle(workbook, false, 0, false, 10, false, false, false, false, false);
        //表体数据
        if (CollectionUtils.isNotEmpty(detailData)) {
            for (FovaUpdateDataPO detail : detailData) {
                rownum++;
                row = sheet.createRow(rownum);
                int j = 0;
                cell = row.createCell(j);
                cell.setCellValue(detail.getNotes());
                j++;
                cell = row.createCell(j);
                cell.setCellValue(detail.getCustomerAccount());
                j++;
                cell = row.createCell(j);
                cell.setCellValue(detail.getCustomerName());
                j++;
                cell = row.createCell(j);
                cell.setCellValue(detail.getCurrType());
                j++;
                cell = row.createCell(j);
                cell.setCellStyle(numberCellStyle);
                cell.setCellValue(getAmountByUnit(detail.getAmount()));
                j++;
                cell = row.createCell(j);
                if (TYPE_PAY == type) {
                    cell.setCellValue("G");
                } else {
                    cell.setCellValue("R");
                }
                j++;
                cell = row.createCell(j);
                cell.setCellValue(detail.getNotes());
            }
        }
        return ExportDownLoad.genereateExcel(workbook, getPath() + fileName);
    }
}
