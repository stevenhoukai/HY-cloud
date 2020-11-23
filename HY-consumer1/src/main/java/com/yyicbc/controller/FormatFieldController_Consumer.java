package com.yyicbc.controller;

import com.yyicbc.beans.imports.VO.FormatFieldVO;
import com.yyicbc.beans.querycondition.FormatFieldQuestVO;
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
@RequestMapping("/imports/formatField")
@Slf4j
public class FormatFieldController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/getList")
    public String getList(FormatFieldQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<FormatFieldQuestVO> req = new HttpEntity<FormatFieldQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/formatField/getList", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取字段档案列表页数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/add")
    public String add(FormatFieldVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<FormatFieldVO> req = new HttpEntity<FormatFieldVO>(request, headers);
        try {
            String addResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/formatField/add", req, String.class);
            return addResponse;
        } catch (Exception e) {
            log.error("新增字段档案数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/update")
    public String update(FormatFieldVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<FormatFieldVO> req = new HttpEntity<FormatFieldVO>(request, headers);
        try {
            String updateResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/formatField/update", req, String.class);
            return updateResponse;
        } catch (Exception e) {
            log.error("更新字段档案数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/delete")
    public String delete(FormatFieldQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<FormatFieldQuestVO> req = new HttpEntity<FormatFieldQuestVO>(request, headers);
        try {
            String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/formatField/delete", req, String.class);
            return deleteResponse;
        } catch (Exception e) {
            log.error("删除字段档案数据异常 error={}" + e);
            return "";
        }
    }
}