package com.yyicbc.beans.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * @ClassNameExcelUtils
 * @Description Excel导出工具类
 * @Author vic
 * @Date2020/2/28 10:09
 * @Version V1.0
 **/
public class ExcelUtils {

    /**
     * 数字样式
     *
     * @param workbook
     * @param color
     * @param bg
     * @param bold
     * @param fontsize
     * @param borderLeft
     * @param borderRight
     * @param borderTop
     * @param borderBottom
     * @param isint
     * @return
     */
    public static HSSFCellStyle getNumberCellStyle(HSSFWorkbook workbook, boolean color, int bg, boolean bold, int fontsize, boolean borderLeft, boolean borderRight,
                                                   boolean borderTop, boolean borderBottom, boolean isint) {
        HSSFCellStyle hssfCellStyle = getStrCellStyle(workbook, color, bg, bold, fontsize, borderLeft, borderRight, borderTop, borderBottom);
        HSSFDataFormat format = workbook.createDataFormat();
        if (isint) {
            hssfCellStyle.setDataFormat(format.getFormat("#,##0"));
        } else {
            hssfCellStyle.setDataFormat(format.getFormat("#,##0.00"));
        }
        return hssfCellStyle;
    }

    /**
     * 带%分号
     * @param workbook
     * @param color
     * @param bg
     * @param bold
     * @param fontsize
     * @param borderLeft
     * @param borderRight
     * @param borderTop
     * @param borderBottom
     * @param isint
     * @return
     */
    public static HSSFCellStyle getNumberBuiltinCellStyle(HSSFWorkbook workbook, boolean color, int bg, boolean bold, int fontsize, boolean borderLeft, boolean borderRight,
                                                          boolean borderTop, boolean borderBottom, boolean isint) {
        HSSFCellStyle hssfCellStyle = getStrCellStyle(workbook, color, bg, bold, fontsize, borderLeft, borderRight, borderTop, borderBottom);
        HSSFDataFormat format = workbook.createDataFormat();
        hssfCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
        return hssfCellStyle;
    }

    /**
     * 单元格为0 时，显示成'-'
     *
     * @param workbook
     * @param color
     * @param bg
     * @param bold
     * @param fontsize
     * @param borderLeft
     * @param borderRight
     * @param borderTop
     * @param borderBottom
     * @param isint
     * @return
     */
    public static HSSFCellStyle getNumberCellStyle_Zero(HSSFWorkbook workbook, boolean color, int bg, boolean bold, int fontsize, boolean borderLeft, boolean borderRight,
                                                        boolean borderTop, boolean borderBottom, boolean isint) {
        HSSFCellStyle hssfCellStyle = getStrCellStyle(workbook, color, bg, bold, fontsize, borderLeft, borderRight, borderTop, borderBottom);
        HSSFDataFormat format = workbook.createDataFormat();
        if (isint) {
            hssfCellStyle.setDataFormat(format.getFormat("_(* #,##0_);_(* (#,##0);_(* \"-\"??_);_(@_)"));
        } else {
            hssfCellStyle.setDataFormat(format.getFormat("_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)"));
        }


        return hssfCellStyle;

    }

    /**
     * 字符串文字显示位置
     *
     * @param workbook
     * @param color
     * @param bg
     * @param bold
     * @param fontsize
     * @param borderLeft
     * @param borderRight
     * @param borderTop
     * @param borderBottom
     * @param alignV
     * @param alignH
     * @return
     */
    public static HSSFCellStyle getStrCellStyleAlign(HSSFWorkbook workbook, boolean color, int bg, boolean bold, int fontsize, boolean borderLeft, boolean borderRight,
                                                     boolean borderTop, boolean borderBottom,
                                                     VerticalAlignment alignV, HorizontalAlignment alignH) {

        HSSFCellStyle hssfCellStyle = getStrCellStyle(workbook, color, bg, bold, fontsize, borderLeft, borderRight, borderTop, borderBottom);

        hssfCellStyle.setVerticalAlignment(alignV);

        hssfCellStyle.setAlignment(alignH);

        return hssfCellStyle;
    }

    /**
     * 字符样式
     *
     * @param workbook
     * @param color
     * @param bg
     * @param bold
     * @param fontsize
     * @param borderLeft
     * @param borderRight
     * @param borderTop
     * @param borderBottom
     * @return
     */
    public static HSSFCellStyle getStrCellStyle(HSSFWorkbook workbook, boolean color, int bg, boolean bold, int fontsize, boolean borderLeft, boolean borderRight,
                                                boolean borderTop, boolean borderBottom) {

        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();

        hssfCellStyle.setFont(getHSSFFont(workbook, bold, fontsize));

        if (color) {
            hssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//设置前景填充样式
            hssfCellStyle.setFillForegroundColor((short) bg);//前景填充色
        }

        if (borderLeft) {
            hssfCellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
        }
        if (borderRight) {
            hssfCellStyle.setBorderRight(BorderStyle.THIN);// 右边框
        }
        if (borderTop) {
            hssfCellStyle.setBorderTop(BorderStyle.THIN);// 上边框
        }
        if (borderBottom) {
            hssfCellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
        }
        return hssfCellStyle;
    }

    /**
     * 字體樣式
     *
     * @param workbook
     * @param bold
     * @param fontsize
     * @return
     */
    public static HSSFFont getHSSFFont(HSSFWorkbook workbook, boolean bold, int fontsize) {
        //创建字体
        HSSFFont groupFont = workbook.createFont();

        groupFont.setFontName("新細明體");
        //是否加粗字体
        groupFont.setBold(bold);

        groupFont.setFontHeightInPoints((short) fontsize);

        return groupFont;
    }

    /**
     * // 补充下面的操作，可以显示全部框线
     *
     * @param sheet
     * @param cellRangeAddress
     */
    public static void setRegionBorder(Sheet sheet, CellRangeAddress cellRangeAddress) {

        sheet.addMergedRegion(cellRangeAddress);
        RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, sheet);

    }

    /**
     * 根据列获取对应列的字母
     *
     * @param num
     * @return
     */
    public static String getExcelColumnLabel(int num) {
        String temp = "";
        double i = Math.floor(Math.log(25.0 * (num) / 26.0 + 1) / Math.log(26)) + 1;
        if (i > 1) {
            double sub = num - 26 * (Math.pow(26, i - 1) - 1) / 25;
            for (double j = i; j > 0; j--) {
                temp = temp + (char) (sub / Math.pow(26, j - 1) + 65);
                sub = sub % Math.pow(26, j - 1);
            }
        } else {
            temp = temp + (char) (num + 65);
        }
        return temp;
    }

    public static String getRealStringValueOfDouble(Double d) {
        String doubleStr = d.toString();
        boolean b = doubleStr.contains("E");
        int indexOfPoint = doubleStr.indexOf('.');
        if (b) {
            int indexOfE = doubleStr.indexOf('E');
            BigInteger xs = new BigInteger(doubleStr.substring(indexOfPoint + BigInteger.ONE.intValue(), indexOfE));
            int pow = Integer.parseInt(doubleStr.substring(indexOfE + BigInteger.ONE.intValue()));
            int xsLen = xs.toByteArray().length;
            int scale = xsLen - pow > 0 ? xsLen - pow : 0;
            final String format = "%." + scale + "f";
            doubleStr = String.format(format, d);
        } else {
            java.util.regex.Pattern p = Pattern.compile(".0$");
            java.util.regex.Matcher m = p.matcher(doubleStr);
            if (m.find()) {
                doubleStr = doubleStr.replace(".0", "");
            }
        }
        return doubleStr;
    }

}
