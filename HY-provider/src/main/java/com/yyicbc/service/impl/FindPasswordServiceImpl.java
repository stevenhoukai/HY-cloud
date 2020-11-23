//package com.yyicbc.service.impl;
//
//import com.yyicbc.beans.RetData;
//import com.yyicbc.beans.StatusCode;
//import com.yyicbc.beans.security.SysUserVO;
//import com.yyicbc.dao.SysUserDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
///**
// * @author stv
// * 找回密码分为邮件找回和短信找回
// */
//@Service
//public class FindPasswordServiceImpl {
//
//    @Autowired
//    SendEmailServiceImpl sendEmailService;
//
//    @Autowired
//    SendMessageServiceImpl sendMessageService;
//
//    @Autowired
//    SysUserDao sysUserDao;
//
//    @Value("${spring.mail.username}")
//    private String mailSender;
//
//    public RetData send(String usercode, String email, String mobile) throws Exception{
//        RetData retData = null;
//        if(email!=null&&!email.equals("")){
//            SysUserVO sysUserVO = sysUserDao.findSysUserVOByUserCode(usercode);
//            if(sysUserVO == null){
//                retData = new RetData();
//                retData.setCode(StatusCode.ERROR);
//                retData.setMsg("不存在该用户！");
//                return retData;
//            }else{
//                if(!sysUserVO.getEmail().trim().equals(email.trim())){
//                    retData = new RetData();
//                    retData.setCode(StatusCode.ERROR);
//                    retData.setMsg("邮箱与用户信息不匹配！");
//                    return retData;
//                }else{
//                    //TODO 需要对密码进行重置然后发送密码
//                    try {
//                        retData = sendEmailService.send(mailSender, email, "密码找回", "已触发密码找回事件，密码找回功能还未开启");
//                    }catch (Exception e){
//                        throw e;
//                    }
//                }
//            }
//        }else if(mobile!=null&&!mobile.equals("")){
//            retData = new RetData();
//            retData.setCode(StatusCode.OK);
//            retData.setMsg("暂未开通短信平台");
//        }
//        return retData;
//
//    }
//}