// src/main/java/com/G19/hospital/controller/EmailVerificationController.java
package com.G19.hospital.controller;

import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.EmailVerificationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verify")
public class EmailVerificationController {

    @Autowired
    private EmailVerificationService verificationService;
    @Autowired
    private UserRepository userRepository;

    /** 1) Request a code to be sent */
    @PostMapping("/request")
    public String requestCode(@RequestParam String email) {
        try {
            verificationService.sendVerificationCode(email);
            return "Verification code sent to " + email;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send verification code: " + e.getMessage();
        }
    }

    /** 2) Verify the code user submits */
    @PostMapping("/confirm")
    public String confirmCode(@RequestParam String email,
                              @RequestParam String code) {
        boolean valid = verificationService.verifyCode(email, code);
        if (valid) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // assuming username is the unique identifier
        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new CustomSecurityException("User not found with username: " + username));
        user.setVerified(true);
            // <-- here you can create the user or perform your “task”
            return "Email verified successfully!";
        } else {
            return "Invalid or expired code.";
        }
    }
}
