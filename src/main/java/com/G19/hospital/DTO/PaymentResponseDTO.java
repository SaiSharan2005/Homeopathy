package com.G19.hospital.DTO;

import com.G19.hospital.model.prescription.PaymentStatus;
import com.G19.hospital.model.prescription.PaymentMethod;
import java.math.BigDecimal;

public class PaymentResponseDTO {
    private Long id;
    private Long prescriptionId;
    private String paymentScreenshotPath;
    private PaymentStatus status;
    private PaymentMethod method;
    private BigDecimal totalAmount;

    // Constructors
    public PaymentResponseDTO() {}
    
    public PaymentResponseDTO(Long id, Long prescriptionId, String paymentScreenshotPath, 
                            PaymentStatus status, PaymentMethod method, BigDecimal totalAmount) {
        this.id = id;
        this.prescriptionId = prescriptionId;
        this.paymentScreenshotPath = paymentScreenshotPath;
        this.status = status;
        this.method = method;
        this.totalAmount = totalAmount;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(Long prescriptionId) { this.prescriptionId = prescriptionId; }
    
    public String getPaymentScreenshotPath() { return paymentScreenshotPath; }
    public void setPaymentScreenshotPath(String paymentScreenshotPath) { this.paymentScreenshotPath = paymentScreenshotPath; }
    
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    
    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}