package com.G19.hospital.repository;

import com.G19.hospital.model.Authentication.PatientRegister;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientAuthenticationRepository extends JpaRepository<PatientRegister, Long> {
    PatientRegister findByPhoneNumber(String phoneNumber);
    PatientRegister findByPatientId(String patientId);
}

