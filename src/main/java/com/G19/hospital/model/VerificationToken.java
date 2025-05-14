// src/main/java/com/G19/hospital/model/VerificationToken.java
package com.G19.hospital.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    // Constructors, getters & setters
    public VerificationToken() {}

    public VerificationToken(String email, String code, LocalDateTime expiresAt) {
        this.email     = email;
        this.code      = code;
        this.expiresAt = expiresAt;
    }

    // … getters/setters …
}
