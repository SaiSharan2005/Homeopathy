// src/main/java/com/G19/hospital/DTO/billing/PaymentResponseDTO.java
package com.G19.hospital.DTO.inventory.billing;

import com.G19.hospital.model.inventory.Billing_Payment_Due.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long paymentId;
    private Long invoiceId;
    private PaymentMethod method;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String transactionRef;
    private String receiptImageUrl;
    private Long receiptUploadedById;
    private LocalDateTime receiptUploadDate;
}
