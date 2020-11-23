package com.yyicbc.utils;

import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

@Slf4j
public class EXCELtemplateForDownload {
    public static byte[] getExcel(String templateCode, List<FovaUpdateDataPO> data) {
        switch (templateCode) {
            case "macaoInsAccept":
                return macaoInsAccept(data);
            default:
                return commonEXCEL(data);
        }
    }

    private static byte[] commonEXCEL(List<FovaUpdateDataPO> data) {
        return null;
    }

    private static byte[] macaoInsAccept(List<FovaUpdateDataPO> data) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        sheet.setDefaultColumnWidth(20);// 默认列宽
        HSSFRow row = sheet.createRow((int) 0);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCell cell = null;
        //写入表头
        cell = row.createCell(0);
        cell.setCellValue("remark6");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("icbc_ac_no");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("icbc_ac_name");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("txn_ccy");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("txn_amt");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("txn_ind");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("remarks");
        cell.setCellStyle(style);
        //写入数据
        for (int j = 0; j < data.size(); j++) {
            row = sheet.createRow(j + 1);
            cell = row.createCell(0);
            cell.setCellValue(data.get(j).getNotes());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(data.get(j).getAgenpsn());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(data.get(j).getShotName());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(data.get(j).getCurrType());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(data.get(j).getAmount());
            cell.setCellStyle(style);
            cell = row.createCell(5);
            String result = StringUtils.isBlank(data.get(j).getErrNo()) ?
                    "G" : "";
            cell.setCellValue(result);
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue(data.get(j).getNotes());
            cell.setCellStyle(style);
        }
        return genereateExcel(wb);
    }

    private static byte[] genereateExcel(HSSFWorkbook workbook) {
        try {
            File file = new File("\\output.xls");
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.flush();
            out.close();
            byte[] bFile = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(bFile);
            fis.close();
            file.delete();
            return bFile;
        } catch (Exception e) {
            log.error("生成excel文件异常");
        }
        return null;
    }
}