package com.G19.hospital.repository.inventory.PurchaseAndReceipt;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.PurchaseAndReceipt.GoodsReceiptItem;

public interface GoodsReceiptItemRepository extends JpaRepository<GoodsReceiptItem, Long> {
}
