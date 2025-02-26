package com.G19.hospital.service.implement.inventory;

import com.G19.hospital.DTO.inventory.InventoryRecordDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.InventoryRecord;
import com.G19.hospital.model.inventory.InventoryItem;
import com.G19.hospital.model.inventory.Warehouse;
import com.G19.hospital.repository.inventory.InventoryRecordRepository;
import com.G19.hospital.repository.inventory.InventoryItemRepository;
import com.G19.hospital.repository.inventory.WarehouseRepository;
import com.G19.hospital.service.inventory.InventoryRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class InventoryRecordServiceImpl implements InventoryRecordService {

    @Autowired
    private InventoryRecordRepository inventoryRecordRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public InventoryRecord createInventoryRecord(InventoryRecordDto recordDto) {
        try {
            InventoryRecord record = new InventoryRecord();
            record.setQuantity(recordDto.getQuantity());
            InventoryItem item = inventoryItemRepository.findById(recordDto.getInventoryItemId())
                    .orElseThrow(() -> new CustomSecurityException("Inventory item not found", HttpStatus.NOT_FOUND));
            record.setInventoryItem(item);
            Warehouse warehouse = warehouseRepository.findById(recordDto.getWarehouseId())
                    .orElseThrow(() -> new CustomSecurityException("Warehouse not found", HttpStatus.NOT_FOUND));
            record.setWarehouse(warehouse);
            return inventoryRecordRepository.save(record);
        } catch (Exception ex) {
            log.error("Error creating inventory record: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to create inventory record", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public InventoryRecord updateInventoryRecord(Long id, InventoryRecordDto recordDto) {
        InventoryRecord existingRecord = inventoryRecordRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory record not found with id: " + id, HttpStatus.NOT_FOUND));
        try {
            existingRecord.setQuantity(recordDto.getQuantity());
            if (recordDto.getInventoryItemId() != null) {
                InventoryItem item = inventoryItemRepository.findById(recordDto.getInventoryItemId())
                        .orElseThrow(() -> new CustomSecurityException("Inventory item not found", HttpStatus.NOT_FOUND));
                existingRecord.setInventoryItem(item);
            }
            if (recordDto.getWarehouseId() != null) {
                Warehouse warehouse = warehouseRepository.findById(recordDto.getWarehouseId())
                        .orElseThrow(() -> new CustomSecurityException("Warehouse not found", HttpStatus.NOT_FOUND));
                existingRecord.setWarehouse(warehouse);
            }
            return inventoryRecordRepository.save(existingRecord);
        } catch (Exception ex) {
            log.error("Error updating inventory record: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to update inventory record", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteInventoryRecord(Long id) {
        InventoryRecord existingRecord = inventoryRecordRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory record not found with id: " + id, HttpStatus.NOT_FOUND));
        try {
            inventoryRecordRepository.delete(existingRecord);
        } catch (Exception ex) {
            log.error("Error deleting inventory record: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to delete inventory record", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public InventoryRecord getInventoryRecordById(Long id) {
        return inventoryRecordRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory record not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<InventoryRecord> getInventoryRecordsByItemId(Long inventoryItemId) {
        try {
            return inventoryRecordRepository.findByInventoryItemId(inventoryItemId);
        } catch (Exception ex) {
            log.error("Error retrieving inventory records: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to retrieve inventory records", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<InventoryRecord> getAllInventoryRecords() {
        try {
            return inventoryRecordRepository.findAll();
        } catch (Exception ex) {
            log.error("Error retrieving inventory records: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to retrieve inventory records", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
