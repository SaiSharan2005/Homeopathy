package com.G19.hospital.DTO.inventory.Prescription;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data @AllArgsConstructor
public class PrescriptionDto {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long bookingAppointmentId;
    private LocalDateTime rxDate;
    private String notes;
    private Set<PrescriptionItemDto> items;
}