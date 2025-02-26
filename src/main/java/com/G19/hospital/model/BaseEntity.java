package com.G19.hospital.model;


import lombok.Getter;

import java.io.Serializable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    public Long getId() {
        return id;
    }

    // Optionally, include a setId method if you want to allow setting the ID
    public void setId(Long id) {
        this.id = id;
    }

}

