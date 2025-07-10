package com.G19.hospital.DTO.prescription;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InstructionDto {
    private LocalDateTime rxDate;
    private String notes;
}
