package com.G19.hospital.repository.inventory.core;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.core.Supplier;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findById(Long supplierId);
    boolean existsById(Long supplierId);
    boolean existsByName(String name);
}
