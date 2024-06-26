package com.G19.hospital.repository;
import com.G19.hospital.model.Authentication.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PatientDetailsRepository extends JpaRepository<PatientDetails, Long>{
    PatientDetails findByPatientId(String patientId);
}
