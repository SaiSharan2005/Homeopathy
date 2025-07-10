package com.G19.hospital.repository.inventory.PurchaseAndReceipt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrder;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrderItem;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrderStatus;

import java.util.List;

public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {
    List<PurchaseOrderItem> findByPurchaseOrderOrderId(Long orderId);
    Page<PurchaseOrder> findAllPage(Pageable pageable);
    Page<PurchaseOrder> findBySupplier_Id(Long supplierId, Pageable pageable);
    Page<PurchaseOrder> findByStatus(PurchaseOrderStatus status, Pageable pageable);

}
