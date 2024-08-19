package com.G19.hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.G19.hospital.model.PatientRegister;


public interface PatientAuthenticationRepository extends JpaRepository<PatientRegister, Long> {
    PatientRegister findByPhoneNumber(String phoneNumber);
    PatientRegister findByPatientId(String patientId);
    // List<PatientRegister> findByPatientNameContainingIgnoreCaseAndPatientDetails_CityContainingIgnoreCase(String patientName, String city);
  @Query("SELECT p FROM PatientRegister p WHERE LOWER(p.patientName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.patientId) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
List<PatientRegister> searchPatients(@Param("keyword") String keyword);


    long count();
}



