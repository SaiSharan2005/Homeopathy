package com.G19.hospital.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.PatientDetails;
public interface PatientDetailsRepository extends JpaRepository<PatientDetails, Long>{
    PatientDetails findByPatientId(String patientId);
}
