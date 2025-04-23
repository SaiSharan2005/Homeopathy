package com.G19.hospital.service.inventory;

import com.G19.hospital.DTO.inventory.PurchaseOrderItemDto;
import com.G19.hospital.model.inventory.PurchaseOrderItem;
import java.util.List;

public interface PurchaseOrderItemService {
    PurchaseOrderItem createPurchaseOrderItem(PurchaseOrderItemDto itemDto);
    PurchaseOrderItem updatePurchaseOrderItem(Long orderItemId, PurchaseOrderItemDto itemDto);
    void deletePurchaseOrderItem(Long orderItemId);
    PurchaseOrderItem getPurchaseOrderItemById(Long orderItemId);
    List<PurchaseOrderItem> getPurchaseOrderItemsByOrderId(Long orderId);
}
