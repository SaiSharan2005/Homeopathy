// src/main/java/com/G19/hospital/DTO/inventory/Batch/BatchDto.java
package com.G19.hospital.DTO.inventory.StockAndBatchTracking;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

import com.G19.hospital.model.inventory.StockAndBatchTracking.Batch.BatchStatus;

@Data
@AllArgsConstructor
public class BatchDto {
    private Long id;
    private Long inventoryItemId;
    private String batchNumber;
    private LocalDate expiryDate;
    private BatchStatus status;
}
