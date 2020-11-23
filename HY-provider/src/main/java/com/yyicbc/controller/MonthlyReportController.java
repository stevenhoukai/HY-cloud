package com.yyicbc.controller;


import com.yyicbc.beans.RetData;
import com.yyicbc.beans.querycondition.DailyReportQueryVO;
import com.yyicbc.beans.querycondition.MonthlyReportQueryVO;
import com.yyicbc.service.impl.DailyReportServiceImpl;
import com.yyicbc.service.impl.MonthlyReportServiceImpl;
import com.yyicbc.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 以添加user示例通过consumer调用eureka注册中心
 *
 * @author ste
 * 用户管理节点controller
 */
@RestController
@RequestMapping(value = "/report/monthlysummry", produces = {"application/json;charset=UTF-8"})
public class MonthlyReportController {


    @Autowired
    MonthlyReportServiceImpl monthlyReportService;

    /**
     * 查询所有用户系统测试使用
     *
     * @return
     */
    @GetMapping(value = "/list", name = "/report/monthlysummry/list")
    public RetData all(@RequestParam String where) {
        MonthlyReportQueryVO queryVO = JsonUtils.jsonToPojo(where, MonthlyReportQueryVO.class);
        final RetData result = monthlyReportService.all(queryVO);
        return result;
    }


    @PostMapping(value = "/exportexcel",name = "/report/monthlysummry/exportexcel")
    public RetData exportExcel(@RequestBody Map<String, Object> map){
        String json = (String) map.get("monthlyReportQueryVO");
        MonthlyReportQueryVO vo = JsonUtils.jsonToPojo(json, MonthlyReportQueryVO.class);
        RetData retData = monthlyReportService.exportExcel(vo);
        return retData;
    }

}