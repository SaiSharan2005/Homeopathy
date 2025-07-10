// src/main/java/com/G19/hospital/service/implement/billing/OverdueReminderServiceImpl.java
package com.G19.hospital.service.implement.inventory.billing;

import com.G19.hospital.DTO.inventory.billing.CreateOverdueReminderDto;
import com.G19.hospital.DTO.inventory.billing.OverdueReminderDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.Billing_Payment_Due.OverdueReminder;
import com.G19.hospital.repository.inventory.billing.InvoiceRepository;
import com.G19.hospital.repository.inventory.billing.OverdueReminderRepository;
import com.G19.hospital.service.inventory.billing.OverdueReminderService;
import com.G19.hospital.model.inventory.Billing_Payment_Due.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OverdueReminderServiceImpl implements OverdueReminderService {

    private final OverdueReminderRepository repo;
    private final InvoiceRepository invoiceRepo;

    @Override
    public OverdueReminderDto create(CreateOverdueReminderDto dto) {
        Invoice inv = invoiceRepo.findById(dto.getInvoiceId())
            .orElseThrow(() -> new CustomSecurityException("Invoice not found", HttpStatus.NOT_FOUND));

        OverdueReminder r = new OverdueReminder();
        r.setInvoice(inv);
        r.setSentDate(dto.getSentDate());
        r.setReminderType(dto.getReminderType());
        r.setNotes(dto.getNotes());
        OverdueReminder saved = repo.save(r);
        return toDto(saved);
    }

    @Override
    public OverdueReminderDto update(Long id, CreateOverdueReminderDto dto) {
        OverdueReminder r = repo.findById(id)
            .orElseThrow(() -> new CustomSecurityException("Reminder not found", HttpStatus.NOT_FOUND));
        if (dto.getInvoiceId() != null) {
            Invoice inv = invoiceRepo.findById(dto.getInvoiceId())
                .orElseThrow(() -> new CustomSecurityException("Invoice not found", HttpStatus.NOT_FOUND));
            r.setInvoice(inv);
        }
        r.setSentDate(dto.getSentDate());
        r.setReminderType(dto.getReminderType());
        r.setNotes(dto.getNotes());
        return toDto(repo.save(r));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new CustomSecurityException("Reminder not found", HttpStatus.NOT_FOUND);
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public OverdueReminderDto getById(Long id) {
        return repo.findById(id)
            .map(this::toDto)
            .orElseThrow(() -> new CustomSecurityException("Reminder not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OverdueReminderDto> getAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OverdueReminderDto> getByInvoice(Long invoiceId, Pageable pageable) {
        invoiceRepo.findById(invoiceId).orElseThrow(() ->
            new CustomSecurityException("Invoice not found", HttpStatus.NOT_FOUND));
        return repo.findByInvoice_InvoiceId(invoiceId, pageable)
                   .map(this::toDto);
    }

    private OverdueReminderDto toDto(OverdueReminder r) {
        return new OverdueReminderDto(
            r.getReminderId(),
            r.getInvoice().getInvoiceId(),
            r.getSentDate(),
            r.getReminderType(),
            r.getNotes()
        );
    }

    /**
     * Runs every day at 8:00 AM server time.
     * Fetches all reminders scheduled for "today" and processes them.
     */
    @Override
    @Scheduled(cron = "0 0 8 * * *")
    @Transactional(readOnly = true)
    public void processTodaysReminders() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay().minusNanos(1);

        List<OverdueReminder> todays = repo.findBySentDateBetween(start, end);
        for (OverdueReminder r : todays) {
            // TODO: send email/SMS to r.getInvoice().getPatient()
            //     via your notification service, using r.getReminderType() and r.getNotes()
            System.out.println("Sending " + r.getReminderType()
                + " reminder for invoice " + r.getInvoice().getInvoiceId()
                + " to be sent at " + r.getSentDate());
        }
    }
}
