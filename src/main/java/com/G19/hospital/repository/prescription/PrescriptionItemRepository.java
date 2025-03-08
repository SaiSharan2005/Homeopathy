package com.G19.hospital.repository.prescription;

import com.G19.hospital.model.prescription.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {
}
