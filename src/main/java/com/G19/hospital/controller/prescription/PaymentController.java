package com.G19.hospital.controller.prescription;

import com.G19.hospital.DTO.PaymentRequestDTO;
import com.G19.hospital.DTO.PaymentResponseDTO;
import com.G19.hospital.model.prescription.Payment;
import com.G19.hospital.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

}