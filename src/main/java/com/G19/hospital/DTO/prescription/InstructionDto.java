// src/main/java/com/G19/hospital/DTO/prescription/InstructionDto.java
package com.G19.hospital.DTO.prescription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
public class InstructionDto {
    private LocalDateTime dateIssued;
    private String generalInstructions;
}
