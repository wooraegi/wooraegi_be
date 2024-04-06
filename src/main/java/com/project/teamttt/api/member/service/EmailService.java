package com.project.teamttt.api.member.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String recipient;

    private MimeMessage createMessage(String temporaryPassword, String email) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("wooraegi 임시 비밀번호 발송 메일입니다.");
        message.setText("wooraegi 임시 비밀번호 : " + temporaryPassword);
        message.setFrom(new InternetAddress(recipient));

        return  message;
    }

    public void sendMail(String temporaryPassword, String email) throws Exception{
        try{
            MimeMessage mimeMessage = createMessage(temporaryPassword, email);
            javaMailSender.send(mimeMessage);
        }catch (MailException mailException){
            mailException.printStackTrace();
            throw   new IllegalAccessException();
        }
    }
}

