package com.G19.hospital.model.inventory;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

import com.G19.hospital.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouses")
public class Warehouse extends AuditableBaseEntity {
    // Audit fields
    @ManyToOne
    @JoinColumn(name = "created_by_id", updatable = false)
    private User createdBy;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "location")
    private String location;
    
    // One Warehouse can store many InventoryRecords
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private Set<InventoryRecord> inventoryRecords = new HashSet<>();
}
