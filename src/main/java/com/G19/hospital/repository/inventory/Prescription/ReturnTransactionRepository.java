// src/main/java/com/G19/hospital/repository/prescription/ReturnTransactionRepository.java
package com.G19.hospital.repository.inventory.Prescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.prescription.ReturnTransaction;

public interface ReturnTransactionRepository extends JpaRepository<ReturnTransaction, Long> {
    Page<ReturnTransaction> findAll(Pageable pageable);
}
