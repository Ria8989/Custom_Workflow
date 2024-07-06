package com.project.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("kswadi123@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);

            mailSender.send(message);
            System.out.println("Mail Sent...");
        }catch (Exception e){
            System.out.println("Error sending mail "+e.getMessage());
        }
    }
}
