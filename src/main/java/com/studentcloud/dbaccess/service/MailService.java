package com.studentcloud.dbaccess.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender javaMailSender;

    public MailService(@Qualifier("getJavaMailSender") JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(String emailTo, String subject, String letterBody) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(username);
        message.setTo(emailTo);
        message.setSubject(subject);
        message.setText(letterBody);

        javaMailSender.send(message);
    }
}
