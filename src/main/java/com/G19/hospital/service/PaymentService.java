package com.G19.hospital.service;

import com.G19.hospital.DTO.PaymentRequestDTO;
import com.G19.hospital.DTO.PaymentResponseDTO;
import com.G19.hospital.model.prescription.Payment;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequestDTO request);
    PaymentResponseDTO updatePayment(Long id, String screenshotPath);
    List<PaymentResponseDTO> getAllPayments();
    Payment getPaymentById(Long id);
    String uploadImage(MultipartFile imageFile) throws IOException ;
    Payment paidCashByUser(Long id) ;

}