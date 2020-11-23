package com.yyicbc.controller;


import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.YyResult;
import com.yyicbc.beans.business.PO.CompanyBasePO;
import com.yyicbc.beans.querycondition.CompanyQuestVO;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.SysUserVO;
import com.yyicbc.dao.CompanyFileDao;
import com.yyicbc.service.CompanyFileService;
import com.yyicbc.service.UserService;
import com.yyicbc.utils.JsonUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author stevenHou
 * @date 2020/10/14 : 15:46
 * @class UserController
 * @description 用戶節點類
 */
@RestController
@RequestMapping(value = "/system/user", produces = {"application/json;charset=UTF-8"})
public class UserController {


    @Autowired
    UserService userService;

    /*
     * @author stevenHou
     * @date 2020/10/14 : 15:33
     * @param  * @param page
     * @param userCode
     * @param userName
     * @param mobile
     * @return com.yyicbc.beans.RetData
     * @desc 用戶列表查詢
     */
    @GetMapping(value = "/list")
    public RetData getAllUser(@RequestParam Integer page,
                             @RequestParam String userCode,
                             @RequestParam String userName,
                             @RequestParam String mobile) {
        UserQueryVO vo = new UserQueryVO();
        vo.setPage(page).setMobile(mobile).setUserCode(userCode).setUserName(userName);
        RetData result = userService.all(vo);
        return result;
    }

    /*
     * @author stevenHou
     * @date 2020/10/14 : 15:42
     * @param  * @param
     * @return com.yyicbc.beans.RetData
     * @desc 獲取所有公司列表信息
     */
    @GetMapping("/corplist")
    public RetData getAllCorpList() {
        RetData result = userService.findAllCorps();
        return result;
    }

    /*
     * @author stevenHou
     * @date 2020/10/14 : 15:43
     * @param  * @param sysUserVO
     * @return com.yyicbc.beans.RetData
     * @desc 用戶新增
     */
    @RequestMapping("/add")
    public RetData saveUser(SysUserVO sysUserVO){
        RetData retData = userService.saveOrUpdate(sysUserVO);
        return retData;

    }

    /*
     * @author stevenHou
     * @date 2020/10/14 : 15:44
     * @param  * @param sysUserVO
     * @return com.yyicbc.beans.RetData
     * @desc 用戶新增
     */
    @PostMapping(value = "/update")
    public RetData updateUser(SysUserVO sysUserVO) {
        RetData retData = userService.saveOrUpdate(sysUserVO);
        return retData;
    }

    /*
     * @author stevenHou
     * @date 2020/10/14 : 15:44
     * @param  * @param userId
     * @return com.yyicbc.beans.RetData
     * @desc 用戶刪除
     */
    @PostMapping(value = "/delete")
    public RetData delete(@RequestParam Long userId) {
        RetData retData = userService.deleteUserById(userId);
        return retData;
    }

    /*
     * @author stevenHou
     * @date 2020/10/14 : 15:45
     * @param  * @param userId
     * @return com.yyicbc.beans.RetData
     * @desc 用戶密碼重置
     */
    @PostMapping(value = "/resetpwd")
    public RetData resetpwd(@RequestParam Long userId) {
        RetData retData = userService.resetPwdById(userId);
        return retData;
    }

}