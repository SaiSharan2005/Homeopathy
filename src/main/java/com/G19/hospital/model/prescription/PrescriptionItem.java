package com.G19.hospital.model.prescription;

import com.G19.hospital.model.BaseEntity;
import com.G19.hospital.model.inventory.InventoryItem;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescription_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItem extends BaseEntity {

    // Link back to the parent prescription
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;
    
    // Reference to the homeopathic remedy from your inventory
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;
    
    // Dosage instructions (e.g., "1 pellet")
    @Column(name = "dosage", nullable = false)
    private String dosage;
    
    // Frequency of administration (e.g., "3 times a day")
    @Column(name = "frequency", nullable = false)
    private String frequency;
    
    // Duration for which the remedy should be taken (e.g., "7 days")
    @Column(name = "duration", nullable = false)
    private String duration;
    
    // Any additional instructions or notes specific to this remedy
    @Column(name = "additional_instructions", length = 500)
    private String additionalInstructions;
    
}
