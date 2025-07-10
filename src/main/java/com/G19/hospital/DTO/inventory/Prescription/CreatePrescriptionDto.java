package com.G19.hospital.DTO.inventory.Prescription;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreatePrescriptionDto {
    private Long patientId;
    private Long doctorId;
    private Long bookingAppointmentId;  // optional
    private LocalDateTime rxDate;       // optional; defaults to now
    private String notes;
}
