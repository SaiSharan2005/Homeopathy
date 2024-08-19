package com.G19.hospital.controller;

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
    public String sendEmail(@RequestParam String Token) {
        String to = "duginisaisharan@gmail.com";
        String subject = "Appointment Booked today  at HomeoPathy";
        String text = "Your Appointment is Booked Succesfully . your token is  "+Token;
        System.out.println("sending mail ");
        emailService.sendSimpleEmail(to, subject, text);
        return "Email sent successfully to " + to + "!";
    }
}