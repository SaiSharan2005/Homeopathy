package com.G19.hospital.repository.inventory;

import com.G19.hospital.model.inventory.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
    List<InventoryTransaction> findByInventoryItemId(Long inventoryItemId);
}
