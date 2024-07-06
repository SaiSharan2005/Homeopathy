package com.G19.hospital.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.DoctorRegister;

// Create a separate interface for DoctorRegister
public interface DoctorAuthenticationRepository extends JpaRepository<DoctorRegister, Long> {
    DoctorRegister findByPhoneNumber(String phoneNumber);
    DoctorRegister findByDoctorId(String doctorId);
    // DoctorRegister findById(Long id);
    List<DoctorRegister> findAll();
}