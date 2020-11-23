package com.yyicbc.controller;


import com.yyicbc.beans.RetData;
import com.yyicbc.beans.querycondition.ProtocalReportQueryVO;
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
@RequestMapping(value = "/report/subprotocal", produces = {"application/json;charset=UTF-8"})
public class SubProtocalReportController {


    @Autowired
    ProtocalReportServiceImpl protocalReportService;

    /**
     *
     * @return
     */
    @GetMapping(value = "/list", name = "/report/subprotocal/list")
    public RetData all(@RequestParam String where) {
        ProtocalReportQueryVO queryVO = JsonUtils.jsonToPojo(where, ProtocalReportQueryVO.class);
        final RetData result = protocalReportService.allSub(queryVO);
        return result;
    }


    @PostMapping(value = "/exportexcel",name = "/report/subprotocal/exportexcel")
    public RetData exportExcel(@RequestBody Map<String, Object> map){
        String json = (String) map.get("subprotocalReportQueryVO");
        ProtocalReportQueryVO vo = JsonUtils.jsonToPojo(json, ProtocalReportQueryVO.class);
        RetData retData = protocalReportService.exportSubExcel(vo);
        return retData;
    }

}