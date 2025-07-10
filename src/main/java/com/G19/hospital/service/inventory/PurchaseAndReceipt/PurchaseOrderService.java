// src/main/java/com/G19/hospital/service/inventory/PurchaseAndReceipt/PurchaseOrderService.java
package com.G19.hospital.service.inventory.PurchaseAndReceipt;

import com.G19.hospital.DTO.inventory.PurchaseOrderDto;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrder;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrderStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseOrderService {
    PurchaseOrder createPurchaseOrder(PurchaseOrderDto dto);
    PurchaseOrder updatePurchaseOrder(Long orderId, PurchaseOrderDto dto);
    void deletePurchaseOrder(Long orderId);
    PurchaseOrder getPurchaseOrderById(Long orderId);
    Page<PurchaseOrder> getPurchaseOrders(Pageable pageable);
    Page<PurchaseOrder> getPurchaseOrdersBySupplier(Long supplierId, Pageable pageable);
    Page<PurchaseOrder> getPurchaseOrdersByStatus(PurchaseOrderStatus status, Pageable pageable);
    PurchaseOrder changePurchaseOrderStatus(Long orderId, PurchaseOrderStatus status);
    PurchaseOrder getPurchaseOrderFullDetail(Long orderId);
    void removePurchaseOrderItem(Long orderId, Long itemId);
}
