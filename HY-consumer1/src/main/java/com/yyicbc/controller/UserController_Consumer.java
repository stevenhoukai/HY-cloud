package com.yyicbc.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.SysUserVO;
import com.yyicbc.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/system/user")
public class UserController_Consumer {

    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/list")
    public String getAllUser(@RequestParam Integer page,
                             @RequestParam String userCode,
                             @RequestParam String userName,
                             @RequestParam String mobile) {
        UserQueryVO vo = new UserQueryVO();
        vo.setPage(page).setMobile(mobile).setUserCode(userCode).setUserName(userName);
        String jsonStr = JsonUtils.objectToJson(vo);
        Map<String,String> map = new HashMap<String,String>();
        map.put("where",jsonStr);
        return restTemplate.getForObject(REST_URL_PREFIX + "/system/user/list?where={where}", String.class, map);
    }

    /**
     * 新增用户
     * @param sysUserVO
     * @return
     */
    @RequestMapping("/add")
    public String saveUser(SysUserVO sysUserVO){
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        String jsonStr = JsonUtils.objectToJson(sysUserVO);
        map.put("sysUserVO", jsonStr);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/user/add", req, String.class);
        String body = response.getBody();
        return body;

    }
    /**
     * 更新用户
     * @param sysUserVO
     * @return
     */
    @RequestMapping("/update")
    public String updateUser(SysUserVO sysUserVO){
        HttpHeaders headers = new HttpHeaders();
        Map<String,String> map = new HashMap<String,String>();
        String jsonStr = JsonUtils.objectToJson(sysUserVO);
        map.put("sysUserVO", jsonStr);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/user/update", req, String.class);
        String body = response.getBody();
        return body;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping("/delete")
    public String deleteUser(@RequestParam Long userId){
        HttpHeaders headers = new HttpHeaders();
        Map<String,Long> map = new HashMap<String,Long>();
        map.put("userId", userId);
        HttpEntity<Map<String, Long>> req = new HttpEntity<Map<String, Long>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/user/delete", req, String.class);
        String body = response.getBody();
        return body;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping("/resetpwd")
    public String resetPwd(@RequestParam Long userId){
        HttpHeaders headers = new HttpHeaders();
        Map<String,Long> map = new HashMap<String,Long>();
        map.put("userId", userId);
        HttpEntity<Map<String, Long>> req = new HttpEntity<Map<String, Long>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/user/resetpwd", req, String.class);
        String body = response.getBody();
        return body;
    }

    /**
     * 获取公司选择框数据
     * @return
     */
    @RequestMapping("/corplist")
    public String getAllCorpList() {
        Map<String,String> map = new HashMap<String,String>();
        return restTemplate.getForObject(REST_URL_PREFIX + "/system/user/corplist", String.class, map);
    }

    @RequestMapping("/test")
    public String getAllCorpList(@RequestParam String json) {
        Map<String,String> map = new HashMap<String,String>();
        return restTemplate.getForObject(REST_URL_PREFIX + "/system/user/test", String.class, map);
    }

    //服务访问失败直接返回失败信息，不阻塞
    public String fallback(String json)
    {
        RetData retData = new RetData();
        retData.setCode(StatusCode.ERROR).setMsg("hystrix生效");
        return JsonUtils.objectToJson(retData);
    }
}
