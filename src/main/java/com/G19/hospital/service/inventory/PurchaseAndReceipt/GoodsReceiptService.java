package com.G19.hospital.service.inventory.PurchaseAndReceipt;

import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.CreateGoodsReceiptDTO;
import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.GoodsReceiptDTO;
import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.CreateGoodsReceiptItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoodsReceiptService {
    GoodsReceiptDTO create(CreateGoodsReceiptDTO dto);
    GoodsReceiptDTO addItem(Long receiptId, CreateGoodsReceiptItemDTO itemDto);
    void removeItem(Long itemId);
    GoodsReceiptDTO findById(Long id);
    Page<GoodsReceiptDTO> findAll(Pageable pageable);
    GoodsReceiptDTO update(Long id, CreateGoodsReceiptDTO dto);
    void delete(Long id);
    List<GoodsReceiptDTO> findByPurchaseOrderId(Long purchaseOrderId);
}
