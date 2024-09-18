package com.dmiit3iy.reminder.controller;

import com.dmiit3iy.reminder.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Value("${spring.mail.subject}")
    String s;
    @Value("${spring.mail.subject}")
    String ss;

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmail() {

        System.out.println(s);
        emailService.sendMessage("tigerdim@gmail.com","Тест!!!!");
        return "Email отправлен!";
    }
}
