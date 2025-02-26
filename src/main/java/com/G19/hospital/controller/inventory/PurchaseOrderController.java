package com.G19.hospital.controller.inventory;

import com.G19.hospital.DTO.inventory.PurchaseOrderDto;
import com.G19.hospital.model.inventory.PurchaseOrder;
import com.G19.hospital.service.inventory.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@Slf4j
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder order = purchaseOrderService.createPurchaseOrder(purchaseOrderDto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrder(@PathVariable Long orderId, @RequestBody PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder order = purchaseOrderService.updatePurchaseOrder(orderId, purchaseOrderDto);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long orderId) {
        purchaseOrderService.deletePurchaseOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable Long orderId) {
        PurchaseOrder order = purchaseOrderService.getPurchaseOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        List<PurchaseOrder> orders = purchaseOrderService.getAllPurchaseOrders();
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{orderId}/recalculate")
    public ResponseEntity<PurchaseOrder> recalculateTotal(@PathVariable Long orderId) {
        PurchaseOrder order = purchaseOrderService.recalculateTotal(orderId);
        return ResponseEntity.ok(order);
    }
}
