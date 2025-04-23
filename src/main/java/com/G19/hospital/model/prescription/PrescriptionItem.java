package com.G19.hospital.model.prescription;

import com.G19.hospital.model.BaseEntity;
import com.G19.hospital.model.inventory.InventoryItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "prescription_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItem extends BaseEntity {

    // Use the same unique value for the back reference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    @JsonBackReference(value = "prescription-items")
    private Prescription prescription;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;
    
    @Column(name = "dosage", nullable = false)
    private String dosage;
    
    @Column(name = "frequency", nullable = false)
    private String frequency;
    
    @Column(name = "duration", nullable = false)
    private String duration;

    
    @Column(name = "quantity", nullable = false)
    private String quantity;
    
    @Column(name = "additional_instructions", length = 500)
    private String additionalInstructions;
}
