// src/main/java/com/G19/hospital/DTO/billing/PaymentRequestDTO.java
package com.G19.hospital.DTO.inventory.billing;

import com.G19.hospital.model.inventory.Billing_Payment_Due.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentRequestDTO {
    @NotNull
    private Long invoiceId;
    @NotNull
    private PaymentMethod method;
    @NotNull @Min(0)
    private BigDecimal amount;
    private LocalDateTime paymentDate;        // optional, defaults to now
    private String transactionRef;
    private String receiptImageUrl;
    private Long receiptUploadedById;
    private LocalDateTime receiptUploadDate;  // optional, defaults to now if image provided
}
