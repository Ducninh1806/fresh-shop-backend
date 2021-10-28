//package com.ducninh.freshfood.service.impl;
//
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//
//@Service
//public class EmailServiceImpl {
//
//    @Resource
//    private JavaMailSender javaMailSender;
//
//    public void sendMail(String toEmail, String body, String subject) {
//        try {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setTo("freshshop2711@gmail.com");
//            mailMessage.setSubject("acd");
//            mailMessage.setText("1231");
//
//            javaMailSender.send(mailMessage);
//        } catch (Exception e) {
//
//        }
//
//    }
//
//    public void sendEmailWithAttachment() throws MessagingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//
//        helper.setTo("freshshop2711@gmail.com");
//        helper.setSubject("Test attachment 1");
//        helper.setText("Please find the attached document below.");
//
//        ClassPathResource classPathResource = new ClassPathResource("Attachment.pdf");
//        helper.addAttachment(classPathResource.getFilename(), classPathResource);
//        javaMailSender.send(mimeMessage);
//
//    }
//}
