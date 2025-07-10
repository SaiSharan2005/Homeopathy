// src/main/java/com/G19/hospital/DTO/prescription/ReturnDto.java
package com.G19.hospital.DTO.inventory.Prescription;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class ReturnDto {
    private Long id;
    private Long dispenseTransactionId;
    private Long returnedById;
    private LocalDateTime returnDate;
    private int quantityReturned;
    private String reason;
}
