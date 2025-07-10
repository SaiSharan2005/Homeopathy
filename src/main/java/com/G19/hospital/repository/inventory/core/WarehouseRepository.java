package com.G19.hospital.repository.inventory.core;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.G19.hospital.model.inventory.core.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findById(Long wareHouseId);
        boolean existsByName(String name);

}
