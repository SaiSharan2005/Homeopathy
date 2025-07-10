
package com.G19.hospital.repository.inventory.core;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.core.InventoryItem;

import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByName(String name);
}
