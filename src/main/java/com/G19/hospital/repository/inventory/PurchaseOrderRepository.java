package com.G19.hospital.repository.inventory;

import com.G19.hospital.model.inventory.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Optional<PurchaseOrder> findByOrderId(Long orderId);
}
