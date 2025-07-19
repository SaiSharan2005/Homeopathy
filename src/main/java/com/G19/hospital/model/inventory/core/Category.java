package com.G19.hospital.model.inventory.core;

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
    @Column(name = "name")
    private String name;
    
    @Column(name = "description", length = 1000)
    private String description;
    

    @ManyToOne
    @JoinColumn(name = "created_by_id", updatable = false, nullable = true)
    private User createdBy;
    
    // One Category can have many InventoryItems
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<InventoryItem> inventoryItems = new HashSet<>();
}
