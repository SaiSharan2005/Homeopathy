package com.G19.hospital.service.implement.inventory;

import com.G19.hospital.DTO.inventory.PurchaseOrderDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.PurchaseOrder;
import com.G19.hospital.repository.inventory.PurchaseOrderRepository;
import com.G19.hospital.repository.inventory.SupplierRepository;
import com.G19.hospital.service.inventory.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
    @Autowired
    private SupplierRepository supplierRepository; // To set the supplier for the order

    @Override
    public PurchaseOrder createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        try {
            PurchaseOrder order = new PurchaseOrder();
            order.setOrderId(purchaseOrderDto.getOrderId());
            order.setOrderDate(purchaseOrderDto.getOrderDate());
            order.setStatus(purchaseOrderDto.getStatus());
            order.setTotalAmount(purchaseOrderDto.getTotalAmount());
            order.setSupplier(supplierRepository.findById(purchaseOrderDto.getSupplierId())
                    .orElseThrow(() -> new CustomSecurityException("Supplier not found", HttpStatus.NOT_FOUND)));
            // If purchase order items are provided, they will be cascaded automatically.
            return purchaseOrderRepository.save(order);
        } catch (Exception ex) {
            log.error("Error creating purchase order: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to create purchase order", HttpStatus.INTERNAL_SERVER_ERROR);
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
