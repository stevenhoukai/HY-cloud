package com.yyicbc.controller;

import com.yyicbc.beans.imports.VO.TxtTemplateVO;
import com.yyicbc.beans.querycondition.TxtTemplateQuestVO;
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
@RequestMapping("/imports/txtTemplate")
@Slf4j
public class TxtTemplateController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/getTxtFormatFields")
    public String getFormatFieldList(TxtTemplateQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TxtTemplateQuestVO> req = new HttpEntity<TxtTemplateQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/txtTemplate/getTxtFormatFields", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取字段模板数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/getList")
    public String getList(TxtTemplateQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TxtTemplateQuestVO> req = new HttpEntity<TxtTemplateQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/txtTemplate/getList", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取Txt模板列表页数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/add")
    public String add(TxtTemplateVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TxtTemplateVO> req = new HttpEntity<TxtTemplateVO>(request, headers);
        try {
            String addResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/txtTemplate/add", req, String.class);
            return addResponse;
        } catch (Exception e) {
            log.error("新增Txt模板数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/update")
    public String update(TxtTemplateVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TxtTemplateVO> req = new HttpEntity<TxtTemplateVO>(request, headers);
        try {
            String updateResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/txtTemplate/update", req, String.class);
            return updateResponse;
        } catch (Exception e) {
            log.error("更新Txt模板数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/delete")
    public String delete(TxtTemplateQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TxtTemplateQuestVO> req = new HttpEntity<TxtTemplateQuestVO>(request, headers);
        try {
            String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/txtTemplate/delete", req, String.class);
            return deleteResponse;
        } catch (Exception e) {
            log.error("删除Txt模板数据异常 error={}" + e);
            return "";
        }
    }
}