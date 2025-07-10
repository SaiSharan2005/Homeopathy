package com.G19.hospital.DTO.prescription;

import lombok.Data;

@Data
public class PrescriptionItemDto {
    private Long inventoryItemId;    // InventoryItem PK
    private Long batchId;            // which Batch to pull from
    private String frequency;
    private String duration;
    private int quantity;
    private String additionalInstructions;
}