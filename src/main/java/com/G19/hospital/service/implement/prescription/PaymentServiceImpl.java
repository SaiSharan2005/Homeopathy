package com.G19.hospital.service.implement.prescription;

import com.G19.hospital.DTO.PaymentResponseDTO;
import com.G19.hospital.DTO.PaymentRequestDTO;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.prescription.*;
import com.G19.hospital.repository.prescription.*;
import com.G19.hospital.service.PaymentService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final Cloudinary cloudinary;

    // Add image upload method
    @Override
    public String uploadImage(MultipartFile imageFile) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    @Override
    @Transactional
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        Prescription prescription = prescriptionRepository.findById(request.getPrescriptionId())
                .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));

        Payment payment = new Payment();
        payment.setPrescription(prescription);
        payment.setMethod(request.getMethod());
        payment.setTotalAmount(request.getTotalAmount());
        payment.setStatus(request.getMethod() == PaymentMethod.CASH ? PaymentStatus.PAID : PaymentStatus.PENDING);

        Payment savedPayment = paymentRepository.save(payment);
        return mapToResponse(savedPayment);
    }

    @Override
    @Transactional
    public PaymentResponseDTO updatePayment(Long id, String screenshotPath) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND));

        // if (payment.getMethod() == PaymentMethod.ONLINE) {
            payment.setMethod(PaymentMethod.ONLINE);
            payment.setPaymentScreenshotPath(screenshotPath);
            payment.setStatus(PaymentStatus.PAID);
        // }

        return mapToResponse(paymentRepository.save(payment));
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND));
    }
    @Override
    public Payment paidCashByUser(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Payment not found", HttpStatus.NOT_FOUND));
        payment.setMethod(PaymentMethod.CASH);
        payment.setStatus(PaymentStatus.PAID);
        
        return paymentRepository.save(payment);

    }

    private PaymentResponseDTO mapToResponse(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getPrescription().getId(),
                payment.getPaymentScreenshotPath(),
                payment.getStatus(),
                payment.getMethod(),
                payment.getTotalAmount()
        );
    }
}
