// src/main/java/com/G19/hospital/service/implement/inventory/PurchaseAndReceipt/PurchaseOrderServiceImpl.java
package com.G19.hospital.service.implement.inventory.PurchaseAndReceipt;

import com.G19.hospital.DTO.inventory.PurchaseOrderDto;
import com.G19.hospital.DTO.inventory.PurchaseOrderItemDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrder;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrderItem;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrderStatus;
import com.G19.hospital.model.inventory.core.Supplier;
import com.G19.hospital.repository.inventory.PurchaseAndReceipt.PurchaseOrderRepository;
import com.G19.hospital.repository.inventory.PurchaseAndReceipt.PurchaseOrderItemRepository;
import com.G19.hospital.repository.inventory.core.InventoryItemRepository;
import com.G19.hospital.repository.inventory.core.SupplierRepository;
import com.G19.hospital.service.inventory.PurchaseAndReceipt.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired private PurchaseOrderRepository orderRepo;
    @Autowired private PurchaseOrderItemRepository itemRepo;
    @Autowired private SupplierRepository supplierRepo;
    @Autowired private InventoryItemRepository inventoryItemRepository;

    @Override
    public PurchaseOrder createPurchaseOrder(PurchaseOrderDto dto) {
        Supplier sup = supplierRepo.findById(dto.getSupplierId())
                .orElseThrow(() -> new CustomSecurityException("Supplier not found", HttpStatus.NOT_FOUND));

        PurchaseOrder po = new PurchaseOrder();
        po.setSupplier(sup);
        po.setOrderDate(dto.getOrderDate() == null ? LocalDateTime.now() : dto.getOrderDate());
        po.setStatus(dto.getStatus() == null ? PurchaseOrderStatus.CREATED : dto.getStatus());

        // Map items
        Set<PurchaseOrderItem> items = dto.getItems().stream().map(i -> {
            PurchaseOrderItem p = new PurchaseOrderItem();
            p.setQuantityOrdered(i.getQuantityOrdered());
            p.setUnitPrice(i.getUnitPrice());
            p.setInventoryItem(
                inventoryItemRepository.findById(i.getInventoryItemId())
                .orElseThrow(() -> new CustomSecurityException("Inventory Item not found", HttpStatus.NOT_FOUND))
                // assume inventoryItemRepo injected similarly
                            );
            p.setPurchaseOrder(po);
            return p;
        }).collect(Collectors.toSet());
        po.setPurchaseOrderItems(items);

        computeTotals(po);
        return orderRepo.save(po);
    }

    @Override
    public PurchaseOrder updatePurchaseOrder(Long orderId, PurchaseOrderDto dto) {
        PurchaseOrder po = getPurchaseOrderById(orderId);
        if (dto.getOrderDate() != null) po.setOrderDate(dto.getOrderDate());
        if (dto.getStatus() != null)     po.setStatus(dto.getStatus());

        if (dto.getItems() != null) {
            // Replace existing items
            itemRepo.deleteAll(po.getPurchaseOrderItems());
            Set<PurchaseOrderItem> newItems = dto.getItems().stream().map(i -> {
                PurchaseOrderItem p = new PurchaseOrderItem();
                p.setQuantityOrdered(i.getQuantityOrdered());
                p.setUnitPrice(i.getUnitPrice());
                p.setInventoryItem(
                inventoryItemRepository.findById(i.getInventoryItemId())
                .orElseThrow(() -> new CustomSecurityException("Inventory Item not found", HttpStatus.NOT_FOUND))
                // assume inventoryItemRepo injected similarly
                            );
                p.setPurchaseOrder(po);
                return p;
            }).collect(Collectors.toSet());
            po.setPurchaseOrderItems(newItems);
        }

        computeTotals(po);
        return orderRepo.save(po);
    }

    @Override
    public void deletePurchaseOrder(Long orderId) {
        PurchaseOrder po = getPurchaseOrderById(orderId);
        orderRepo.delete(po);
    }

    @Override
    public PurchaseOrder getPurchaseOrderById(Long orderId) {
        return orderRepo.findById(orderId)
            .orElseThrow(() -> new CustomSecurityException("PurchaseOrder not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<PurchaseOrder> getPurchaseOrders(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }

    @Override
    public Page<PurchaseOrder> getPurchaseOrdersBySupplier(Long supplierId, Pageable pageable) {
        return orderRepo.findBySupplier_Id(supplierId, pageable);
    }

    @Override
    public Page<PurchaseOrder> getPurchaseOrdersByStatus(PurchaseOrderStatus status, Pageable pageable) {
        return orderRepo.findByStatus(status, pageable);
    }

    @Override
    public PurchaseOrder changePurchaseOrderStatus(Long orderId, PurchaseOrderStatus status) {
        PurchaseOrder po = getPurchaseOrderById(orderId);
        po.setStatus(status);
        return orderRepo.save(po);
    }

    @Override
    public PurchaseOrder getPurchaseOrderFullDetail(Long orderId) {
        PurchaseOrder po = getPurchaseOrderById(orderId);
        // if you have LAZY, initialize items:
        po.getPurchaseOrderItems().size();
        return po;
    }

    @Override
    public void removePurchaseOrderItem(Long orderId, Long itemId) {
        PurchaseOrder po = getPurchaseOrderById(orderId);
        PurchaseOrderItem itm = itemRepo.findById(itemId)
            .orElseThrow(() -> new CustomSecurityException("Item not found", HttpStatus.NOT_FOUND));
        if (!itm.getPurchaseOrder().getOrderId().equals(orderId)) {
            throw new CustomSecurityException("Item not part of this PO", HttpStatus.BAD_REQUEST);
        }
        po.getPurchaseOrderItems().remove(itm);
        computeTotals(po);
        orderRepo.save(po);
        itemRepo.delete(itm);
    }

    private void computeTotals(PurchaseOrder po) {
        BigDecimal total = po.getPurchaseOrderItems().stream()
            .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantityOrdered())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        po.setTotalAmount(total);
    }
}
