package com.yyicbc.controller;

import com.yyicbc.beans.imports.VO.ExcelTemplateVO;
import com.yyicbc.beans.querycondition.ExcelTemplateQuestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/imports/excelTemplate")
@Slf4j
public class ExcelTemplateController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/getExcelFormatFields")
    public String getFormatFieldList(ExcelTemplateQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ExcelTemplateQuestVO> req = new HttpEntity<ExcelTemplateQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/excelTemplate/getExcelFormatFields", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取字段模板数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/getList")
    public String getList(ExcelTemplateQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ExcelTemplateQuestVO> req = new HttpEntity<ExcelTemplateQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/excelTemplate/getList", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取Excel模板列表页数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/add")
    public String add(ExcelTemplateVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ExcelTemplateVO> req = new HttpEntity<ExcelTemplateVO>(request, headers);
        try {
            String addResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/excelTemplate/add", req, String.class);
            return addResponse;
        } catch (Exception e) {
            log.error("新增Excel模板数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/update")
    public String update(ExcelTemplateVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ExcelTemplateVO> req = new HttpEntity<ExcelTemplateVO>(request, headers);
        try {
            String updateResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/excelTemplate/update", req, String.class);
            return updateResponse;
        } catch (Exception e) {
            log.error("更新Excel模板数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/delete")
    public String delete(ExcelTemplateQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ExcelTemplateQuestVO> req = new HttpEntity<ExcelTemplateQuestVO>(request, headers);
        try {
            String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/excelTemplate/delete", req, String.class);
            return deleteResponse;
        } catch (Exception e) {
            log.error("删除Excel模板数据异常 error={}" + e);
            return "";
        }
    }
}