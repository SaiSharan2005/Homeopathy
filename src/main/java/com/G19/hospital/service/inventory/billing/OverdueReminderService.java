// src/main/java/com/G19/hospital/service/billing/OverdueReminderService.java
package com.G19.hospital.service.inventory.billing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.G19.hospital.DTO.inventory.billing.CreateOverdueReminderDto;
import com.G19.hospital.DTO.inventory.billing.OverdueReminderDto;

import java.util.List;

public interface OverdueReminderService {
    OverdueReminderDto create(CreateOverdueReminderDto dto);
    OverdueReminderDto update(Long id, CreateOverdueReminderDto dto);
    void delete(Long id);
    OverdueReminderDto getById(Long id);
    Page<OverdueReminderDto> getAll(Pageable pageable);
    Page<OverdueReminderDto> getByInvoice(Long invoiceId, Pageable pageable);

    /**
     * Fetch and process all reminders scheduled for today.
     * This is typically invoked by a scheduled task.
     */
    void processTodaysReminders();
}
