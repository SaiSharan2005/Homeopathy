package com.G19.hospital.model.inventory;

import com.G19.hospital.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_items")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InventoryItem extends AuditableBaseEntity {

    // The user who created the inventory item record (for auditing)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", updatable = false)
    private User createdBy;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "common_name")
    private String commonName;

    @Column(name = "source")
    private String source;

    // Potency: Dilution level, e.g., 6X, 30C, 200CK.
    @Column(name = "potency")
    private String potency;
    // Physical form—pellets, tablets, liquid tinctures, gels, creams
    @Column(name = "formulation")
    private String formulation;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "unit")
    private String unit;

    @Column(name = "reorder_level")
    private int reorderLevel;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "storage_conditions")
    private String storageConditions;

    @Column(name = "indications", length = 1000)
    private String indications;

    @Column(name = "contraindications", length = 1000)
    private String contraindications;

    @Column(name = "side_effects", length = 1000)
    private String sideEffects;

    @Column(name = "usage_instructions", length = 1000)
    private String usageInstructions;

    @Column(name = "regulatory_status")
    private String regulatoryStatus;

    @Column(name = "cost_price")
    private double costPrice;

    @Column(name = "selling_price")
    private double sellingPrice;

    // Each InventoryItem belongs to a Category (e.g., homeopathic remedy category)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    // One InventoryItem can have many inventory transactions (logs)
    @OneToMany(mappedBy = "inventoryItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InventoryTransaction> transactions = new HashSet<>();

    // Business method to update stock. This method can be expanded with your business logic.
    public void updateStock(int change) {
        // Implement the logic to update stock based on the change value
    }
}

// Common Name: Provides the colloquial name of the remedy for easier identification.
// Source: Specifies the origin (plant, mineral, animal) of the remedy.
// Potency: Indicates the dilution level, essential in homeopathy.
// Formulation: Describes the physical form (e.g., pellet, liquid) of the remedy.
// Storage Conditions: Details optimal storage requirements to maintain efficacy.
// Indications: Lists ailments or conditions the remedy addresses.
// Contraindications: Specifies scenarios where the remedy should not be used.
// Side Effects: Notes potential adverse reactions.
// Usage Instructions: Provides guidelines on proper administration.
// Regulatory Status: Indicates compliance with health regulations.
// Cost Price & Selling Price: Facilitates financial tracking and pricing strategies.

