// src/main/java/com/G19/hospital/service/EmailVerificationService.java
package com.G19.hospital.service;

import com.G19.hospital.model.VerificationToken;
import com.G19.hospital.repository.VerificationTokenRepository;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailVerificationService {

    private static final int    CODE_LENGTH    = 4;
    private static final int    EXPIRE_MINUTES = 10;

    @Autowired
    private VerificationTokenRepository tokenRepo;

    @Autowired
    private EmailService emailService;

    /**
     * Generate a 4‑digit code, save it with expiry, and email it.
     */
    public void sendVerificationCode(String email) throws MessagingException {
        // 1) generate code
        String code = String.format("%04d", new Random().nextInt(10_000));

        // 2) save (overwriting any prior)
        tokenRepo.deleteByEmail(email);
        VerificationToken token = new VerificationToken(
            email, code, LocalDateTime.now().plusMinutes(EXPIRE_MINUTES)
        );
        tokenRepo.save(token);

        // 3) send as HTML email
        Map<String,Object> props = new HashMap<>();
        props.put("code", code);
        props.put("expireMinutes", EXPIRE_MINUTES);
            emailService.sendHtmlEmail(
                email,
                "Your Email Verification Code",
                props,
                "email-verification"
            );
            
    }

    /**
     * Check code and that it hasn’t expired.
     */
    public boolean verifyCode(String email, String code) {
        return tokenRepo
            .findByEmailAndCode(email, code)
            .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
            .map(t -> {
                tokenRepo.delete(t);   // one‑time use
                return true;
            })
            .orElse(false);
    }

    
}
