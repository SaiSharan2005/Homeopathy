package com.G19.hospital.repository;

import com.G19.hospital.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByEmailAndCode(String email, String code);
    void deleteByEmail(String email);
}
