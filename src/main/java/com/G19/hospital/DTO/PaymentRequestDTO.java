package com.G19.hospital.DTO;

import com.G19.hospital.model.prescription.PaymentMethod;
import java.math.BigDecimal;

public class PaymentRequestDTO {
    private Long prescriptionId;
    private PaymentMethod method;
    private BigDecimal totalAmount;

    // Constructors
    public PaymentRequestDTO() {}
    
    public PaymentRequestDTO(Long prescriptionId, PaymentMethod method, BigDecimal totalAmount) {
        this.prescriptionId = prescriptionId;
        this.method = method;
        this.totalAmount = totalAmount;
    }

    // Getters & Setters
    public Long getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(Long prescriptionId) { this.prescriptionId = prescriptionId; }
    
    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}