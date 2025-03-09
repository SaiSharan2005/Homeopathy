package com.G19.hospital.DTO.prescription;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDto {
    private Long id;
    private String prescriptionNumber;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime dateIssued;
    private String generalInstructions;
    private Long bookingAppointmentId; // Optional: include if needed
    private List<PrescriptionItemDto> prescriptionItems;
}
