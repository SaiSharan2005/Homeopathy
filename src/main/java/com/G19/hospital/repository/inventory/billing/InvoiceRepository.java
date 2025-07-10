// InvoiceRepository.java
package com.G19.hospital.repository.inventory.billing;

import com.G19.hospital.model.inventory.Billing_Payment_Due.Invoice;
import com.G19.hospital.model.inventory.Billing_Payment_Due.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Page<Invoice> findByPatient_Id(Long patientId, Pageable pageable);
    Page<Invoice> findByStatus(InvoiceStatus status, Pageable pageable);
}
