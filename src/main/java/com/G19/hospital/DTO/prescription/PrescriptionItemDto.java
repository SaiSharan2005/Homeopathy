package com.G19.hospital.DTO.prescription;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemDto {
    private Long id;
    private Long inventoryItemId;
    private String dosage;
    private String frequency;
    private String duration;
    private String additionalInstructions;
}
