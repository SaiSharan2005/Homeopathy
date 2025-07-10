package com.G19.hospital.controller.inventory.billing;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.G19.hospital.DTO.inventory.billing.PaymentRequestDTO;
import com.G19.hospital.DTO.inventory.billing.PaymentResponseDTO;
import com.G19.hospital.service.inventory.billing.PaymentService;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /** 1. Create a new Payment */
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @Valid @RequestBody PaymentRequestDTO request) 
    {
        PaymentResponseDTO created = paymentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** 2. Update an existing Payment */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(
            @PathVariable("id") Long id,
            @Valid @RequestBody PaymentRequestDTO request) 
    {
        PaymentResponseDTO updated = paymentService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    /** 3. Delete a Payment */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("id") Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** 4. Get Payment by ID */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable("id") Long id) {
        PaymentResponseDTO dto = paymentService.getById(id);
        return ResponseEntity.ok(dto);
    }

    /** 5. Get all Payments (paginated) */
    @GetMapping
    public ResponseEntity<Page<PaymentResponseDTO>> getAllPayments(Pageable pageable) {
        Page<PaymentResponseDTO> page = paymentService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    /** 6. Get Payments by Invoice ID (paginated) */
    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<Page<PaymentResponseDTO>> getByInvoice(
            @PathVariable("invoiceId") Long invoiceId,
            Pageable pageable) 
    {
        Page<PaymentResponseDTO> page = paymentService.getByInvoice(invoiceId, pageable);
        return ResponseEntity.ok(page);
    }
}
// package com.G19.hospital.controller.prescription;

// import com.G19.hospital.DTO.PaymentRequestDTO;
// import com.G19.hospital.DTO.PaymentResponseDTO;
// import com.G19.hospital.model.User;
// import com.G19.hospital.model.inventory.prescription.Payment;
// import com.G19.hospital.model.inventory.prescription.PaymentStatus;
// import com.G19.hospital.repository.UserRepository;
// import com.G19.hospital.service.DoctorServices;
// import com.G19.hospital.service.PaymentService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.security.core.Authentication;
// import java.io.IOException;
// import java.math.BigDecimal;
// import java.util.List;

// @RestController
// @RequestMapping("/api/payments")
// @RequiredArgsConstructor
// public class PaymentController {
//     private final PaymentService paymentService;
//     private final UserRepository userRepository; // service that knows how to find a patient by phone
//     private final DoctorServices doctorServices; // service that knows how to find a doctor by phone

//     @PostMapping
//     public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO request) {
//         return ResponseEntity.ok(paymentService.createPayment(request));
//     }

//     @PutMapping("/{id}/complete-payment")
//     public ResponseEntity<PaymentResponseDTO> completePayment(
//             @PathVariable Long id,
//             @RequestParam("file") MultipartFile file) throws IOException {

//         // Upload to Cloudinary instead of local storage
//         String imageUrl = paymentService.uploadImage(file);
//         return ResponseEntity.ok(paymentService.updatePayment(id, imageUrl));
//     }

//     @PutMapping("/{id}/mark-unpaid")
//     public ResponseEntity<PaymentResponseDTO> markAsUnpaid(@PathVariable Long id) {
//         return ResponseEntity.ok(paymentService.markAsUnpaid(id));
//     }

//     @GetMapping
//     public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
//         return ResponseEntity.ok(paymentService.getAllPayments());
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
//         return ResponseEntity.ok(paymentService.getPaymentById(id));
//     }

//     @PutMapping("/{id}/cash-payment")
//     public ResponseEntity<Payment> cashPaid(
//             @PathVariable Long id) {
//         return ResponseEntity.ok(paymentService.paidCashByUser(id));
//     }

//     @GetMapping("/prescription/{id}")
//     public ResponseEntity<PaymentResponseDTO> getPaymentByPrescriptionId(@PathVariable Long id) {
//         return ResponseEntity.ok(paymentService.getPaymentByPrescriptionId(id));
//     }

//     @GetMapping("/patient")
//     public ResponseEntity<List<PaymentResponseDTO>> getPaymentsForPatient() {
//         // 1) get raw Authentication
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//         // 2) extract username (in your app, the phone number)
//         String phone = authentication.getName();

//         // 3) load the patient by phone
//         User patient = userRepository.findByUsername(phone).get(); // or whatever your method is

//         // 4) fetch payments for that patient
//         List<PaymentResponseDTO> list = paymentService.getPaymentsForPatient(patient.getId());

//         return ResponseEntity.ok(list);
//     }

//     /**
//      * Returns all payments for the currently authenticated doctor.
//      */
//     @GetMapping("/doctor")
//     public ResponseEntity<List<PaymentResponseDTO>> getPaymentsForDoctor() {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         String phone = authentication.getName();

//         User doctor = doctorServices
//                 .getDoctorInfoByUserName(phone); // or your equivalent

//         List<PaymentResponseDTO> list = paymentService.getPaymentsForDoctor(doctor.getId());

//         return ResponseEntity.ok(list);
//     }

//     @GetMapping("/current")
//     public ResponseEntity<PaymentResponseDTO> getCurrentPendingPayment() {
//         PaymentResponseDTO dto = paymentService.getCurrentPendingPayment();
//         return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.noContent().build();
//     }

//     @GetMapping("/next")
//     public ResponseEntity<PaymentResponseDTO> getNextPendingPayment() {
//         PaymentResponseDTO dto = paymentService.getNextPendingPayment();
//         return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.noContent().build();
//     }

//     @GetMapping("/last")
//     public ResponseEntity<PaymentResponseDTO> getLastPendingPayment() {
//         PaymentResponseDTO dto = paymentService.getLastPendingPayment();
//         return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.noContent().build();
//     }

//     @GetMapping("/{id}/previous")
//     public ResponseEntity<PaymentResponseDTO> previous(@PathVariable Long id) {
//         PaymentResponseDTO dto = paymentService.getPreviousPendingPayment(id);
//         return dto != null
//                 ? ResponseEntity.ok(dto)
//                 : ResponseEntity.noContent().build();
//     }

//     /** next pending after {id}, or 204 if none */
//     @GetMapping("/{id}/next")
//     public ResponseEntity<PaymentResponseDTO> next(@PathVariable Long id) {
//         PaymentResponseDTO dto = paymentService.getNextPendingPayment(id);
//         return dto != null
//                 ? ResponseEntity.ok(dto)
//                 : ResponseEntity.noContent().build();
//     }

//     @PutMapping("/{id}/delivery")
//     public ResponseEntity<PaymentResponseDTO> setDelivery(
//             @PathVariable Long id,
//             @RequestParam boolean delivered) {
//         return ResponseEntity.ok(paymentService.setDeliveryStatus(id, delivered));
//     }

//     /** 2) Record a partial/full payment amount */
//     @PutMapping("/{id}/pay")
//     public ResponseEntity<PaymentResponseDTO> payAmount(
//             @PathVariable Long id,
//             @RequestParam BigDecimal amount) {
//         return ResponseEntity.ok(paymentService.recordPaymentAmount(id, amount));
//     }

//     /** 3) List all due payments for a patient */
//     @GetMapping("/dues/{patientId}")
//     public ResponseEntity<List<PaymentResponseDTO>> duesForPatient(
//             @PathVariable Long patientId) {
//         List<PaymentResponseDTO> dues = paymentService.getDuesForPatient(patientId);
//         return ResponseEntity.ok(dues);
//     }

//     @PutMapping("/{id}/status")
//     public ResponseEntity<PaymentResponseDTO> setStatus(
//             @PathVariable Long id,
//             @RequestParam String status) {
//         return ResponseEntity.ok(paymentService.setStatus(id, PaymentStatus.valueOf(status)));
//     }

// }