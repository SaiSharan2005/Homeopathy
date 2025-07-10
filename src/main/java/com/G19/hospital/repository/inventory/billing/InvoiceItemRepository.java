// InvoiceItemRepository.java
package com.G19.hospital.repository.inventory.billing;

import com.G19.hospital.model.inventory.Billing_Payment_Due.InvoiceItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    Page<InvoiceItem> findByInvoice_InvoiceId(Long invoiceId, Pageable pageable);
    List<InvoiceItem> findByInvoice_InvoiceId(Long invoiceId);
}
