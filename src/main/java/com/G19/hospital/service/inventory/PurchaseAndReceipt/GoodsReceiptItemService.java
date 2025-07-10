package com.G19.hospital.service.inventory.PurchaseAndReceipt;

import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.CreateGoodsReceiptItemDTO;
import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.GoodsReceiptItemDTO;

import java.util.List;

public interface GoodsReceiptItemService {
    GoodsReceiptItemDTO addItemToReceipt(Long receiptId, CreateGoodsReceiptItemDTO dto);
    void removeItem(Long itemId);
    GoodsReceiptItemDTO getItemById(Long itemId);
    List<GoodsReceiptItemDTO> getAllItemsByReceiptId(Long receiptId);
}
