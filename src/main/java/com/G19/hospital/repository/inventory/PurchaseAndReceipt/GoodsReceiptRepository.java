package com.G19.hospital.repository.inventory.PurchaseAndReceipt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.PurchaseAndReceipt.GoodsReceipt;

import java.util.List;

public interface GoodsReceiptRepository extends JpaRepository<GoodsReceipt, Long> {
    Page<GoodsReceipt> findAll(Pageable pageable);
    List<GoodsReceipt> findByPurchaseOrder_Id(Long purchaseOrderId);
}
