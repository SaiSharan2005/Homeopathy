package com.G19.hospital.repository.inventory.PurchaseAndReceipt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrder;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrderStatus;

import java.util.Optional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Optional<PurchaseOrder> findByOrderId(Long orderId);
    Page<PurchaseOrder> findBySupplier_Id(Long supplierId, Pageable pageable);
    Page<PurchaseOrder> findByStatus(PurchaseOrderStatus status, Pageable pageable);
}
