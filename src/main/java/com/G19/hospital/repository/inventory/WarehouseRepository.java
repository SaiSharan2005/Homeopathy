package com.G19.hospital.repository.inventory;

import com.G19.hospital.model.inventory.Supplier;
import com.G19.hospital.model.inventory.Warehouse;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findById(Long wareHouseId);
        boolean existsByName(String name);

}
