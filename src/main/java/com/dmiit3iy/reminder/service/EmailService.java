package com.dmiit3iy.reminder.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Data

public class EmailService implements Sender{
    private final JavaMailSender mailSender;

    private final String from;

    private final String subject;

    @Autowired
    public EmailService(JavaMailSender mailSender,
                        @Value("${spring.mail.username}") String from,
                        @Value("${spring.mail.subject:Default Subject}") String subject) {
        this.mailSender = mailSender;
        this.from = from;
        this.subject = subject;
    }


    public void sendMessage(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(from);

        mailSender.send(message);
        System.out.println("Email отправлен!");
    }
}
