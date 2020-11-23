package com.yyicbc.controller;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.VO.InterfaceConfigurationVO;
import com.yyicbc.beans.enums.StatusEnums;
import com.yyicbc.beans.querycondition.InterfaceConfigurationQuestVO;
import com.yyicbc.service.InterfaceConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/system/interfaceConfiguration", produces = {"application/json;charset=UTF-8"})
@Slf4j
public class InterfaceConfigurationController {

    @Resource
    InterfaceConfigurationService interfaceConfigurationService;

    @PostMapping(value = "/add")
    public RetData add(InterfaceConfigurationVO request)
    {
        RetData result = null;
        try {
            result = interfaceConfigurationService.save(request);
        } catch (Exception e) {
            log.error("增加数据失败 error={}" + e);
            throw e;
        }
        return result;
    }

    @GetMapping(value = "/lists")
    public RetData getList(InterfaceConfigurationQuestVO request) {
        RetData result = null;
        try {
            result = interfaceConfigurationService.getData(request);

        } catch (Exception e) {
            log.error("获取数据失败 error={}" + e);
            throw e;
        }
        return result;
    }

    @PostMapping(value ="/update")
    public RetData update(InterfaceConfigurationVO request) {
        RetData result = null;
        try {
            result = interfaceConfigurationService.update(request);
        } catch (Exception e) {
            log.error("更新数据失败 error={}" + e);
            throw e;
        }
        return result;
    }

    @PostMapping(value = "/delete")
    public RetData delete(InterfaceConfigurationQuestVO request) {
        RetData result = null;
        try {
            result =  interfaceConfigurationService.delete(request);
        } catch (Exception e) {
            log.error("删除数据失败 error={}" + e);
            throw e;
        }
        return result;
    }
}
