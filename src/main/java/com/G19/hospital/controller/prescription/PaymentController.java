package com.G19.hospital.controller.prescription;

import com.G19.hospital.DTO.PaymentRequestDTO;
import com.G19.hospital.DTO.PaymentResponseDTO;
import com.G19.hospital.model.User;
import com.G19.hospital.model.prescription.Payment;
import com.G19.hospital.repository.PatientDetailsRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.DoctorServices;
import com.G19.hospital.service.PatientServices;
import com.G19.hospital.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final UserRepository userRepository;   // service that knows how to find a patient by phone
    private final DoctorServices doctorServices;     // service that knows how to find a doctor by phone

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @PutMapping("/{id}/complete-payment")
    public ResponseEntity<PaymentResponseDTO> completePayment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {
        
        // Upload to Cloudinary instead of local storage
        String imageUrl = paymentService.uploadImage(file);
        return ResponseEntity.ok(paymentService.updatePayment(id, imageUrl));
    }

    @PutMapping("/{id}/mark-unpaid")
    public ResponseEntity<PaymentResponseDTO> markAsUnpaid(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.markAsUnpaid(id));
    }


    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PutMapping("/{id}/cash-payment")
    public ResponseEntity<Payment> cashPaid(
            @PathVariable Long id){
                return ResponseEntity.ok(paymentService.paidCashByUser(id));
    }

    @GetMapping("/prescription/{id}")
public ResponseEntity<PaymentResponseDTO> getPaymentByPrescriptionId(@PathVariable Long id) {
    return ResponseEntity.ok(paymentService.getPaymentByPrescriptionId(id));
}


    @GetMapping("/patient")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsForPatient() {
        // 1) get raw Authentication
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        // 2) extract username (in your app, the phone number)
        String phone = authentication.getName();

        // 3) load the patient by phone
        User patient = userRepository.findByUsername(phone).get();  // or whatever your method is

        // 4) fetch payments for that patient
        List<PaymentResponseDTO> list =
            paymentService.getPaymentsForPatient(patient.getId());

        return ResponseEntity.ok(list);
    }

    /**
     * Returns all payments for the currently authenticated doctor.
     */
    @GetMapping("/doctor")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsForDoctor() {
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();
        String phone = authentication.getName();

        User doctor = doctorServices
            .getDoctorInfoByUserName(phone);  // or your equivalent

        List<PaymentResponseDTO> list =
            paymentService.getPaymentsForDoctor(doctor.getId());

        return ResponseEntity.ok(list);
    }
}