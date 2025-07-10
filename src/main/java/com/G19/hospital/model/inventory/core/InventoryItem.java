package com.G19.hospital.model.inventory.core;

import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.InventoryRecord;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", updatable = false)
    private User createdBy;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "common_name")
    private String commonName;

    @Column(name = "source")
    private String source;

    @Column(name = "potency")
    private String potency;

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

    @Column(name = "storage_condit1ions")
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

    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "image_url")
    private BigDecimal sellingPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    // @OneToMany(mappedBy = "inventoryItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    // @JsonManagedReference("inventoryItem-records")
    // private Set<InventoryRecord> records = new HashSet<>();


    public void updateStock(int change) {
        // Implement the logic to update stock based on the change value
    }
}
