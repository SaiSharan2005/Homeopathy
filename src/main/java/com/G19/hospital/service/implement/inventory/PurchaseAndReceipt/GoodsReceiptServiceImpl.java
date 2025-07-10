package com.G19.hospital.service.implement.inventory.PurchaseAndReceipt;

import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.CreateGoodsReceiptDTO;
import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.CreateGoodsReceiptItemDTO;
import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.GoodsReceiptDTO;
import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.GoodsReceiptItemDTO;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.GoodsReceipt;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.GoodsReceiptItem;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.inventory.PurchaseAndReceipt.GoodsReceiptItemRepository;
import com.G19.hospital.repository.inventory.PurchaseAndReceipt.GoodsReceiptRepository;
import com.G19.hospital.repository.inventory.PurchaseAndReceipt.PurchaseOrderRepository;
import com.G19.hospital.repository.inventory.core.InventoryItemRepository;
import com.G19.hospital.service.inventory.PurchaseAndReceipt.GoodsReceiptService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsReceiptServiceImpl implements GoodsReceiptService {

    private final GoodsReceiptRepository receiptRepo;
    private final GoodsReceiptItemRepository itemRepo;
    private final PurchaseOrderRepository poRepo;
    private final UserRepository userRepo;
    private final InventoryItemRepository itemRepoCore;

    @Override
    public GoodsReceiptDTO create(CreateGoodsReceiptDTO dto) {
        GoodsReceipt receipt = new GoodsReceipt();
        receipt.setPurchaseOrder(poRepo.findById(dto.getPurchaseOrderId())
                .orElseThrow(() -> new CustomSecurityException("PurchaseOrder not found", HttpStatus.NOT_FOUND)));
        receipt.setReceiptDate(dto.getReceiptDate());
        receipt.setReceivedBy(userRepo.findById(dto.getReceivedById())
                .orElseThrow(() -> new CustomSecurityException("User not found", HttpStatus.NOT_FOUND)));
        receipt.setRemarks(dto.getRemarks());

        List<GoodsReceiptItem> items = dto.getItems().stream().map(i -> {
            GoodsReceiptItem item = new GoodsReceiptItem();
            item.setGoodsReceipt(receipt);
            item.setInventoryItem(itemRepoCore.findById(i.getInventoryItemId())
                    .orElseThrow(() -> new CustomSecurityException("Item not found", HttpStatus.NOT_FOUND)));
            item.setBatchNumber(i.getBatchNumber());
            item.setExpiryDate(i.getExpiryDate());
            item.setQuantityReceived(i.getQuantityReceived());
            return item;
        }).collect(Collectors.toList());

        receipt.getItems().addAll(items);
        receiptRepo.save(receipt);
        return toDTO(receipt);
    }

    @Override
    public GoodsReceiptDTO addItem(Long receiptId, CreateGoodsReceiptItemDTO itemDto) {
        GoodsReceipt receipt = receiptRepo.findById(receiptId)
                .orElseThrow(() -> new CustomSecurityException("Goods Receipt not found", HttpStatus.NOT_FOUND));

        GoodsReceiptItem newItem = new GoodsReceiptItem();
        newItem.setGoodsReceipt(receipt);
        newItem.setInventoryItem(itemRepoCore.findById(itemDto.getInventoryItemId())
                .orElseThrow(() -> new CustomSecurityException("Item not found", HttpStatus.NOT_FOUND)));
        newItem.setBatchNumber(itemDto.getBatchNumber());
        newItem.setExpiryDate(itemDto.getExpiryDate());
        newItem.setQuantityReceived(itemDto.getQuantityReceived());

        itemRepo.save(newItem);
        return toDTO(receipt);
    }

    @Override
    public void removeItem(Long itemId) {
        if (!itemRepo.existsById(itemId))
            throw new CustomSecurityException("Goods Receipt Item not found", HttpStatus.NOT_FOUND);
        itemRepo.deleteById(itemId);
    }

    @Override
    public GoodsReceiptDTO findById(Long id) {
        return receiptRepo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new CustomSecurityException("Goods Receipt not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<GoodsReceiptDTO> findAll(Pageable pageable) {
        return receiptRepo.findAll(pageable).map(this::toDTO);
    }

    @Override
    public GoodsReceiptDTO update(Long id, CreateGoodsReceiptDTO dto) {
        GoodsReceipt receipt = receiptRepo.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Receipt not found", HttpStatus.NOT_FOUND));
        receipt.setReceiptDate(dto.getReceiptDate());
        receipt.setRemarks(dto.getRemarks());

        receipt.getItems().clear();
        receiptRepo.flush();

        List<GoodsReceiptItem> items = dto.getItems().stream().map(i -> {
            GoodsReceiptItem item = new GoodsReceiptItem();
            item.setGoodsReceipt(receipt);
            item.setInventoryItem(itemRepoCore.findById(i.getInventoryItemId())
                    .orElseThrow(() -> new CustomSecurityException("Item not found", HttpStatus.NOT_FOUND)));
            item.setBatchNumber(i.getBatchNumber());
            item.setExpiryDate(i.getExpiryDate());
            item.setQuantityReceived(i.getQuantityReceived());
            return item;
        }).collect(Collectors.toList());

        receipt.getItems().addAll(items);
        receiptRepo.save(receipt);
        return toDTO(receipt);
    }

    @Override
    public void delete(Long id) {
        if (!receiptRepo.existsById(id))
            throw new CustomSecurityException("Receipt not found", HttpStatus.NOT_FOUND);
        receiptRepo.deleteById(id);
    }

    @Override
    public List<GoodsReceiptDTO> findByPurchaseOrderId(Long purchaseOrderId) {
        return receiptRepo.findByPurchaseOrder_Id(purchaseOrderId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private GoodsReceiptDTO toDTO(GoodsReceipt r) {
        List<GoodsReceiptItemDTO> items = r.getItems().stream().map(i -> new GoodsReceiptItemDTO(
                i.getId(),
                i.getInventoryItem().getId(),
                i.getBatchNumber(),
                i.getExpiryDate(),
                i.getQuantityReceived()
        )).collect(Collectors.toList());

        return new GoodsReceiptDTO(
                r.getId(),
                r.getPurchaseOrder().getId(),
                r.getReceiptDate(),
                r.getReceivedBy().getId(),
                r.getRemarks(),
                items
        );
    }
}
