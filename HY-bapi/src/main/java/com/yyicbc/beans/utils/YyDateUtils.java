package com.yyicbc.beans.utils;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class YyDateUtils
{

    /**
     * Author : Xiep
     * 构造闭区间时间查询，传入时间戳
     *
     * @param beginTime
     * @param endTime
     * @param isNeedTime
     * @return
     */
    public static Map<String, String> createDateInterval(String beginTime, String endTime, Boolean isNeedTime)
    {
        if(StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)){
            return null;
        }
        Map<String, String> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date beginDate = new Date(Long.parseLong(beginTime));
        String strBeginTime = format.format(beginDate).substring(0, 10) + (isNeedTime ? " 00:00:00" : "");
        Date endDate = new Date(Long.parseLong(endTime));
        String strEndTime = format.format(endDate).substring(0, 10) + (isNeedTime ? " 23:59:59" : "");

        map.put("beginDate", strBeginTime);
        map.put("endDate", strEndTime);
        return map;
    }
}
