package com.G19.hospital.repository.inventory;

import com.G19.hospital.model.inventory.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {
    List<PurchaseOrderItem> findByPurchaseOrderOrderId(Long orderId);
}
