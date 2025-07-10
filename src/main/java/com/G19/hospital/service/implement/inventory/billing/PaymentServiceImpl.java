// src/main/java/com/G19/hospital/service/implement/billing/PaymentServiceImpl.java
package com.G19.hospital.service.implement.inventory.billing;

import com.G19.hospital.DTO.inventory.billing.PaymentRequestDTO;
import com.G19.hospital.DTO.inventory.billing.PaymentResponseDTO;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.Billing_Payment_Due.Invoice;
import com.G19.hospital.model.inventory.Billing_Payment_Due.Payment;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.inventory.billing.InvoiceRepository;
import com.G19.hospital.repository.inventory.billing.PaymentRepository;
import com.G19.hospital.service.inventory.billing.PaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepo;
    private final InvoiceRepository invoiceRepo;
    private final UserRepository userRepo;

    @Override
    public PaymentResponseDTO create(PaymentRequestDTO dto) {
        Invoice inv = invoiceRepo.findById(dto.getInvoiceId())
                .orElseThrow(() -> new CustomSecurityException("Invoice not found", HttpStatus.NOT_FOUND));

        Payment p = new Payment();
        p.setInvoice(inv);
        p.setMethod(dto.getMethod());
        p.setAmount(dto.getAmount());
        p.setPaymentDate(dto.getPaymentDate() != null
                ? dto.getPaymentDate() : LocalDateTime.now());
        p.setTransactionRef(dto.getTransactionRef());

        if (dto.getReceiptImageUrl() != null) {
            p.setReceiptImageUrl(dto.getReceiptImageUrl());
            p.setReceiptUploadDate(dto.getReceiptUploadDate() != null
                    ? dto.getReceiptUploadDate() : LocalDateTime.now());
            if (dto.getReceiptUploadedById() != null) {
                User uploader = userRepo.findById(dto.getReceiptUploadedById())
                        .orElseThrow(() -> new CustomSecurityException(
                                "Uploader user not found", HttpStatus.NOT_FOUND));
                p.setReceiptUploadedBy(uploader);
            }
        }

        Payment saved = paymentRepo.save(p);
        return toDto(saved);
    }

    @Override
    public PaymentResponseDTO update(Long id, PaymentRequestDTO dto) {
        Payment p = paymentRepo.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND));

        if (dto.getMethod() != null) p.setMethod(dto.getMethod());
        if (dto.getAmount() != null) p.setAmount(dto.getAmount());
        if (dto.getPaymentDate() != null) p.setPaymentDate(dto.getPaymentDate());
        p.setTransactionRef(dto.getTransactionRef());

        if (dto.getReceiptImageUrl() != null) {
            p.setReceiptImageUrl(dto.getReceiptImageUrl());
            p.setReceiptUploadDate(dto.getReceiptUploadDate() != null
                    ? dto.getReceiptUploadDate() : LocalDateTime.now());
            if (dto.getReceiptUploadedById() != null) {
                User uploader = userRepo.findById(dto.getReceiptUploadedById())
                        .orElseThrow(() -> new CustomSecurityException(
                                "Uploader user not found", HttpStatus.NOT_FOUND));
                p.setReceiptUploadedBy(uploader);
            }
        }

        return toDto(paymentRepo.save(p));
    }

    @Override
    public void delete(Long id) {
        if (!paymentRepo.existsById(id)) {
            throw new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND);
        }
        paymentRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getById(Long id) {
        return paymentRepo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponseDTO> getAll(Pageable pageable) {
        return paymentRepo.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponseDTO> getByInvoice(Long invoiceId, Pageable pageable) {
        invoiceRepo.findById(invoiceId).orElseThrow(() ->
            new CustomSecurityException("Invoice not found", HttpStatus.NOT_FOUND));
        return paymentRepo.findByInvoice_InvoiceId(invoiceId, pageable)
                .map(this::toDto);
    }

    private PaymentResponseDTO toDto(Payment p) {
        return new PaymentResponseDTO(
                p.getPaymentId(),
                p.getInvoice().getInvoiceId(),
                p.getMethod(),
                p.getAmount(),
                p.getPaymentDate(),
                p.getTransactionRef(),
                p.getReceiptImageUrl(),
                p.getReceiptUploadedBy() != null ? p.getReceiptUploadedBy().getId() : null,
                p.getReceiptUploadDate()
        );
    }
}



// package com.G19.hospital.service.implement.prescription;

// import com.G19.hospital.DTO.PaymentResponseDTO;
// import com.G19.hospital.DTO.PaymentRequestDTO;
// import com.G19.hospital.exceptions.security.CustomSecurityException;
// import com.G19.hospital.model.inventory.prescription.*;
// import com.G19.hospital.repository.inventory.Prescription.PrescriptionRepository;
// import com.G19.hospital.repository.prescription.*;
// import com.G19.hospital.service.PaymentService;
// import com.G19.hospital.service.inventory.InventoryRecordService;
// import com.cloudinary.Cloudinary;
// import com.cloudinary.utils.ObjectUtils;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.IOException;
// import java.math.BigDecimal;
// import java.util.List;
// import java.util.Map;

// @Service
// @RequiredArgsConstructor
// public class PaymentServiceImpl implements PaymentService {

//     private final PaymentRepository paymentRepository;
//     private final PrescriptionRepository prescriptionRepository;
//     private final Cloudinary cloudinary;
//     private final InventoryRecordService inventoryRecordService;



//     // Add image upload method
//     @Override
//     public String uploadImage(MultipartFile imageFile) throws IOException {
//         Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
//         return uploadResult.get("url").toString();
//     }

//     @Override
//     @Transactional
//     public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
//         Prescription prescription = prescriptionRepository.findById(request.getPrescriptionId())
//                 .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));

//         Payment payment = new Payment();
//         payment.setPrescription(prescription);
//         payment.setMethod(request.getMethod());
//         payment.setTotalAmount(request.getTotalAmount());
//         payment.setStatus(request.getMethod() == PaymentMethod.CASH ? PaymentStatus.PAID : PaymentStatus.PENDING);

//         Payment savedPayment = paymentRepository.save(payment);
//         return mapToResponse(savedPayment);
//     }

//     @Override
//     @Transactional
//     public PaymentResponseDTO updatePayment(Long id, String screenshotPath) {
//         Payment payment = paymentRepository.findById(id)
//                 .orElseThrow(() -> new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND));

//         // if (payment.getMethod() == PaymentMethod.ONLINE) {
//             payment.setMethod(PaymentMethod.ONLINE);
//             payment.setPaymentScreenshotPath(screenshotPath);
//             payment.setStatus(PaymentStatus.PAID);
//         // }
//                // --- NEW: decrease inventory for each prescription item ---
//                 var prescription = payment.getPrescription();
//                 prescription.getPrescriptionItems()
//                    .forEach(pi -> {
//                         Long itemId = pi.getInventoryItem().getId();
//                         int qty    = Integer.parseInt(pi.getQuantity());
//                         // fetch all records for this item, pick first
//                         var records = inventoryRecordService.getInventoryRecordsByItemId(itemId);
//                         if (!records.isEmpty()) {
//                           Long recordId = records.get(0).getId();
//                           inventoryRecordService.decreaseQuantity(recordId, qty);
//                         }
//                     });
        
        
//         return mapToResponse(paymentRepository.save(payment));
//     }

//     @Override
//     public List<PaymentResponseDTO> getAllPayments() {
//         return paymentRepository.findAll().stream()
//                 .map(this::mapToResponse)
//                 .toList();
//     }

//     @Override
//     public Payment getPaymentById(Long id) {
//         return paymentRepository.findById(id)
//                 .orElseThrow(() -> new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND));
//     }
//     @Override
//     public Payment paidCashByUser(Long id) {
//         Payment payment = paymentRepository.findById(id)
//                 .orElseThrow(() -> new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND));
//         payment.setMethod(PaymentMethod.CASH);
//         payment.setStatus(PaymentStatus.PAID);
//         var prescription = payment.getPrescription();
//         prescription.getPrescriptionItems()
//            .forEach(pi -> {
//                 Long itemId = pi.getInventoryItem().getId();
//                 int qty    = Integer.parseInt(pi.getQuantity());
//                 // fetch all records for this item, pick first
//                 var records = inventoryRecordService.getInventoryRecordsByItemId(itemId);
//                 if (!records.isEmpty()) {
//                   Long recordId = records.get(0).getId();
//                   inventoryRecordService.decreaseQuantity(recordId, qty);
//                 }
//             });

//         return paymentRepository.save(payment);

//     }

//     private PaymentResponseDTO mapToResponse(Payment payment) {
//         return new PaymentResponseDTO(
//                 payment.getId(),
//                 payment.getPrescription().getId(),
//                 payment.getPaymentScreenshotPath(),
//                 payment.getStatus(),
//                 payment.getMethod(),
//                 payment.getTotalAmount(),
//                 payment.getPaidAmount(),
//                 payment.getDeliveryStatus()
//         );
//     }
//     @Override
//     public List<PaymentResponseDTO> getPaymentsForPatient(Long patientId) {
//       return paymentRepository
//         .findByPrescriptionPatientId(patientId)
//         .stream()
//         .map(this::mapToResponse)
//         .toList();
//     }
  
//     @Override
//     public List<PaymentResponseDTO> getPaymentsForDoctor(Long doctorId) {
//       return paymentRepository
//         .findByPrescriptionDoctorId(doctorId)
//         .stream()
//         .map(this::mapToResponse)
//         .toList();
//     }
  
//     @Override
//     public PaymentResponseDTO markAsUnpaid(Long id) {
//         Payment p = paymentRepository.findById(id)
//             .orElseThrow(() -> new RuntimeException("Payment not found"));
//         p.setStatus(PaymentStatus.FAILED);
//         p.setPaymentScreenshotPath(null);
//         // --- NEW: restore inventory for each prescription item ---
//         var prescription = p.getPrescription();
//         prescription.getPrescriptionItems()
//             .forEach(pi -> {
//                 Long itemId = pi.getInventoryItem().getId();
//                 int qty    = Integer.parseInt(pi.getQuantity());
//                 var records = inventoryRecordService.getInventoryRecordsByItemId(itemId);
//                 if (!records.isEmpty()) {
//                   Long recordId = records.get(0).getId();
//                   inventoryRecordService.increaseQuantity(recordId, qty);
//                 }
//             });

//         Payment updated = paymentRepository.save(p);
//         return mapToResponse(updated);
//     }
//     private PaymentResponseDTO toDTO(Payment p) {
//         PaymentResponseDTO dto = new PaymentResponseDTO();
//         dto.setId(p.getId());
//         dto.setStatus(p.getStatus());
//         dto.setMethod(p.getMethod());
//         dto.setTotalAmount(p.getTotalAmount());
//         dto.setPaymentScreenshotPath(p.getPaymentScreenshotPath());
//         if (p.getPrescription() != null) {
//             dto.setPrescriptionId(p.getPrescription().getId());
//         }
//         return dto;
//     }

//     @Override
//     public PaymentResponseDTO getPaymentByPrescriptionId(Long prescriptionId) {
//         Payment payment = paymentRepository.findByPrescriptionId(prescriptionId)
//             .orElseThrow(() -> new CustomSecurityException("Payment not found for the prescription", HttpStatus.NOT_FOUND));
//         return mapToResponse(payment);
//     }

//     @Override
//     public PaymentResponseDTO getCurrentPendingPayment() {
//       return paymentRepository
//         .findFirstByStatusOrderByIdAsc(PaymentStatus.PENDING)
//         .map(this::mapToResponse)
//         .orElse(null);
//     }
//     @Override
//     public PaymentResponseDTO getLastPendingPayment() {
//       return paymentRepository
//         .findFirstByStatusOrderByIdDesc(PaymentStatus.PENDING)
//         .map(this::mapToResponse)
//         .orElse(null);
//     }
  
//     @Override
//     public PaymentResponseDTO getNextPendingPayment() {
//       List<Payment> pending = paymentRepository
//         .findAllByStatusOrderByIdAsc(PaymentStatus.PENDING);
//       if (pending.size() <= 1) return null;
//       return mapToResponse(pending.get(1));
//     }
  
//     @Override
//     public PaymentResponseDTO getPreviousPendingPayment(Long id) {
//       return paymentRepository
//         .findFirstByStatusAndIdLessThanOrderByIdDesc(PaymentStatus.PENDING, id)
//         .map(this::mapToResponse)
//         .orElse(null);
//     }
  
//     @Override
//     public PaymentResponseDTO getNextPendingPayment(Long id) {
//       return paymentRepository
//         .findFirstByStatusAndIdGreaterThanOrderByIdAsc(PaymentStatus.PENDING, id)
//         .map(this::mapToResponse)
//         .orElse(null);
//     }
  

//     @Override
//     @Transactional
//     public PaymentResponseDTO setDeliveryStatus(Long id, boolean delivered) {
//         Payment p = paymentRepository.findById(id)
//             .orElseThrow(() -> new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND));
//         p.setDeliveryStatus(delivered);
//         // no inventory change here
//         return mapToResponse(paymentRepository.save(p));
//     }

//     @Override
//     @Transactional
//     public PaymentResponseDTO recordPaymentAmount(Long id, BigDecimal amountPaid) {
//         Payment p = paymentRepository.findById(id)
//             .orElseThrow(() -> new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND));

//         BigDecimal newPaid = p.getPaidAmount().add(amountPaid);
//         p.setPaidAmount(newPaid);

//         // determine status
//         if (newPaid.compareTo(p.getTotalAmount()) >= 0) {
//             p.setStatus(PaymentStatus.PAID);
//         } else {
//             p.setStatus(PaymentStatus.DUE);
//         }

//         // if fully paid, you may trigger inventory decrease here (if not done already)
//         // … your existing inventory logic …

//         return mapToResponse(paymentRepository.save(p));
//     }
//     @Override
//     public PaymentResponseDTO setStatus(Long id, PaymentStatus st) {
//     Payment p = paymentRepository.findById(id)
//             .orElseThrow(() -> new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND));
//     p.setStatus(st);
//     return mapToResponse(paymentRepository.save(p));
// }


//     @Override
//     public List<PaymentResponseDTO> getDuesForPatient(Long patientId) {
//         return paymentRepository
//             .findByStatusAndPrescriptionPatientId(PaymentStatus.DUE,patientId)
//             .stream()
//             .map(this::mapToResponse)
//             .toList();
//     }

// }

