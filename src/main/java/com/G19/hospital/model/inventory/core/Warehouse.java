package com.G19.hospital.model.inventory.core;

import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.InventoryRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouses")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @JsonIgnore
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private Set<InventoryRecord> inventoryRecords = new HashSet<>();
}
