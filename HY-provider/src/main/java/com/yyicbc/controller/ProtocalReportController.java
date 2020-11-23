package com.yyicbc.controller;


import com.yyicbc.beans.RetData;
import com.yyicbc.beans.querycondition.DailyReportQueryVO;
import com.yyicbc.beans.querycondition.LogQueryVO;
import com.yyicbc.beans.querycondition.ProtocalReportQueryVO;
import com.yyicbc.service.impl.DailyReportServiceImpl;
import com.yyicbc.service.impl.ProtocalReportServiceImpl;
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
@RequestMapping(value = "/report/protocal", produces = {"application/json;charset=UTF-8"})
public class ProtocalReportController {


    @Autowired
    ProtocalReportServiceImpl protocalReportService;

    /**
     * 查询所有用户系统测试使用
     *
     * @return
     */
    @GetMapping(value = "/list", name = "/report/protocal/list")
    public RetData all(@RequestParam String where) {
        ProtocalReportQueryVO queryVO = JsonUtils.jsonToPojo(where, ProtocalReportQueryVO.class);
        final RetData result = protocalReportService.all(queryVO);
        return result;
    }


    @PostMapping(value = "/exportexcel",name = "/report/protocal/exportexcel")
    public RetData exportExcel(@RequestBody Map<String, Object> map){
        String json = (String) map.get("protocalReportQueryVO");
        ProtocalReportQueryVO vo = JsonUtils.jsonToPojo(json, ProtocalReportQueryVO.class);
        RetData retData = protocalReportService.exportExcel(vo);
        return retData;
    }

}