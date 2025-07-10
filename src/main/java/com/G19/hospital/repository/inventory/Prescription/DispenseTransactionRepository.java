// DispenseTransactionRepository.java
package com.G19.hospital.repository.inventory.Prescription;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.prescription.DispenseTransaction;

import java.util.List;

public interface DispenseTransactionRepository extends JpaRepository<DispenseTransaction, Long> {
    List<DispenseTransaction> findByRxItem_Prescription_Id(Long prescriptionId);
}
