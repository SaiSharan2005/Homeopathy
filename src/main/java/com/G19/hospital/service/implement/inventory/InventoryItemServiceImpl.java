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
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class InventoryItemServiceImpl implements InventoryItemService {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile imageFile) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    @Override
    public InventoryItem createInventoryItem(InventoryItemDto inventoryItemDto, MultipartFile image) throws IOException {
        try {
            InventoryItem item = new InventoryItem();
            item.setName(inventoryItemDto.getName());
            item.setCommonName(inventoryItemDto.getCommonName());
            item.setSource(inventoryItemDto.getSource());
            item.setPotency(inventoryItemDto.getPotency());
            item.setFormulation(inventoryItemDto.getFormulation());
            item.setDescription(inventoryItemDto.getDescription());
            item.setManufacturer(inventoryItemDto.getManufacturer());
            item.setUnit(inventoryItemDto.getUnit());
            item.setReorderLevel(inventoryItemDto.getReorderLevel());
            item.setExpiryDate(inventoryItemDto.getExpiryDate());
            item.setStorageConditions(inventoryItemDto.getStorageConditions());
            item.setIndications(inventoryItemDto.getIndications());
            item.setContraindications(inventoryItemDto.getContraindications());
            item.setSideEffects(inventoryItemDto.getSideEffects());
            item.setUsageInstructions(inventoryItemDto.getUsageInstructions());
            item.setRegulatoryStatus(inventoryItemDto.getRegulatoryStatus());
            item.setCostPrice(inventoryItemDto.getCostPrice());
            item.setSellingPrice(inventoryItemDto.getSellingPrice());

            // Upload image if provided
            if (image != null && !image.isEmpty()) {
                String imageUrl = uploadImage(image);
                item.setImageUrl(imageUrl);
            }

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
    public InventoryItem updateInventoryItem(Long id, InventoryItemDto inventoryItemDto, MultipartFile image) throws IOException {
        InventoryItem existingItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory item not found with id: " + id, HttpStatus.NOT_FOUND));
        try {
            existingItem.setName(inventoryItemDto.getName());
            existingItem.setCommonName(inventoryItemDto.getCommonName());
            existingItem.setSource(inventoryItemDto.getSource());
            existingItem.setPotency(inventoryItemDto.getPotency());
            existingItem.setFormulation(inventoryItemDto.getFormulation());
            existingItem.setDescription(inventoryItemDto.getDescription());
            existingItem.setManufacturer(inventoryItemDto.getManufacturer());
            existingItem.setUnit(inventoryItemDto.getUnit());
            existingItem.setReorderLevel(inventoryItemDto.getReorderLevel());
            existingItem.setExpiryDate(inventoryItemDto.getExpiryDate());
            existingItem.setStorageConditions(inventoryItemDto.getStorageConditions());
            existingItem.setIndications(inventoryItemDto.getIndications());
            existingItem.setContraindications(inventoryItemDto.getContraindications());
            existingItem.setSideEffects(inventoryItemDto.getSideEffects());
            existingItem.setUsageInstructions(inventoryItemDto.getUsageInstructions());
            existingItem.setRegulatoryStatus(inventoryItemDto.getRegulatoryStatus());
            existingItem.setCostPrice(inventoryItemDto.getCostPrice());
            existingItem.setSellingPrice(inventoryItemDto.getSellingPrice());

            // Upload new image if provided
            if (image != null && !image.isEmpty()) {
                String imageUrl = uploadImage(image);
                existingItem.setImageUrl(imageUrl);
            }

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
            item.updateStock(change);
            return inventoryItemRepository.save(item);
        } catch (Exception ex) {
            log.error("Error updating stock for inventory item: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to update stock", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
