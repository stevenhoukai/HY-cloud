package com.yyicbc.controller;


import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.querycondition.RoleQueryVO;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.SysRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/role")
public class RoleController_Consumer {

    //    private static final String REST_URL_PREFIX = "http://yyicbcservice-pro1";
    //    @Value("${spring.redis.host:disabled}")
    //    private String host;
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 查询所有角色all
     *
     * @return
     */
    @RequestMapping("/list")
    public String getRoleList(@RequestParam Integer page,
                              @RequestParam String roleCode,
                              @RequestParam String roleName) {
        RoleQueryVO vo = new RoleQueryVO();
        vo.setPage(page).setRoleCode(roleCode).setRoleName(roleName);
        String jsonStr = JsonUtils.objectToJson(vo);
        Map<String,String> map = new HashMap<String,String>();
        map.put("where",jsonStr);
        return restTemplate.getForObject(REST_URL_PREFIX + "/system/role/list?where={where}", String.class, map);
    }



    /**
     * 新增角色
     * 填写角色名称编码保存
     * @return
     */
    @PostMapping("/add")
    public String addRole(SysRoleVO sysRoleVO) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        String jsonStr = JsonUtils.objectToJson(sysRoleVO);
        map.put("sysRoleVO", jsonStr);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/role/add", req, String.class);
        String body = response.getBody();
        return body;
    }


    /**
     * 更新角色
     *
     * @return
     */
    @RequestMapping("/update")
    public String updateRole(@RequestBody String role) {
        System.out.println(role);
        return null;
    }

    /**
     * 删除角色
     *
     * @return
     */
    @RequestMapping("/delete")
    public String deleteRole(@RequestParam Long roleId) {
        HttpHeaders headers = new HttpHeaders();
        Map<String,Long> map = new HashMap<String,Long>();
        map.put("roleId", roleId);
        HttpEntity<Map<String, Long>> req = new HttpEntity<Map<String, Long>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/role/delete", req, String.class);
        String body = response.getBody();
        return body;
    }

    /**
     * 角色授权
     *
     * @return
     */
    @RequestMapping("/permission")
    public String permissionRole(@RequestParam String roleId,@RequestParam String menuString) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        map.put("roleId", roleId);
        map.put("menuString",menuString);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/role/permission", req, String.class);
        String body = response.getBody();
        return body;
    }

    /**
     *
     * @param roleId
     * @return
     * 获取roleUserList信息
     */
    @RequestMapping("/userlist")
    public String getRoleUserList(@RequestParam Long roleId) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("roleId",roleId.toString());
        return restTemplate.getForObject(REST_URL_PREFIX + "/system/role/userlist?roleId={roleId}", String.class, map);
    }



    /**
     * 用户授权
     *
     * @return
     */
    @RequestMapping("/role_user_edit")
    public String permissionRoleUser(@RequestParam String roleId,@RequestParam String userIds) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        map.put("roleId", roleId);
        map.put("userIds",userIds);
        HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REST_URL_PREFIX + "/system/role/role_user_edit", req, String.class);
        String body = response.getBody();
        return body;
    }

}
