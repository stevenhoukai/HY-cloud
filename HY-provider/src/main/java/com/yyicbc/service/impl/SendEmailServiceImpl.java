//package com.yyicbc.service.impl;
//
//import com.yyicbc.beans.RetData;
//import com.yyicbc.beans.StatusCode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.File;
//
///**
// * @author : stv
// * 邮件消息服务类
// */
//@Service
//public class SendEmailServiceImpl {
//
//    @Autowired
//    JavaMailSender javaMailSender;
//
//    /**
//     * 发送最简单的text邮件
//     * @param sender
//     * @param receiver
//     * @param title
//     * @param text
//     * @return
//     * @throws MessagingException
//     */
//    public RetData send(String sender, String receiver, String title, String text) throws MessagingException {
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
//        javaMailSender.send(mainMessage);
//        retData.setCode(StatusCode.OK);
//        retData.setMsg("已成功发送邮件");
//        return retData;
//    }
//
//    /**
//     * 发送含有附件的邮件
//     * @param sender
//     * @param receiver
//     * @param subject
//     * @param content
//     * @param filePath
//     * @return
//     * @throws MessagingException
//     */
//    public RetData sendAttachment(String sender,String receiver,String subject, String content, String filePath) throws MessagingException {
//        RetData retData = new RetData();
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message,true);
//        //发送方
//        helper.setFrom(sender);
//        //接收方
//        helper.setTo(receiver);
//        //邮件主题
//        helper.setSubject(subject);
//        //邮件内容
//        helper.setText(content,true);
//        //邮件的附件
//        FileSystemResource file = new FileSystemResource(new File(filePath));
//        //获取附件的文件名
//        String fileName = file.getFilename();
//        helper.addAttachment(fileName,file);
//        javaMailSender.send(message);
//        retData.setCode(StatusCode.OK);
//        retData.setMsg("已成功发送邮件");
//        return retData;
//    }
//
//    /**
//     * 发送html模板的邮件
//     * @param sender
//     * @param receiver
//     * @param subject
//     * @param content
//     * @return
//     * @throws MessagingException
//     */
//    public RetData sendHtml(String sender,String receiver,String subject, String content) throws MessagingException {
////        content = "<html>\n"+
////                "<body>\n"+
////                "<h2>这是一封有历史意义的HTML邮件,请注意查收!!!</h2>\n"+
////                "</body>\n"+
////                "</html>";
//        RetData retData = new RetData();
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message,true);
//        //发送方
//        helper.setFrom(sender);
//        //接收方
//        helper.setTo(receiver);
//        //邮件主题
//        helper.setSubject(subject);
//        //邮件内容
//        helper.setText(content,true);
//        javaMailSender.send(message);
//        retData.setCode(StatusCode.OK);
//        retData.setMsg("已成功发送邮件");
//        return retData;
//    }
//
//    /**
//     * 含有图片的邮件
//     * @param sender
//     * @param receiver
//     * @param subject
//     * @param content
//     * @param picPath
//     * @param picId
//     * @return
//     * @throws MessagingException
//     */
//    public RetData sendPicture(String sender,String receiver,String subject, String content,
//                              String picPath,String picId) throws MessagingException {
//        RetData retData = new RetData();
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message,true);
//        //发送方
//        helper.setFrom(sender);
//        //接收方
//        helper.setTo(receiver);
//        //邮件主题
//        helper.setSubject(subject);
//        //邮件内容
//        helper.setText(content,true);
//        //邮件的图片
//        FileSystemResource file = new FileSystemResource(new File(picPath));
//        helper.addInline(picId,file);
//        javaMailSender.send(message);
//        retData.setCode(StatusCode.OK);
//        retData.setMsg("已成功发送邮件");
//        return retData;
//    }
//}