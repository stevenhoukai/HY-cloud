package com.yyicbc.controller;


import com.yyicbc.beans.RetData;
import com.yyicbc.beans.YyResult;
import com.yyicbc.beans.security.ChangePwdVO;
import com.yyicbc.beans.security.SysUserVO;
import com.yyicbc.service.UserService;
import com.yyicbc.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/*
 * @author stevenHou
 * @date 2020/10/14 : 15:04
 * @class UserAuthController
 * @description  用戶登錄登出
 */
@RestController
@RequestMapping(value = "/system/user", produces = {"application/json;charset=UTF-8"})
public class UserAuthController {

    @Autowired
    UserService userService;


    /*
     * @author stevenHou
     * @date 2020/10/14 : 15:02
     * @param  * @param usercode
     * @param password
     * @param request
     * @return com.yyicbc.beans.RetData
     * @desc 用戶的登錄
     */
    @PostMapping(value = "/login")
    public RetData user(@RequestParam(required = true) String usercode,
                        @RequestParam(required = true) String password, HttpServletRequest request) {
        RetData result = null;
        try {
            result = userService.execUserLogin(usercode, password);
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /*
     * @author stevenHou
     * @date 2020/10/14 : 15:03
     * @param  * @param usercode
     * @return com.yyicbc.beans.RetData
     * @desc 用戶登出
     */
    @PostMapping(value = "/logout")
    public RetData queryUser(@RequestParam(required = true) String usercode) {
        RetData result = null;
        try {
            result = userService.execUserLogout(usercode);
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /*
     * @author stevenHou
     * @date 2020/10/14 : 15:03
     * @param  * @param changePwdVO
     * @return com.yyicbc.beans.RetData
     * @desc 用戶修改密碼
     */
    @PostMapping(value = "/changepwd")
    public RetData saveUser(ChangePwdVO changePwdVO) {
        RetData retData = null;
        try {
            retData = userService.saveOrUpdate(changePwdVO);
        } catch (Exception e) {
            throw e;
        }
        return retData;
    }

}