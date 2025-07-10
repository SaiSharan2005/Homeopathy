// src/main/java/com/G19/hospital/DTO/billing/CreateOverdueReminderDto.java
package com.G19.hospital.DTO.inventory.billing;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateOverdueReminderDto {
    @NotNull
    private Long invoiceId;
    @NotNull
    private LocalDateTime sentDate;      // when to send
    @NotNull
    private String reminderType;         // e.g. "EMAIL" or "SMS"
    private String notes;
}
