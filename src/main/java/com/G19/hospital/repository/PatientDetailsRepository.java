package com.G19.hospital.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.PatientDetails;
// import java.util.List;
import java.util.Optional;

public interface PatientDetailsRepository extends JpaRepository<PatientDetails, Long>{
    Optional<PatientDetails> findById(Long id);
}