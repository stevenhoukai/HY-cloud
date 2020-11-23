//package com.yyicbc.controller;
//
//import com.yyicbc.beans.RetData;
//import com.yyicbc.service.impl.FindPasswordServiceImpl;
//import com.yyicbc.service.impl.SendEmailServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//
//
//@RestController
//@RequestMapping(value = "/system/login",produces = {"application/json;charset=UTF-8"})
//public class MailController {
//    @Autowired
//    private FindPasswordServiceImpl findPasswordService;
//
//    @PostMapping(value = "/findpwd",name = "system/login/findpwd")
//    public RetData send(@RequestBody HashMap<String,Object> map) throws Exception{
//        String usercode = (String) map.get("usercode");
//        String email = (String) map.get("email");
//        String mobile = (String) map.get("mobile");
//        RetData retData  = findPasswordService.send(usercode,email,mobile);
//        return retData;
//    }
//}