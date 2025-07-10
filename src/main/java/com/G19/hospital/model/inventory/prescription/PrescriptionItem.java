package com.G19.hospital.model.inventory.prescription;

import com.G19.hospital.model.inventory.core.AuditableBaseEntity;
import com.G19.hospital.model.inventory.core.InventoryItem;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prescription_items", indexes = {
        @Index(name = "idx_rx_item_drug", columnList = "drug_id"),
        // @Index(name = "idx_rx_item_batch", columnList = "batch_id")
})
public class PrescriptionItem extends AuditableBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "drug_id", nullable = false)
    private InventoryItem drug;


    @Column(name = "frequency", nullable = false)
    private String frequency;
    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "additional_instructions", length = 500)
    private String additionalInstructions;
}
