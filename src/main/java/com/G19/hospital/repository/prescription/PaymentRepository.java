package com.G19.hospital.repository.prescription;

import com.G19.hospital.model.prescription.Payment;
import com.G19.hospital.model.prescription.PaymentStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
  // first pending → lowest id
  Optional<Payment> findFirstByStatusOrderByIdAsc(PaymentStatus status);

  // last pending → highest id
  Optional<Payment> findFirstByStatusOrderByIdDesc(PaymentStatus status);

  // all pendings in id order
  List<Payment> findAllByStatusOrderByIdAsc(PaymentStatus status);

  Optional<Payment> findFirstByStatusAndIdLessThanOrderByIdDesc(
      PaymentStatus status, Long id);

  // the pending payment immediately after this id
  Optional<Payment> findFirstByStatusAndIdGreaterThanOrderByIdAsc(
      PaymentStatus status, Long id);
// @Query("""
//       SELECT p
//       FROM Payment p
//       WHERE p.prescription.patient.id = :patientId
//         AND p.paidAmount < p.totalAmount
//       """)
    List<Payment> findByStatusAndPrescriptionPatientId(PaymentStatus status,Long id );
    // (@Param("patientId") Long patientId);
      

} 
