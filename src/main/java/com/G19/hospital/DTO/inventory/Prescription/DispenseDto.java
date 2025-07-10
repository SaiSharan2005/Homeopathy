// src/main/java/com/G19/hospital/DTO/prescription/DispenseDto.java
package com.G19.hospital.DTO.inventory.Prescription;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class DispenseDto {
    private Long id;
    private Long rxItemId;
    private Long dispensedById;
    private Long batchId;
    private LocalDateTime dispenseDate;
    private Long warehouseId;
}
