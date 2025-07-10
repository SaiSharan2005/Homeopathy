package com.G19.hospital.DTO.inventory.Prescription;

import lombok.Data;

@Data
public class CreatePrescriptionItemDto {
    private Long prescriptionId;
    private Long drugId;
    private Long batchId;
    private String frequency;
    private String duration;
    private int quantity;
    private String additionalInstructions;
}
