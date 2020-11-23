package com.yyicbc.controller;


import com.yyicbc.beans.RetData;
import com.yyicbc.beans.querycondition.DailyReportQueryVO;
import com.yyicbc.beans.querycondition.LogQueryVO;
import com.yyicbc.service.impl.DailyReportServiceImpl;
import com.yyicbc.service.impl.SysLogServiceImpl;
import com.yyicbc.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;


/**
 * 以添加user示例通过consumer调用eureka注册中心
 *
 * @author ste
 * 用户管理节点controller
 */
@RestController
@RequestMapping(value = "/report/dailysummry", produces = {"application/json;charset=UTF-8"})
public class DailyReportController {


    @Autowired
    DailyReportServiceImpl dailyReportService;

    /**
     * 查询所有用户系统测试使用
     *
     * @return
     */
    @GetMapping(value = "/list", name = "/report/dailysummry/list")
    public RetData all(@RequestParam String where) {
        DailyReportQueryVO queryVO = JsonUtils.jsonToPojo(where, DailyReportQueryVO.class);
        final RetData result = dailyReportService.all(queryVO);
        return result;
    }


    @PostMapping(value = "/exportexcel",name = "/report/dailysummry/exportexcel")
    public RetData exportExcel(@RequestBody Map<String, Object> map){
        String json = (String) map.get("dailyReportExportVO");
        ArrayList list = JsonUtils.jsonToPojo(json, ArrayList.class);
        RetData retData = dailyReportService.exportExcel(list);
        return retData;
    }

}