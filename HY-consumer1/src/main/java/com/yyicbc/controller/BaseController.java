package com.yyicbc.controller;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.enums.StatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassNameBaseController
 * @Description
 * @Author vic
 * @Date2020/4/2 18:25
 * @Version V1.0
 **/
@Slf4j
@RestController
public class BaseController {

    @Value("${rest.location.prefix}")
    protected String REST_URL_PREFIX;

    @Autowired
    protected RestTemplate restTemplate;


    /**
     * 导出临时文件夹路径
     **/
    @Value("${export.path.pdf}")
    protected String EXPORT_PATH_TEMP;


    protected String res(Object request,String url){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> req = new HttpEntity<Object>(request, headers);
        try {
            String res = restTemplate.postForObject(REST_URL_PREFIX + url, req, String.class);
            return res;
        } catch (Exception e) {
            log.error("获取接口失败 error={}" + e);
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }
}
