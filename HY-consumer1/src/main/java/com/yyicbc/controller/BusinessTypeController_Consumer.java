package com.yyicbc.controller;

import com.yyicbc.beans.business.VO.BusinessTypeVO;
import com.yyicbc.beans.querycondition.BusinessTypeQuestVO;
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
@RequestMapping("/collection/businessFile")
@Slf4j
public class BusinessTypeController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/getList")
    public String getList(BusinessTypeQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<BusinessTypeQuestVO> req = new HttpEntity<BusinessTypeQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/businessFile/getList", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取业务档案列表页数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/add")
    public String add(BusinessTypeVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<BusinessTypeVO> req = new HttpEntity<BusinessTypeVO>(request, headers);
        try {
            String addResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/businessFile/add", req, String.class);
            return addResponse;
        } catch (Exception e) {
            log.error("新增业务档案数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/update")
    public String update(BusinessTypeVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<BusinessTypeVO> req = new HttpEntity<BusinessTypeVO>(request, headers);
        try {
            String updateResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/businessFile/update", req, String.class);
            return updateResponse;
        } catch (Exception e) {
            log.error("更新业务档案数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/delete")
    public String delete(BusinessTypeQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<BusinessTypeQuestVO> req = new HttpEntity<BusinessTypeQuestVO>(request, headers);
        try {
            String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/businessFile/delete", req, String.class);
            return deleteResponse;
        } catch (Exception e) {
            log.error("删除业务档案数据异常 error={}" + e);
            return "";
        }
    }
}