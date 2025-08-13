
// src/main/java/com/G19/hospital/controller/inventory/PurchaseOrderController.java
package com.G19.hospital.controller.inventory.PurchaseAndReceipt;

import com.G19.hospital.DTO.inventory.PurchaseOrderDto;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrder;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrderStatus;
import com.G19.hospital.service.inventory.PurchaseAndReceipt.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    @Autowired private PurchaseOrderService service;

    @PostMapping
    public ResponseEntity<PurchaseOrder> create(@Valid @RequestBody PurchaseOrderDto dto) {
        return new ResponseEntity<>(service.createPurchaseOrder(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPurchaseOrderFullDetail(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrder> update(
        @PathVariable Long id, @Valid @RequestBody PurchaseOrderDto dto) 
    {
        return ResponseEntity.ok(service.updatePurchaseOrder(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletePurchaseOrder(id);
        return ResponseEntity.noContent().build();
    }

    // List all POs pageable
    @GetMapping
    public ResponseEntity<Page<PurchaseOrder>> list(
      @RequestParam(defaultValue="0") int page,
      @RequestParam(defaultValue="20") int size) 
    {
        return ResponseEntity.ok(service.getPurchaseOrders(PageRequest.of(page, size)));
    }

    // By supplier
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<Page<PurchaseOrder>> bySupplier(
      @PathVariable Long supplierId,
      @RequestParam(defaultValue="0") int page,
      @RequestParam(defaultValue="20") int size) 
    {
        return ResponseEntity.ok(
            service.getPurchaseOrdersBySupplier(supplierId, PageRequest.of(page, size)));
    }

    // By status
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<PurchaseOrder>> byStatus(
      @PathVariable PurchaseOrderStatus status,
      @RequestParam(defaultValue="0") int page,
      @RequestParam(defaultValue="20") int size) 
    {
        return ResponseEntity.ok(
            service.getPurchaseOrdersByStatus(status, PageRequest.of(page, size)));
    }

    // Change status
    @PatchMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> changeStatus(
      @PathVariable Long id,
      @RequestParam PurchaseOrderStatus status) 
    {
        return ResponseEntity.ok(service.changePurchaseOrderStatus(id, status));
    }

    // Remove an item
    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<Void> removeItem(
      @PathVariable Long orderId,
      @PathVariable Long itemId) 
    {
        service.removePurchaseOrderItem(orderId, itemId);
        return ResponseEntity.noContent().build();
    }
}
