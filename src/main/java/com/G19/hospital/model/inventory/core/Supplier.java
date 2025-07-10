package com.G19.hospital.model.inventory.core;

import com.G19.hospital.model.User;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier extends AuditableBaseEntity {
    // Audit fields

    // @Column(name = "supplier_id", unique = true)
    // private Long supplierId;
    @ManyToOne
    @JoinColumn(name = "created_by_id", updatable = false)
    private User createdBy;
    @Column(name = "name")
    private String name;   
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "contact_details", length = 1000)
    private String contactDetails;
    
    @Column(name = "address", length = 1000)
    private String address;
}
