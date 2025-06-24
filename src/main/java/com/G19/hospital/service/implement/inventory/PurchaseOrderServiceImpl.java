package com.G19.hospital.service.implement.inventory;

import com.G19.hospital.DTO.inventory.PurchaseOrderDto;
import com.G19.hospital.DTO.inventory.PurchaseOrderItemDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.InventoryRecord;
import com.G19.hospital.model.inventory.PurchaseOrder;
import com.G19.hospital.model.inventory.PurchaseOrderItem;
import com.G19.hospital.model.inventory.InventoryItem;
import com.G19.hospital.repository.inventory.InventoryItemRepository;
import com.G19.hospital.repository.inventory.InventoryRecordRepository;
import com.G19.hospital.repository.inventory.PurchaseOrderRepository;
import com.G19.hospital.repository.inventory.SupplierRepository;
import com.G19.hospital.service.inventory.InventoryRecordService;
import com.G19.hospital.service.inventory.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    @Autowired
    private InventoryItemRepository inventoryItemRepository;
    @Autowired
    private InventoryRecordRepository inventoryRecordRepository;
    
    @Autowired
    private InventoryRecordService inventoryRecordService;

    @Override
    public PurchaseOrder createPurchaseOrder(PurchaseOrderDto dto) {
        try {
            // 1. Build the PurchaseOrder
            PurchaseOrder order = new PurchaseOrder();
            order.setOrderId(dto.getOrderId());
            order.setOrderDate(dto.getOrderDate());
            order.setStatus(dto.getStatus());
            order.setTotalAmount(dto.getTotalAmount());
            order.setSupplier(
                supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> 
                        new CustomSecurityException("Supplier not found", HttpStatus.NOT_FOUND)
                    )
            );

            // 2. Map each DTO item into a PurchaseOrderItem and attach to order
            for (PurchaseOrderItemDto itemDto : dto.getPurchaseOrderItems()) {
                InventoryItem invItem = inventoryItemRepository.findById(itemDto.getInventoryItemId())
                    .orElseThrow(() ->
                        new CustomSecurityException(
                            "Inventory item not found: " + itemDto.getInventoryItemId(),
                            HttpStatus.NOT_FOUND
                        )
                    );
                
                PurchaseOrderItem item = new PurchaseOrderItem();
                item.setOrderItemId(itemDto.getOrderItemId());
                item.setQuantityOrdered(itemDto.getQuantityOrdered());
                item.setUnitPrice(itemDto.getUnitPrice());
                item.setInventoryItem(invItem);
                
                // link back to parent
                item.setPurchaseOrder(order);
                order.getPurchaseOrderItems().add(item);
            }

            // 3. Persist order + items (cascade saves the items too)
            PurchaseOrder saved = purchaseOrderRepository.save(order);

            // 4. For each saved item, bump up the corresponding InventoryRecord
            for (PurchaseOrderItem savedItem : saved.getPurchaseOrderItems()) {
                // you could also decide which record (warehouse) here;
                // assuming a single record per item or you have logic to select one:
                Optional<InventoryRecord> records =
                inventoryRecordRepository.findByInventoryItemIdAndWarehouseId(
                        savedItem.getInventoryItem().getId(),1L
                    );
                if (records.isEmpty()) {
                    throw new CustomSecurityException(
                        "No inventory record found for item " + savedItem.getInventoryItem().getId(),
                        HttpStatus.NOT_FOUND
                    );
                }
                // just pick the first record, or loop if you need to split across warehouses
                // InventoryRecord record = records.get(0);
                
                // your existing service method
                inventoryRecordService.increaseQuantity(
                    records.get().getId(),
                    savedItem.getQuantityOrdered()
                );
            }

            return saved;
            
        } catch (CustomSecurityException cse) {
            throw cse; // rethrow with proper status
        } catch (Exception ex) {
            log.error("Error creating purchase order: {}", ex.getMessage(), ex);
            throw new CustomSecurityException(
                "Failed to create purchase order", 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    @Override
    public PurchaseOrder updatePurchaseOrder(Long orderId, PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder existingOrder = purchaseOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomSecurityException("Purchase order not found with id: " + orderId, HttpStatus.NOT_FOUND));
        try {
            existingOrder.setOrderDate(purchaseOrderDto.getOrderDate());
            existingOrder.setStatus(purchaseOrderDto.getStatus());
            existingOrder.setTotalAmount(purchaseOrderDto.getTotalAmount());
            if (purchaseOrderDto.getSupplierId() != null) {
                existingOrder.setSupplier(supplierRepository.findById(purchaseOrderDto.getSupplierId())
                        .orElseThrow(() -> new CustomSecurityException("Supplier not found", HttpStatus.NOT_FOUND)));
            }
            return purchaseOrderRepository.save(existingOrder);
        } catch (Exception ex) {
            log.error("Error updating purchase order: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to update purchase order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deletePurchaseOrder(Long orderId) {
        PurchaseOrder existingOrder = purchaseOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomSecurityException("Purchase order not found with id: " + orderId, HttpStatus.NOT_FOUND));
        try {
            purchaseOrderRepository.delete(existingOrder);
        } catch (Exception ex) {
            log.error("Error deleting purchase order: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to delete purchase order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PurchaseOrder getPurchaseOrderById(Long orderId) {
        return purchaseOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomSecurityException("Purchase order not found with id: " + orderId, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrders() {
        try {
            return purchaseOrderRepository.findAll();
        } catch (Exception ex) {
            log.error("Error retrieving purchase orders: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to retrieve purchase orders", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PurchaseOrder recalculateTotal(Long orderId) {
        PurchaseOrder order = getPurchaseOrderById(orderId);
        try {
            order.setTotalAmount(order.calculateTotal());
            return purchaseOrderRepository.save(order);
        } catch (Exception ex) {
            log.error("Error recalculating total for purchase order: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to recalculate total", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
