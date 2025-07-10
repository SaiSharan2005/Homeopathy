package com.G19.hospital.repository.inventory.PurchaseAndReceipt;

import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrderItem;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {

    // All items for a given order:
    List<PurchaseOrderItem> findByPurchaseOrder_OrderId(Long orderId);

    // Pageable list of items in a given order:
    Page<PurchaseOrderItem> findByPurchaseOrder_OrderId(Long orderId, Pageable pageable);

    // Items whose parent order has this status:
    Page<PurchaseOrderItem> findByPurchaseOrder_Status(PurchaseOrderStatus status, Pageable pageable);

    // Items whose parent order has this supplier:
    Page<PurchaseOrderItem> findByPurchaseOrder_Supplier_Id(Long supplierId, Pageable pageable);

    // You can remove any findAllPage(Pageable) declaration — use the inherited findAll(pg).
}
