package com.yyicbc.controller;


import com.yyicbc.beans.RetData;
import com.yyicbc.beans.YyResult;
import com.yyicbc.beans.querycondition.RoleQueryVO;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.SysRoleVO;
import com.yyicbc.beans.security.SysUserVO;
import com.yyicbc.service.RoleService;
import com.yyicbc.service.UserService;
import com.yyicbc.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/*
 * @author stevenHou
 * @date 2020/10/15 : 11:14
 * @class RoleController
 * @description 角色控制类
 */
@RestController
@RequestMapping(value = "/system/role" ,produces = {"application/json;charset=UTF-8"})
public class RoleController {

  @Autowired
  RoleService roleService;



  /*
   * @author stevenHou
   * @date 2020/10/15 : 16:08
   * @param  * @param queryVO
   * @return com.yyicbc.beans.RetData
   * @desc 角色列表
   */
  @GetMapping(value = "/list")
  public RetData queryAllRole(RoleQueryVO queryVO){
    RetData result = roleService.all(queryVO);
    return result;
  }


  /*
   * @author stevenHou
   * @date 2020/10/15 : 16:09
   * @param  * @param map
   * @return com.yyicbc.beans.RetData
   * @desc 新增角色
   */
  @PostMapping(value = "/add",name = "/system/role/add")
  public RetData saveUser(SysRoleVO sysRoleVO){
    RetData retData = roleService.saveOrUpdate(sysRoleVO);
    return retData;
  }

  /*
   * @author stevenHou
   * @date 2020/10/15 : 16:10
   * @param  * @param roleId
   * @param menuString
   * @return com.yyicbc.beans.RetData
   * @desc 角色授权
   */
  @PostMapping(value = "/permission",name = "/system/role/permission")
  public RetData permissionRole(String roleId,String menuString) {
    RetData retData = roleService.execPermission(roleId,menuString);
    return retData;
  }


  /*
   * @author stevenHou
   * @date 2020/10/15 : 16:10
   * @param  * @param roleId
   * @return com.yyicbc.beans.RetData
   * 根据roleid获取用户信息
   */
  @GetMapping(value = "/userlist",name = "/system/role/userlist")
  public RetData getRoleUserList(String roleId) {
    RetData result = roleService.getAllRoleUserList(Long.parseLong(roleId));
    return result;
  }


  /*
   * @author stevenHou
   * @date 2020/10/15 : 16:10
   * @param  * @param roleId
   * @param menuString
   * @return com.yyicbc.beans.RetData
   * @desc 用户授权
   */
  @RequestMapping(value = "/role_user_edit",name = "/system/role/role_user_edit")
  public RetData permissionRoleUser(String roleId, String userIds){
    RetData retData = roleService.execUserPermission(roleId,userIds);
    return retData;
  }

  /*
   * @author stevenHou
   * @date 2020/10/15 : 16:10
   * @param  * @param roleId
   * @param menuString
   * @return com.yyicbc.beans.RetData
   * @desc 根据roleId删除角色
   */
  @PostMapping(value = "/delete", name = "/system/role/delete")
  public RetData delete(Long roleId){
    RetData retData = roleService.deleteRoleById(roleId);
    return retData;
  }
}