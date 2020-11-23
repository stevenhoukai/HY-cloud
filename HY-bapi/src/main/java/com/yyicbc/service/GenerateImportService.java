package com.yyicbc.service;

import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.querycondition.GenerateImportQuestVO;

import java.util.List;


public interface GenerateImportService {


    /**
     * 功能描述:根据当前日期删除历史数据，读取接口数据，保存到数据库，并且返回LIST，
     *
     * @return: {@link java.util.List}
     * @Author: vic
     * @Date: 2020/4/3 17:05
     */
    List addImportData(GenerateImportQuestVO request);


    /**功能描述:
     * @param request
    * @return: {@link java.util.List}
    * @Author: vic
    * @Date: 2020/5/6 11:12
    */
    List getDateList(GenerateImportQuestVO request);
}
