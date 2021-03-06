package com.yyicbc.controller;

import com.yyicbc.beans.business.VO.CurrencyBaseVO;
import com.yyicbc.beans.querycondition.CurrencyQueryVO;
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
@RequestMapping("/collection/currencyFile")
@Slf4j
public class CurrencyFileController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/getList")
    public String getList(CurrencyQueryVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CurrencyQueryVO> req = new HttpEntity<CurrencyQueryVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/currencyFile/getList", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取币种档案列表页数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/add")
    public String add(CurrencyBaseVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CurrencyBaseVO> req = new HttpEntity<CurrencyBaseVO>(request, headers);
        try {
            String addResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/currencyFile/add", req, String.class);
            return addResponse;
        } catch (Exception e) {
            log.error("新增币种档案数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/update")
    public String update(CurrencyBaseVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CurrencyBaseVO> req = new HttpEntity<CurrencyBaseVO>(request, headers);
        try {
            String updateResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/currencyFile/update", req, String.class);
            return updateResponse;
        } catch (Exception e) {
            log.error("更新币种档案数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/delete")
    public String delete(CurrencyQueryVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CurrencyQueryVO> req = new HttpEntity<CurrencyQueryVO>(request, headers);
        try {
            String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/currencyFile/delete", req, String.class);
            return deleteResponse;
        } catch (Exception e) {
            log.error("删除币种档案数据异常 error={}" + e);
            return "";
        }
    }
}