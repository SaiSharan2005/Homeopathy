package com.G19.hospital.model.inventory;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

import com.G19.hospital.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends AuditableBaseEntity {

        // Audit fields
    @ManyToOne
    @JoinColumn(name = "created_by_id", updatable = false)
    private User createdBy;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description", length = 1000)
    private String description;
    
    // One Category can have many InventoryItems
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<InventoryItem> inventoryItems = new HashSet<>();
}
