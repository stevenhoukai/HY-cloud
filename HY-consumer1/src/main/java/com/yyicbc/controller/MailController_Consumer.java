package com.yyicbc.controller;


import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/system/login")
public class MailController_Consumer {

    //    private static final String REST_URL_PREFIX = "http://yyicbcservice-pro1";
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

//    @RequestMapping("/list")
//    public String getAllUser(@RequestParam Integer page) {
//
//        return restTemplate.getForObject(REST_URL_PREFIX + "/user/list?page="+page, String.class);
//
//    }
    @RequestMapping("/findpwd")
    public String getAllUser(@RequestParam String usercode,
                             @RequestParam String email,
                             @RequestParam String mobile) {
        HttpHeaders headers = new HttpHeaders();
        Map<String,String> map = new HashMap<String,String>();
        map.put("usercode",usercode);
        map.put("email",email);
        map.put("mobile",mobile);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/login/findpwd", req, String.class);
        String body = response.getBody();
        return body;
    }

}
