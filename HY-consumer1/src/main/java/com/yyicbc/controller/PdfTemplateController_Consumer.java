package com.yyicbc.controller;

import com.yyicbc.beans.imports.VO.PdfTemplateVO;
import com.yyicbc.beans.querycondition.PdfTemplateQuestVO;
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
@RequestMapping("/imports/pdfTemplate")
@Slf4j
public class PdfTemplateController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/getPdfFormatFields")
    public String getFormatFieldList(PdfTemplateQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PdfTemplateQuestVO> req = new HttpEntity<PdfTemplateQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/pdfTemplate/getPdfFormatFields", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取字段模板数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/getList")
    public String getList(PdfTemplateQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PdfTemplateQuestVO> req = new HttpEntity<PdfTemplateQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/pdfTemplate/getList", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取Pdf模板列表页数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/add")
    public String add(PdfTemplateVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PdfTemplateVO> req = new HttpEntity<PdfTemplateVO>(request, headers);
        try {
            String addResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/pdfTemplate/add", req, String.class);
            return addResponse;
        } catch (Exception e) {
            log.error("新增Pdf模板数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/update")
    public String update(PdfTemplateVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PdfTemplateVO> req = new HttpEntity<PdfTemplateVO>(request, headers);
        try {
            String updateResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/pdfTemplate/update", req, String.class);
            return updateResponse;
        } catch (Exception e) {
            log.error("更新Pdf模板数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/delete")
    public String delete(PdfTemplateQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PdfTemplateQuestVO> req = new HttpEntity<PdfTemplateQuestVO>(request, headers);
        try {
            String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/pdfTemplate/delete", req, String.class);
            return deleteResponse;
        } catch (Exception e) {
            log.error("删除Pdf模板数据异常 error={}" + e);
            return "";
        }
    }
}