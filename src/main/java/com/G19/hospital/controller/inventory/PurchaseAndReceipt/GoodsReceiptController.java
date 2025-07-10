package com.G19.hospital.controller.inventory.PurchaseAndReceipt;

import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.CreateGoodsReceiptDTO;
import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.GoodsReceiptDTO;
import com.G19.hospital.service.inventory.PurchaseAndReceipt.GoodsReceiptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods-receipts")
@RequiredArgsConstructor
public class GoodsReceiptController {

    private final GoodsReceiptService goodsReceiptService;

    // 1. Create a new Goods Receipt
    @PostMapping
    public ResponseEntity<GoodsReceiptDTO> createReceipt(@Valid @RequestBody CreateGoodsReceiptDTO dto) {
        GoodsReceiptDTO created = goodsReceiptService.create(dto);
        return ResponseEntity.ok(created);
    }

    // 2. Get a Receipt by ID
    @GetMapping("/{id}")
    public ResponseEntity<GoodsReceiptDTO> getReceiptById(@PathVariable Long id) {
        GoodsReceiptDTO receipt = goodsReceiptService.findById(id);
        return ResponseEntity.ok(receipt);
    }

    // 3. Get All Receipts with Pageable
    @GetMapping
    public ResponseEntity<Page<GoodsReceiptDTO>> getAllReceipts(Pageable pageable) {
        Page<GoodsReceiptDTO> result = goodsReceiptService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    // 4. Update a Receipt and its Items
    @PutMapping("/{id}")
    public ResponseEntity<GoodsReceiptDTO> updateReceipt(@PathVariable Long id, @Valid @RequestBody CreateGoodsReceiptDTO dto) {
        GoodsReceiptDTO updated = goodsReceiptService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // 5. Delete a Receipt
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceipt(@PathVariable Long id) {
        goodsReceiptService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Get Receipt by Purchase Order ID
    @GetMapping("/by-purchase-order/{purchaseOrderId}")
    public ResponseEntity<List<GoodsReceiptDTO>> getByPurchaseOrderId(@PathVariable Long purchaseOrderId) {
        List<GoodsReceiptDTO> receipts = goodsReceiptService.findByPurchaseOrderId(purchaseOrderId);
        return ResponseEntity.ok(receipts);
    }
}
