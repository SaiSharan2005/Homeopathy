package com.G19.hospital.repository.prescription;

import com.G19.hospital.model.prescription.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Payment findByPrescriptionId(Long findByPrescriptionId);
    List<Payment> findByPrescriptionPatientId(Long patientId);
    Optional<Payment> findByPrescriptionId(Long prescriptionId);

    // find by prescription → doctor.user.id
    List<Payment> findByPrescriptionDoctorId(Long doctorId);
  
} 
