package com.G19.hospital.controller.Authentication;

import com.G19.hospital.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendEmail")
    public String sendEmail() {
        String to = "duginisaisharan@gmail.com";
        String subject = "Test Email Subject";
        String text = "Hello Sai Sharan, this is a test email from Spring Boot.you are my best friend";
        System.out.println("sending mail ");
        emailService.sendSimpleEmail(to, subject, text);
        return "Email sent successfully to " + to + "!";
    }
}