package com.G19.hospital.DTO.inventory.Prescription;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class PrescriptionItemDto {
    private Long id;
    private Long prescriptionId;
    private Long drugId;
    private String frequency;
    private String duration;
    private int quantity;
    private String additionalInstructions;
}
