package com.yyicbc.controller;

import com.yyicbc.beans.business.VO.CompanyBaseVO;
import com.yyicbc.beans.querycondition.CompanyQuestVO;
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
@RequestMapping("/collection/companyFile")
@Slf4j
public class CompanyFileController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/getList")
    public String getList(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyFile/getList", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取公司档案列表页数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/getTemplate")
    public String getTemplate(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyFile/getTemplate", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取模板数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/add")
    public String add(CompanyBaseVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyBaseVO> req = new HttpEntity<CompanyBaseVO>(request, headers);
        try {
            String addResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyFile/add", req, String.class);
            return addResponse;
        } catch (Exception e) {
            log.error("新增公司档案数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/update")
    public String update(CompanyBaseVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyBaseVO> req = new HttpEntity<CompanyBaseVO>(request, headers);
        try {
            String updateResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyFile/update", req, String.class);
            return updateResponse;
        } catch (Exception e) {
            log.error("更新公司档案数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/delete")
    public String delete(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyFile/delete", req, String.class);
            return deleteResponse;
        } catch (Exception e) {
            log.error("删除公司档案数据异常 error={}" + e);
            return "";
        }
    }
}