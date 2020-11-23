package com.yyicbc.controller;



import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.security.ChangePwdVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;


@Api("用户权限相关接口类")
@RestController
@RequestMapping("/system/user")
public class UserAuthController_Consumer {


    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @ApiOperation(value = "用戶登录",notes = "根据用户编码和密码进行登录",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "usercode",value = "用戶編碼",required = true),
            @ApiImplicitParam(dataType = "string",name = "password",value = "用戶密碼",required = true)})
    @PostMapping("/login" )
    public String userLogin(@RequestParam String usercode,
                            @RequestParam String password){
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        map.put("usercode", usercode);
        map.put("password", password);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/user/login", req, String.class);
        return response.getBody();
    }

    @ApiOperation(value = "用戶登录",notes = "根据用户编码和密码进行登录",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "usercode",value = "用戶編碼",required = true),
            @ApiImplicitParam(dataType = "string",name = "password",value = "用戶密碼",required = true)})
    @PostMapping("/loginsso" )
    public String userLoginsso(@RequestParam String usercode,
                            @RequestParam String password){
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        map.put("usercode", usercode);
        map.put("password", password);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/user/loginsso", req, String.class);
        return response.getBody();
    }

    @ApiOperation(value = "用戶注销",notes = "更具用户编码进行注销",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "usercode",value = "用戶編碼",required = true)})
    @PostMapping("/logout")
    public String userLogout(@RequestParam String usercode){
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        map.put("usercode", usercode);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/user/logout", req, String.class);
        return response.getBody();
    }

    /**
     * 新增用户
     * @param changePwdVO
     * @return
     */
    @ApiOperation(value = "用戶修改密码",notes = "根据用户修改密码实体信息进行密码更新",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long",name = "userid",value = "用戶主键",required = true),
            @ApiImplicitParam(dataType = "string",name = "oldUserPassword",value = "用戶旧密碼",required = true),
            @ApiImplicitParam(dataType = "string",name = "newUserPassword",value = "用戶新密碼",required = true),
            @ApiImplicitParam(dataType = "string",name = "ensureNewUserPassword",value = "确认新密碼",required = true)})
    @PostMapping("/changepwd")
    public String saveUser(ChangePwdVO changePwdVO){
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        String jsonStr = JsonUtils.objectToJson(changePwdVO);
        map.put("changePwdVO", jsonStr);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/user/changepwd", req, String.class);
        String body = response.getBody();
        return body;

    }



    }
