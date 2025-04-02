package com.G19.hospital.repository.prescription;

import com.G19.hospital.model.prescription.Payment;
import com.G19.hospital.model.prescription.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPrescriptionId(Long findByPrescriptionId);
} 
