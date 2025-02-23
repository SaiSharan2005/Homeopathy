package com.G19.hospital.DTO;

import lombok.Data;

@Data
public class PrescriptionDto {
    
    private Long patientId;
    private Long doctorId;
    private Long appointmentId;
    // private String imageUrl;
    private String description;
}
