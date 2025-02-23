package com.G19.hospital.repository;

import com.G19.hospital.model.Prescription;
import com.G19.hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatient(User patient);
    List<Prescription> findByDoctor(User doctor);
}
