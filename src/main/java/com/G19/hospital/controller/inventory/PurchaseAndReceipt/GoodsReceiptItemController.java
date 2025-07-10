package com.G19.hospital.controller.inventory.PurchaseAndReceipt;

import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.CreateGoodsReceiptItemDTO;
import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.GoodsReceiptItemDTO;
import com.G19.hospital.service.inventory.PurchaseAndReceipt.GoodsReceiptItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods-receipts/items")
@RequiredArgsConstructor
public class GoodsReceiptItemController {

    private final GoodsReceiptItemService itemService;

    /** 1. Add an item to a receipt */
    @PostMapping("/receipt/{receiptId}")
    public ResponseEntity<GoodsReceiptItemDTO> addItem(
            @PathVariable Long receiptId,
            @Valid @RequestBody CreateGoodsReceiptItemDTO dto) 
    {
        GoodsReceiptItemDTO created = itemService.addItemToReceipt(receiptId, dto);
        return ResponseEntity.ok(created);
    }

    /** 2. Remove an item by its ID */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        itemService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }

    /** 3. Get a single item’s details */
    @GetMapping("/{itemId}")
    public ResponseEntity<GoodsReceiptItemDTO> getItem(@PathVariable Long itemId) {
        GoodsReceiptItemDTO dto = itemService.getItemById(itemId);
        return ResponseEntity.ok(dto);
    }

    /** 4. List all items for a specific receipt */
    @GetMapping("/receipt/{receiptId}")
    public ResponseEntity<List<GoodsReceiptItemDTO>> listByReceipt(@PathVariable Long receiptId) {
        List<GoodsReceiptItemDTO> items = itemService.getAllItemsByReceiptId(receiptId);
        return ResponseEntity.ok(items);
    }
}
