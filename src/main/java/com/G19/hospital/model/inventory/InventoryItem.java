package com.G19.hospital.model.inventory;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.G19.hospital.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_items")
public class InventoryItem extends AuditableBaseEntity {
    // Audit fields
    @ManyToOne
    @JoinColumn(name = "created_by_id", updatable = false)
    private User createdBy;
    
    @Column(name = "name")
    private String name;
    
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
    
    // Each InventoryItem belongs to a Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;
    
    // One InventoryItem can have many inventory transactions (logs)
    @OneToMany(mappedBy = "inventoryItem", cascade = CascadeType.ALL)
    private Set<InventoryTransaction> transactions = new HashSet<>();

    // Business method to update stock
    public void updateStock(int change) {
        // Implement the logic to update stock based on the change value
    }
}
