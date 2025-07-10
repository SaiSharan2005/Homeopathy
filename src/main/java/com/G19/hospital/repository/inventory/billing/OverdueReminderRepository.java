// src/main/java/com/G19/hospital/repository/billing/OverdueReminderRepository.java
package com.G19.hospital.repository.inventory.billing;

import com.G19.hospital.model.inventory.Billing_Payment_Due.OverdueReminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OverdueReminderRepository extends JpaRepository<OverdueReminder, Long> {
    Page<OverdueReminder> findAll(Pageable pageable);
    Page<OverdueReminder> findByInvoice_InvoiceId(Long invoiceId, Pageable pageable);

    /**
     * Find all reminders scheduled between the start and end of the given day.
     */
    List<OverdueReminder> findBySentDateBetween(LocalDateTime dayStart, LocalDateTime dayEnd);
}
