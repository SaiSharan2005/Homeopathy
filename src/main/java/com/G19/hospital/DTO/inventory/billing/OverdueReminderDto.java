// src/main/java/com/G19/hospital/DTO/billing/OverdueReminderDto.java
package com.G19.hospital.DTO.inventory.billing;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OverdueReminderDto {
    private Long reminderId;
    private Long invoiceId;
    private LocalDateTime sentDate;
    private String reminderType;
    private String notes;
}
