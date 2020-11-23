package com.yyicbc.controller;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.VO.CompanyBaseVO;
import com.yyicbc.beans.enums.StatusEnums;
import com.yyicbc.beans.querycondition.CompanyQuestVO;
import com.yyicbc.service.CompanyFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/collection/companyFile", produces = {"application/json;charset=UTF-8"})
@Slf4j
public class CompanyFileController {

    @Resource
    CompanyFileService companyFileService;

    @PostMapping(value = "/getList", name = "/collection/companyFile/getList")
    public RetData all(CompanyQuestVO request) {
        try {
            RetData result = companyFileService.getData(request);
            return result;
        } catch (Exception e) {
            log.error("获取公司档案页数据失败 error={}" + e);
            return RetData.build(StatusEnums.ERROR.getStatusCode(), "获取公司档案页数据失败:"+e.getMessage());
        }
    }

    @PostMapping(value = "/getTemplate", name = "/collection/companyFile/getTemplate")
    public RetData getTemplate() {
        try {
            RetData result = companyFileService.getTemplate();
            return result;
        } catch (Exception e) {
            log.error("获取公司档案页数据失败 error={}" + e);
            return new RetData(StatusEnums.REMOTE_ERROR);
        }
    }

    @PostMapping(value = "/add", name = "/collection/companyFile/add")
    public RetData add(CompanyBaseVO request) {
        try {
            RetData result = companyFileService.addOrUpdateData(request);
            return result;
        } catch (Exception e) {
            log.error("新增公司档案数据失败 error={}" + e.getMessage());
            return RetData.build(StatusEnums.REMOTE_ERROR.getStatusCode(),"新增公司档案数据失败:"+e.getMessage());
        }
    }

    @PostMapping(value = "/update", name = "/collection/companyFile/update")
    public RetData update(CompanyBaseVO request) {
        try {
            RetData result = companyFileService.addOrUpdateData(request);
            return result;
        } catch (Exception e) {
            log.error("修改公司档案数据失败 error={}" + e);
            return new RetData(StatusEnums.REMOTE_ERROR);
        }
    }

    @PostMapping(value = "/delete", name = "/collection/companyFile/delete")
    public RetData delete(CompanyQuestVO request) {
        try {
            RetData result = companyFileService.deleteData(request);
            return result;
        } catch (Exception e) {
            log.error("删除公司档案数据失败 error={}" + e);
            return new RetData(StatusEnums.REMOTE_ERROR);
        }
    }
}