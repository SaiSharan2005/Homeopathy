package com.G19.hospital.service.implement.inventory;

import com.G19.hospital.DTO.inventory.PurchaseOrderItemDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.InventoryItem;
import com.G19.hospital.model.inventory.PurchaseOrder;
import com.G19.hospital.model.inventory.PurchaseOrderItem;
import com.G19.hospital.repository.inventory.PurchaseOrderItemRepository;
import com.G19.hospital.repository.inventory.PurchaseOrderRepository;
import com.G19.hospital.repository.inventory.InventoryItemRepository;
import com.G19.hospital.service.inventory.PurchaseOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService {

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Override
    public PurchaseOrderItem createPurchaseOrderItem(PurchaseOrderItemDto itemDto) {
        try {
            PurchaseOrderItem item = new PurchaseOrderItem();
            item.setOrderItemId(itemDto.getOrderItemId());
            item.setQuantityOrdered(itemDto.getQuantityOrdered());
            item.setUnitPrice(itemDto.getUnitPrice());
            PurchaseOrder order = purchaseOrderRepository.findByOrderId(itemDto.getPurchaseOrderId())
                    .orElseThrow(() -> new CustomSecurityException("Purchase order not found", HttpStatus.NOT_FOUND));
            item.setPurchaseOrder(order);
            InventoryItem inventoryItem = inventoryItemRepository.findById(itemDto.getInventoryItemId())
                    .orElseThrow(() -> new CustomSecurityException("Inventory item not found", HttpStatus.NOT_FOUND));
            item.setInventoryItem(inventoryItem);
            return purchaseOrderItemRepository.save(item);
        } catch (Exception ex) {
            log.error("Error creating purchase order item: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to create purchase order item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PurchaseOrderItem updatePurchaseOrderItem(Long orderItemId, PurchaseOrderItemDto itemDto) {
        PurchaseOrderItem existingItem = purchaseOrderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new CustomSecurityException("Purchase order item not found with id: " + orderItemId, HttpStatus.NOT_FOUND));
        try {
            existingItem.setQuantityOrdered(itemDto.getQuantityOrdered());
            existingItem.setUnitPrice(itemDto.getUnitPrice());
            if (itemDto.getPurchaseOrderId() != null) {
                PurchaseOrder order = purchaseOrderRepository.findByOrderId(itemDto.getPurchaseOrderId())
                        .orElseThrow(() -> new CustomSecurityException("Purchase order not found", HttpStatus.NOT_FOUND));
                existingItem.setPurchaseOrder(order);
            }
            if (itemDto.getInventoryItemId() != null) {
                InventoryItem inventoryItem = inventoryItemRepository.findById(itemDto.getInventoryItemId())
                        .orElseThrow(() -> new CustomSecurityException("Inventory item not found", HttpStatus.NOT_FOUND));
                existingItem.setInventoryItem(inventoryItem);
            }
            return purchaseOrderItemRepository.save(existingItem);
        } catch (Exception ex) {
            log.error("Error updating purchase order item: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to update purchase order item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deletePurchaseOrderItem(Long orderItemId) {
        PurchaseOrderItem existingItem = purchaseOrderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new CustomSecurityException("Purchase order item not found with id: " + orderItemId, HttpStatus.NOT_FOUND));
        try {
            purchaseOrderItemRepository.delete(existingItem);
        } catch (Exception ex) {
            log.error("Error deleting purchase order item: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to delete purchase order item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PurchaseOrderItem getPurchaseOrderItemById(Long orderItemId) {
        return purchaseOrderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new CustomSecurityException("Purchase order item not found with id: " + orderItemId, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<PurchaseOrderItem> getPurchaseOrderItemsByOrderId(Long orderId) {
        try {
            return purchaseOrderItemRepository.findByPurchaseOrderOrderId(orderId);
        } catch (Exception ex) {
            log.error("Error retrieving purchase order items: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to retrieve purchase order items", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
