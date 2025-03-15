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

    // Each InventoryItem belongs to a Category (for example, homeopathic remedy category)
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
