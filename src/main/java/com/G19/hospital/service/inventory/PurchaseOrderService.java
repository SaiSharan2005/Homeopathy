package com.G19.hospital.service.inventory;

import com.G19.hospital.DTO.inventory.PurchaseOrderDto;
import com.G19.hospital.model.inventory.PurchaseOrder;
import java.util.List;

public interface PurchaseOrderService {
    PurchaseOrder createPurchaseOrder(PurchaseOrderDto purchaseOrderDto);
    PurchaseOrder updatePurchaseOrder(Long orderId, PurchaseOrderDto purchaseOrderDto);
    void deletePurchaseOrder(Long orderId);
    PurchaseOrder getPurchaseOrderById(Long orderId);
    List<PurchaseOrder> getAllPurchaseOrders();
    // Optionally, a method to recalculate the total based on order items
    PurchaseOrder recalculateTotal(Long orderId);
}
