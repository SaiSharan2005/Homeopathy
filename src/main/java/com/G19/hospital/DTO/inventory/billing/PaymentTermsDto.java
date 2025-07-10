// src/main/java/com/G19/hospital/DTO/billing/PaymentTermsDto.java
package com.G19.hospital.DTO.inventory.billing;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentTermsDto {
    private Long paymentTermsId;
    private String name;
    private Integer daysUntilDue;
}
