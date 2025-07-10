package com.G19.hospital.DTO.prescription;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PrescriptionDto {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private Long bookingAppointmentId;
    private LocalDateTime rxDate;       // maps to Prescription.rxDate
    private String notes;               // maps to Prescription.notes
}

