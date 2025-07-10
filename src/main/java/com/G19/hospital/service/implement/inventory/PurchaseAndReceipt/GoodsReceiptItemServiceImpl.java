package com.G19.hospital.service.implement.inventory.PurchaseAndReceipt;

import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.CreateGoodsReceiptItemDTO;
import com.G19.hospital.DTO.inventory.PurchaseAndReceipt.GoodsReceiptItemDTO;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.GoodsReceipt;
import com.G19.hospital.model.inventory.PurchaseAndReceipt.GoodsReceiptItem;
import com.G19.hospital.repository.inventory.PurchaseAndReceipt.GoodsReceiptItemRepository;
import com.G19.hospital.repository.inventory.PurchaseAndReceipt.GoodsReceiptRepository;
import com.G19.hospital.repository.inventory.core.InventoryItemRepository;
import com.G19.hospital.service.inventory.PurchaseAndReceipt.GoodsReceiptItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsReceiptItemServiceImpl implements GoodsReceiptItemService {

    private final GoodsReceiptItemRepository itemRepo;
    private final GoodsReceiptRepository receiptRepo;
    private final InventoryItemRepository inventoryItemRepo;

    @Override
    public GoodsReceiptItemDTO addItemToReceipt(Long receiptId, CreateGoodsReceiptItemDTO dto) {
        GoodsReceipt receipt = receiptRepo.findById(receiptId)
                .orElseThrow(() -> new CustomSecurityException("Goods Receipt not found", HttpStatus.NOT_FOUND));

        GoodsReceiptItem item = new GoodsReceiptItem();
        item.setGoodsReceipt(receipt);
        item.setInventoryItem(inventoryItemRepo.findById(dto.getInventoryItemId())
                .orElseThrow(() -> new CustomSecurityException("Inventory item not found", HttpStatus.NOT_FOUND)));
        item.setBatchNumber(dto.getBatchNumber());
        item.setExpiryDate(dto.getExpiryDate());
        item.setQuantityReceived(dto.getQuantityReceived());

        item = itemRepo.save(item);
        return toDTO(item);
    }

    @Override
    public void removeItem(Long itemId) {
        if (!itemRepo.existsById(itemId)) {
            throw new CustomSecurityException("Goods Receipt Item not found", HttpStatus.NOT_FOUND);
        }
        itemRepo.deleteById(itemId);
    }

    @Override
    public GoodsReceiptItemDTO getItemById(Long itemId) {
        GoodsReceiptItem item = itemRepo.findById(itemId)
                .orElseThrow(() -> new CustomSecurityException("Goods Receipt Item not found", HttpStatus.NOT_FOUND));
        return toDTO(item);
    }

    @Override
    public List<GoodsReceiptItemDTO> getAllItemsByReceiptId(Long receiptId) {
        GoodsReceipt receipt = receiptRepo.findById(receiptId)
                .orElseThrow(() -> new EntityNotFoundException("Receipt not found"));

        return receipt.getItems().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private GoodsReceiptItemDTO toDTO(GoodsReceiptItem item) {
        return new GoodsReceiptItemDTO(
                item.getId(),
                item.getInventoryItem().getId(),
                item.getBatchNumber(),
                item.getExpiryDate(),
                item.getQuantityReceived()
        );
    }
}
