// PrescriptionRepository.java
package com.G19.hospital.repository.inventory.Prescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.prescription.Prescription;

import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Optional<Prescription> findByBookingAppointment_BookingId(Long bookingId);
    Optional<Prescription> findByBookingAppointment_Token(String token);
    Page<Prescription> findByPrescribedBy_Id(Long doctorId, Pageable pageable);
    Page<Prescription> findByPatient_Id(Long patientId, Pageable pageable);
    Page<Prescription> findByPatient_IdOrderByRxDateDesc(Long patientId,Pageable pageable);

}

