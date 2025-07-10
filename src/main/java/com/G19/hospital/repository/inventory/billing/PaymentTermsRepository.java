// src/main/java/com/G19/hospital/repository/billing/PaymentTermsRepository.java
package com.G19.hospital.repository.inventory.billing;

import com.G19.hospital.model.inventory.Billing_Payment_Due.PaymentTerms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTermsRepository extends JpaRepository<PaymentTerms, Long> {
    // No custom methods needed for basic CRUD + paging
}
