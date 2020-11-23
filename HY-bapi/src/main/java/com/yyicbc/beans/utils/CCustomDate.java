package com.yyicbc.beans.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期时间公共处理类
 *
 * @author vic fu
 * @version V1.0
 * @Description: 日期时间公共处理类
 */
public class CCustomDate {

    public static final String FORMAT = "yyyy-MM-dd";
    public static final String YEAR = "year";
    public static final String MONTH = "month";

    /**
     * 获得当前系统日期:格式(yyyy-MM-dd)
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyy-MM-dd)
     */
    public static String getCurDate() {
        return getCurDateTime("yyyy-MM-dd");
    }

    /**
     * 获得当前系统日期:格式(yyyyMMdd)
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyyMMdd)
     */
    public static String getCurDate_yyyyMMdd() {
        return getCurDateTime("yyyyMMdd");
    }

    /**
     * 获得当前系统日期:格式(yyyyMM);
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyyMM);
     */
    public static String getCurDate_yyyyMM() {
        return getCurDateTime("yyyyMM");
    }

    /**
     * 获得当前系统日期:指定格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param format
     * @return
     * @Description: 获得当前系统日期:指定格式(yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurDateTime(String format) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 获得当前系统日期:格式(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurDateTime() {
        return getCurDateTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 输出PDF时间，区分pm和am
     *
     * @return
     */
    public static String getPrintDateTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd KK:mm aa", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    /**
     * 获得当前系统日期:格式(yyyyMMddHHmmss)
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyyMMddHHmmss)
     */
    public static String getCurDateTime_yyyyMMddHHmmss() {
        return getCurDateTime("yyyyMMddHHmmss");
    }

    /**
     * 获得当前系统日期:格式(yyyyMMddHHmmssSSS)
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyyMMddHHmmssSSS)
     */
    public static String getCurDateTime_yyyyMMddHHmmssSSS() {
        return getCurDateTime("yyyyMMddHHmmssSSS");
    }

    /**
     * 获得当前系统时间:格式(HH:mm:ss)
     *
     * @return
     * @Description: 获得当前系统时间:格式(HH:mm:ss)
     */
    public static String getCurTime() {
        return getCurDateTime("HH:mm:ss");
    }

    /**
     * 日期加减年、月、天数、小时、分钟
     *
     * @param dateYMDHMS 日期字符串（格式为format指定的格式）
     * @param format     指定dateYMDHMS的日期格式
     * @param unit       日期加减的单位（可以是年、月、天数、小时、分钟）
     * @param amount     要加减的数量
     * @return 返回加减后的日期（格式为format指定的格式）
     * @Description: 日期加减年、月、天数、小时、分钟
     */
    public static String get0perationDate(String dateYMDHMS, String format, String unit, int amount) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();
            if (dateYMDHMS != null && !"".equals(dateYMDHMS)) {
                Date date = dateFormat.parse(dateYMDHMS);
                calendar.setTime(date);
            } else {
                Date date = new Date(System.currentTimeMillis());
                calendar.setTime(date);
            }
            if (unit.equals("year") || unit.equals("years")) {
                calendar.add(Calendar.YEAR, amount);
            } else if (unit.equals("month") || unit.equals("months")) {
                calendar.add(Calendar.MONTH, amount);
            } else if (unit.equals("day") || unit.equals("days")) {
                calendar.add(Calendar.DAY_OF_YEAR, amount);
            } else if (unit.equals("hour") || unit.equals("hours")) {
                calendar.add(Calendar.HOUR_OF_DAY, amount);
            } else if (unit.equals("minute") || unit.equals("minutes")) {
                calendar.add(Calendar.MINUTE, amount);
            }
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 当前日期加num天
     *
     * @param num
     * @return
     */
    public static String getCurDateByDay(int num) {
        return get0perationDate(getCurDate(), FORMAT, "day", -1);
    }

    /**
     * 获取传入日期所在月的第一天或者最后一天
     *
     * @param dateYMDHMS 日期字符串（格式为format指定的格式）
     * @param format     指定dateYMDHMS的日期格式
     * @param isFirst    true:第一天 false:最后一天
     * @return
     */
    public static String getFirstDayDateOfMonth(String dateYMDHMS, String format, boolean isFirst) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();
            if (dateYMDHMS != null && !"".equals(dateYMDHMS)) {
                Date date = dateFormat.parse(dateYMDHMS);
                calendar.setTime(date);
            } else {
                Date date = new Date(System.currentTimeMillis());
                calendar.setTime(date);
            }
            if (isFirst) {
                final int last = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

                calendar.set(Calendar.DAY_OF_MONTH, last);
            } else {
                final int last = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                calendar.set(Calendar.DAY_OF_MONTH, last);
            }
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取英文日期的 年月 30 October 2012 转成 Oct 2012
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getMonthYearEn(String dateYMDHMS) {

        if (StringUtils.isEmpty(dateYMDHMS)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

        String formatDate = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);

        try {
            Date date = dateFormat.parse(dateYMDHMS);
            formatDate = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] strs = formatDate.split(" ");

        return strs[1].substring(0, 3) + " " + strs[2];
    }

    /**
     * 获取英文日期的 年月 30 October 2012 转成 Oct-12
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getMonthYearEn2(String dateYMDHMS) {

        if (StringUtils.isEmpty(dateYMDHMS)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

        String formatDate = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);

        try {
            Date date = dateFormat.parse(dateYMDHMS);
            formatDate = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] strs = formatDate.split(" ");

        return strs[1].substring(0, 3) + "-" + strs[2].substring(2, 4);
    }

    /**
     * 获取月年  yyyyMM   201912
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getYearMonth(String dateYMDHMS) {
        if (StringUtils.isEmpty(dateYMDHMS)) {
            return "";
        }
        return getFormatDate(dateYMDHMS).replace("-", "").substring(0, 6);
    }


    /**
     * 获取年
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getYear(String dateYMDHMS) {

        if (StringUtils.isEmpty(dateYMDHMS)) {
            return "";
        }
        return getFormatDate(dateYMDHMS).substring(0, 4);
    }

    /**
     * 获取月
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getMonth(String dateYMDHMS) {
        if (StringUtils.isEmpty(dateYMDHMS)) {
            return "";
        }
        return getFormatDate(dateYMDHMS).substring(5, 7);

    }

    /**
     * 得到指定格式日期
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getFormatDate(String dateYMDHMS) {

        if (StringUtils.isEmpty(dateYMDHMS)) return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);

            Date date = dateFormat.parse(dateYMDHMS);

            return dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 得到指定格式日期
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getFormatDate(String dateYMDHMS, String format) {
        if (StringUtils.isEmpty(dateYMDHMS)) return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);

            Date date = dateFormat.parse(dateYMDHMS);

            return dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * @param yyyyMMdd
     * @return yyyy/MM/dd
     */
    public static String getFormatDateDef(String yyyyMMdd) {
        if (StringUtils.isEmpty(yyyyMMdd)) {
            return "";
        }
        if (yyyyMMdd.length() != 8) {
            return yyyyMMdd;
        }
        return yyyyMMdd.substring(0, 4).concat("/").concat(yyyyMMdd.substring(4, 6)).concat("/").concat(yyyyMMdd.substring(6, 8));
    }

    public static String replaceSplit(String dateYMDHMS) {
        if (StringUtils.isEmpty(dateYMDHMS)) {
            return "";
        }
        return dateYMDHMS.replace("-", "");
    }

    public static String replaceSplitDef(String dateYMDHMS) {
        if (StringUtils.isEmpty(dateYMDHMS)) {
            return "";
        }
        return dateYMDHMS.replace("-", "/");
    }


    /**
     * 功能描述: 获取日期范围
     * 1、根据查询日期，将最近一周的“新增服务和取消服务”按社保需要的格式导出txt格式和PDF格式。
     * 2、如果查询当周 星期五、周六周天，则取上周五到当周周四的数据；如果“查询日期”属于当周星期一到星期四的，则取上上周五到上周四的数据
     *
     * @param date
     * @return: {@link String[]}
     * @Author: vic
     * @Date: 2020/3/24 17:47
     */
    public static String[] getDateScope(String date) {
        String[] dates = new String[2];
        String curDate = getCurDate();
        if (StringUtils.isNotBlank(date)) {
            curDate = date;
        } else {
            return null;
        }
        //判断当前日期是否星期五
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date endDate = sdf.parse(curDate);
            Date beginDate = sdf.parse(curDate);
            // 获得一个日历
            Calendar beginCal = Calendar.getInstance();
            beginCal.setTime(beginDate);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);
            // 指示一个星期中的某天。
            int w = beginCal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0) {
                w = 0;
            }
            if (5 == w) {//星期五
                //上周五
                beginCal.add(Calendar.DATE, -7);
                //星期四
                endCal.add(Calendar.DATE, -1);
            } else if (6 == w) {//星期六
                //上周五
                beginCal.add(Calendar.DATE, -8);
                //星期四
                endCal.add(Calendar.DATE, -2);
            } else if (0 == w) {//星期天
                //上周五
                beginCal.add(Calendar.DATE, -9);
                //星期四
                endCal.add(Calendar.DATE, -3);
            } else {//星期一至星期四
                //相差天数
                int sub = 4 - w;
                //上周四
                endCal.add(Calendar.DATE, sub - 7);
                //上上周五
                beginCal.add(Calendar.DATE, sub - 14 + 1);
            }

            beginDate = beginCal.getTime();
            endDate = endCal.getTime();

            dates[0] = sdf.format(beginDate);
            dates[1] = sdf.format(endDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**功能描述: 替换-
     * @param date
    * @return: {@link String}
    * @Author: vic
    * @Date: 2020/3/25 17:11
    */
    public static String replace(String date){
        if(StringUtils.isNotBlank(date)){
            return date.replace("-","");
        }
        return date;
    }

    /**
     *      * 获取两个日期之间的所有日期
     *      * 
     *      * @param startDate  开始日期
     *      * @param endDate   结束日期
     *      * @return  List集合
     *      
     */
    public static List<String> getDates(String startDate, String endDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<>();
        Date date_start = null;
        Date date_end = null;
        try {
            date_start = sdf.parse(startDate);
            date_end = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = date_start;
        //用Calendar 进行日期比较判断
        Calendar cd = Calendar.getInstance();
        while (date.getTime() <= date_end.getTime()) {
            list.add(sdf.format(date));
            cd.setTime(date);
            //增加一天 放入集合
            cd.add(Calendar.DATE, 1);
            date = cd.getTime();
        }
        return list;

    }

    /**功能描述:
     * @param date
    * @return: {@link java.lang.String}
    * @Author: vic
    * @Date: 2020/3/25 18:02
    */
    public static String getDateStr(Date date){
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         String dateString = formatter.format(date);
         return dateString;
    }
}