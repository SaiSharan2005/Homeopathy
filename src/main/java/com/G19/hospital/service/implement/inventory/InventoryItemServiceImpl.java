package com.G19.hospital.service.implement.inventory;

import com.G19.hospital.DTO.inventory.InventoryItemDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.InventoryItem;
import com.G19.hospital.model.inventory.Category;
import com.G19.hospital.repository.inventory.InventoryItemRepository;
import com.G19.hospital.repository.inventory.CategoryRepository;
import com.G19.hospital.service.inventory.InventoryItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class InventoryItemServiceImpl implements InventoryItemService {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public InventoryItem createInventoryItem(InventoryItemDto inventoryItemDto) {
        try {
            InventoryItem item = new InventoryItem();
            item.setName(inventoryItemDto.getName());
            item.setDescription(inventoryItemDto.getDescription());
            item.setManufacturer(inventoryItemDto.getManufacturer());
            item.setUnit(inventoryItemDto.getUnit());
            item.setReorderLevel(inventoryItemDto.getReorderLevel());
            item.setExpiryDate(inventoryItemDto.getExpiryDate());
            if (inventoryItemDto.getCategoryId() != null) {
                Category category = categoryRepository.findById(inventoryItemDto.getCategoryId())
                        .orElseThrow(() -> new CustomSecurityException("Category not found", HttpStatus.NOT_FOUND));
                item.setCategory(category);
            }
            return inventoryItemRepository.save(item);
        } catch (Exception ex) {
            log.error("Error creating inventory item: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to create inventory item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public InventoryItem updateInventoryItem(Long id, InventoryItemDto inventoryItemDto) {
        InventoryItem existingItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory item not found with id: " + id, HttpStatus.NOT_FOUND));
        try {
            existingItem.setName(inventoryItemDto.getName());
            existingItem.setDescription(inventoryItemDto.getDescription());
            existingItem.setManufacturer(inventoryItemDto.getManufacturer());
            existingItem.setUnit(inventoryItemDto.getUnit());
            existingItem.setReorderLevel(inventoryItemDto.getReorderLevel());
            existingItem.setExpiryDate(inventoryItemDto.getExpiryDate());
            if (inventoryItemDto.getCategoryId() != null) {
                Category category = categoryRepository.findById(inventoryItemDto.getCategoryId())
                        .orElseThrow(() -> new CustomSecurityException("Category not found", HttpStatus.NOT_FOUND));
                existingItem.setCategory(category);
            }
            return inventoryItemRepository.save(existingItem);
        } catch (Exception ex) {
            log.error("Error updating inventory item: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to update inventory item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteInventoryItem(Long id) {
        InventoryItem existingItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory item not found with id: " + id, HttpStatus.NOT_FOUND));
        try {
            inventoryItemRepository.delete(existingItem);
        } catch (Exception ex) {
            log.error("Error deleting inventory item: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to delete inventory item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public InventoryItem getInventoryItemById(Long id) {
        return inventoryItemRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory item not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<InventoryItem> getAllInventoryItems() {
        try {
            return inventoryItemRepository.findAll();
        } catch (Exception ex) {
            log.error("Error retrieving inventory items: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to retrieve inventory items", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public InventoryItem updateStock(Long id, int change) {
        InventoryItem item = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory item not found with id: " + id, HttpStatus.NOT_FOUND));
        try {
            // Call the business method to update the stock (delta change)
            item.updateStock(change);
            return inventoryItemRepository.save(item);
        } catch (Exception ex) {
            log.error("Error updating stock for inventory item: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to update stock", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
