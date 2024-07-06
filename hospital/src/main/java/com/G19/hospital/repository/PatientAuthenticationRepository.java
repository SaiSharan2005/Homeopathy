package com.G19.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.PatientRegister;


public interface PatientAuthenticationRepository extends JpaRepository<PatientRegister, Long> {
    PatientRegister findByPhoneNumber(String phoneNumber);
    PatientRegister findByPatientId(String patientId);
}

