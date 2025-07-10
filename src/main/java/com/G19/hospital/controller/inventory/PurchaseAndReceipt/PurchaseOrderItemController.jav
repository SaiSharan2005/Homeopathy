package com.G19.hospital.controller.inventory;

import com.G19.hospital.DTO.inventory.PurchaseOrderItemDto;
import com.G19.hospital.model.inventory.PurchaseOrderItem;
import com.G19.hospital.service.inventory.PurchaseOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-order-items")
@Slf4j
public class PurchaseOrderItemController {

    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;

    @PostMapping
    public ResponseEntity<PurchaseOrderItem> createPurchaseOrderItem(@RequestBody PurchaseOrderItemDto itemDto) {
        PurchaseOrderItem item = purchaseOrderItemService.createPurchaseOrderItem(itemDto);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<PurchaseOrderItem> updatePurchaseOrderItem(@PathVariable Long orderItemId, @RequestBody PurchaseOrderItemDto itemDto) {
        PurchaseOrderItem item = purchaseOrderItemService.updatePurchaseOrderItem(orderItemId, itemDto);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> deletePurchaseOrderItem(@PathVariable Long orderItemId) {
        purchaseOrderItemService.deletePurchaseOrderItem(orderItemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<PurchaseOrderItem> getPurchaseOrderItemById(@PathVariable Long orderItemId) {
        PurchaseOrderItem item = purchaseOrderItemService.getPurchaseOrderItemById(orderItemId);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PurchaseOrderItem>> getPurchaseOrderItemsByOrderId(@PathVariable Long orderId) {
        List<PurchaseOrderItem> items = purchaseOrderItemService.getPurchaseOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(items);
    }
}
