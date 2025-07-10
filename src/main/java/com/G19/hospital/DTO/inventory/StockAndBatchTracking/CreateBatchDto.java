// src/main/java/com/G19/hospital/DTO/inventory/Batch/CreateBatchDto.java
package com.G19.hospital.DTO.inventory.StockAndBatchTracking;

import lombok.Data;

import java.time.LocalDate;

import com.G19.hospital.model.inventory.StockAndBatchTracking.Batch.BatchStatus;

@Data
public class CreateBatchDto {
    private Long inventoryItemId;
    private String batchNumber;
    private LocalDate expiryDate;
    private BatchStatus status; // optional (default to ACTIVE if null)
}
