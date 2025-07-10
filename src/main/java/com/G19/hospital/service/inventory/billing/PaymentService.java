// src/main/java/com/G19/hospital/service/billing/PaymentService.java
package com.G19.hospital.service.inventory.billing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.G19.hospital.DTO.inventory.billing.PaymentRequestDTO;
import com.G19.hospital.DTO.inventory.billing.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO create(PaymentRequestDTO dto);
    PaymentResponseDTO update(Long paymentId, PaymentRequestDTO dto);
    void delete(Long paymentId);
    PaymentResponseDTO getById(Long paymentId);
    Page<PaymentResponseDTO> getAll(Pageable pageable);
    Page<PaymentResponseDTO> getByInvoice(Long invoiceId, Pageable pageable);
}


// package com.G19.hospital.service;

// import com.G19.hospital.DTO.PaymentRequestDTO;
// import com.G19.hospital.DTO.PaymentResponseDTO;
// import com.G19.hospital.model.inventory.prescription.Payment;
// import com.G19.hospital.model.inventory.prescription.PaymentStatus;

// import java.io.IOException;
// import java.math.BigDecimal;
// import java.util.List;

// import org.springframework.web.multipart.MultipartFile;

// public interface PaymentService {
//     PaymentResponseDTO createPayment(PaymentRequestDTO request);

//     PaymentResponseDTO updatePayment(Long id, String screenshotPath);

//     List<PaymentResponseDTO> getAllPayments();

//     PaymentResponseDTO markAsUnpaid(Long id);

//     Payment getPaymentById(Long id);

//     String uploadImage(MultipartFile imageFile) throws IOException;

//     Payment paidCashByUser(Long id);

//     List<PaymentResponseDTO> getPaymentsForPatient(Long patientId);

//     List<PaymentResponseDTO> getPaymentsForDoctor(Long doctorId);

//     PaymentResponseDTO getPaymentByPrescriptionId(Long prescriptionId);

//     PaymentResponseDTO getCurrentPendingPayment();

//     PaymentResponseDTO getNextPendingPayment();

//     PaymentResponseDTO getLastPendingPayment();

//     PaymentResponseDTO getPreviousPendingPayment(Long id);

//     PaymentResponseDTO getNextPendingPayment(Long id);

//     PaymentResponseDTO setDeliveryStatus(Long paymentId, boolean delivered);

//     /** Record a payment towards the bill (partial or full) */
//     PaymentResponseDTO recordPaymentAmount(Long paymentId, BigDecimal amountPaid);

//     /**
//      * List all payments with dues (paidAmount < totalAmount) for a given patient
//      */
//     List<PaymentResponseDTO> getDuesForPatient(Long patientId);
//     PaymentResponseDTO setStatus(Long id, PaymentStatus st) ;

// }

