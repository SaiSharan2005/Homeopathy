// src/main/java/com/G19/hospital/repository/billing/PaymentRepository.java
package com.G19.hospital.repository.inventory.billing;

import com.G19.hospital.model.inventory.Billing_Payment_Due.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findAll(Pageable pageable);
    Page<Payment> findByInvoice_InvoiceId(Long invoiceId, Pageable pageable);
}
