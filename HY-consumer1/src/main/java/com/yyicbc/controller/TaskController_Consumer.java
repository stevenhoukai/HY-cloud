package com.yyicbc.controller;


import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.querycondition.TaskQueryVO;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.SysUserVO;
import com.yyicbc.beans.task.CronTaskVO;
import com.yyicbc.beans.task.SimpleTaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/system/task")
public class TaskController_Consumer {

    //    private static final String REST_URL_PREFIX = "http://yyicbcservice-pro1";
    @Value("${rest.location.taskprefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

//    @RequestMapping("/list")
//    public String getAllUser(@RequestParam Integer page) {
//
//        return restTemplate.getForObject(REST_URL_PREFIX + "/user/list?page="+page, String.class);
//
//    }
    @RequestMapping("/list")
    public String getAllTasks(@RequestParam Integer page) {
        TaskQueryVO vo = new TaskQueryVO();
        vo.setPage(page);
        String jsonStr = JsonUtils.objectToJson(vo);
        Map<String,String> map = new HashMap<String,String>();
        map.put("where",jsonStr);
        return restTemplate.getForObject(REST_URL_PREFIX + "/system/task/list?where={where}", String.class, map);
    }

    @RequestMapping("/runninglist")
    public String getAllRunningTasks(@RequestParam Integer page) {
        TaskQueryVO vo = new TaskQueryVO();
        vo.setPage(page);
        String jsonStr = JsonUtils.objectToJson(vo);
        Map<String,String> map = new HashMap<String,String>();
        map.put("where",jsonStr);
        return restTemplate.getForObject(REST_URL_PREFIX + "/system/task/runninglist?where={where}", String.class, map);
    }


    /**
     * 暂停任务
     * @param taskId
     * @return
     */
    @RequestMapping("/pause")
    public String pauseTask(@RequestParam String taskId){
        HttpHeaders headers = new HttpHeaders();
        Map<String,String> map = new HashMap<String,String>();
        map.put("taskId", taskId);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/task/pause", req, String.class);
        String body = response.getBody();
        return body;
    }

    /**
     * 开启任务
     * @param taskId
     * @return
     */
    @RequestMapping("/resume")
    public String resumeTask(@RequestParam String taskId){
        HttpHeaders headers = new HttpHeaders();
        Map<String,String> map = new HashMap<String,String>();
        map.put("taskId", taskId);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/task/resume", req, String.class);
        String body = response.getBody();
        return body;
    }


    /**
     * 开启任务
     * @param taskId
     * @return
     */
    @RequestMapping("/run")
    public String runTask(@RequestParam String taskId){
        HttpHeaders headers = new HttpHeaders();
        Map<String,String> map = new HashMap<String,String>();
        map.put("taskId", taskId);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/task/run", req, String.class);
        String body = response.getBody();
        return body;
    }


    /**
     * 新增简单任务
     * @param task
     * @return
     */
    @RequestMapping("/simple/add")
    public String saveSimpleTask(SimpleTaskVO task){
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        String jsonStr = JsonUtils.objectToJson(task);
        map.put("simpleTaskVO", jsonStr);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/task/simple/add", req, String.class);
        String body = response.getBody();
        return body;
    }
    /**
     * 更新简单任务
     * @param task
     * @return
     */
    @RequestMapping("/simple/update")
    public String updateSimpleTask(SimpleTaskVO task){
        HttpHeaders headers = new HttpHeaders();
        Map<String,String> map = new HashMap<String,String>();
        String jsonStr = JsonUtils.objectToJson(task);
        map.put("simpleTaskVO", jsonStr);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/task/simple/update", req, String.class);
        String body = response.getBody();
        return body;
    }


    /**
     * 删除任务
     * @param simpleTaskVO
     * @return
     */
    @RequestMapping("/delete")
    public String updateUser(SimpleTaskVO simpleTaskVO){
        HttpHeaders headers = new HttpHeaders();
        Map<String,String> map = new HashMap<String,String>();
        String jsonStr = JsonUtils.objectToJson(simpleTaskVO);
        map.put("simpleTaskVO", jsonStr);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/task/delete", req, String.class);
        String body = response.getBody();
        return body;
    }


    /**
     * 新增Cron任务
     * @param task
     * @return
     */
    @RequestMapping("/cron/add")
    public String saveCronTask(CronTaskVO task){
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        String jsonStr = JsonUtils.objectToJson(task);
        map.put("cronTaskVO", jsonStr);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/task/cron/add", req, String.class);
        String body = response.getBody();
        return body;
    }
    /**
     * 更新Cron任务
     * @param task
     * @return
     */
    @RequestMapping("/cron/update")
    public String updateCronTask(CronTaskVO task){
        HttpHeaders headers = new HttpHeaders();
        Map<String,String> map = new HashMap<String,String>();
        String jsonStr = JsonUtils.objectToJson(task);
        map.put("cronTaskVO", jsonStr);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/task/cron/update", req, String.class);
        String body = response.getBody();
        return body;
    }
}
