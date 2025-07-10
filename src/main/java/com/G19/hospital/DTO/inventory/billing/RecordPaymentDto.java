// src/main/java/com/G19/hospital/DTO/billing/RecordPaymentDto.java
package com.G19.hospital.DTO.inventory.billing;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecordPaymentDto {
    private Long invoiceId;
    private BigDecimal paymentAmount;
}
