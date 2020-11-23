//package com.yyicbc.service.impl;
//
//import com.yyicbc.beans.RetData;
//import com.yyicbc.beans.StatusCode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
///**
// * @author : stv
// * 短信消息服务类
// */
//@Service
//public class SendMessageServiceImpl {
//
//    @Autowired
//    JavaMailSender javaMailSender;
//
//    public RetData send(String sender, String receiver, String title, String text){
//        RetData retData = new RetData();
//        //建立邮件消息
//        SimpleMailMessage mainMessage = new SimpleMailMessage();
//        //发送方
//        mainMessage.setFrom(sender);
//        //接收方
//        mainMessage.setTo(receiver);
//        //发送的标题
//        mainMessage.setSubject(title);
//        //发送的内容
//        mainMessage.setText(text);
//
//        javaMailSender.send(mainMessage);
//
//        retData.setCode(StatusCode.OK);
//        retData.setMsg("已成功发送邮件");
//
//        return retData;
//
//    }
//}