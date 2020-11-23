package com.yyicbc.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestService {

    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;



//    @HystrixCommand(fallbackMethod = "fallback")
    public String test(){
        Map<String,String> map = new HashMap<String,String>();
        String s = restTemplate.getForObject( "http://127.0.0.1:8094/system/user/test", String.class, map);
        return s;
    }

    //服务访问失败直接返回失败信息，不阻塞
    public String fallback()
    {
        RetData retData = new RetData();
        retData.setCode(StatusCode.ERROR).setMsg("hystrix生效");
        return JsonUtils.objectToJson(retData);
    }

}
