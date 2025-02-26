package com.G19.hospital.service.implement.inventory;

import com.G19.hospital.DTO.inventory.InventoryTransactionDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.InventoryTransaction;
import com.G19.hospital.model.inventory.InventoryItem;
import com.G19.hospital.repository.inventory.InventoryTransactionRepository;
import com.G19.hospital.repository.inventory.InventoryItemRepository;
import com.G19.hospital.service.inventory.InventoryTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class InventoryTransactionServiceImpl implements InventoryTransactionService {

    @Autowired
    private InventoryTransactionRepository transactionRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Override
    public InventoryTransaction createTransaction(InventoryTransactionDto transactionDto) {
        try {
            InventoryTransaction transaction = new InventoryTransaction();
            transaction.setTransactionDate(transactionDto.getTransactionDate());
            transaction.setQuantityChange(transactionDto.getQuantityChange());
            transaction.setTransactionType(transactionDto.getTransactionType());
            transaction.setReference(transactionDto.getReference());
            transaction.setComments(transactionDto.getComments());
            InventoryItem item = inventoryItemRepository.findById(transactionDto.getInventoryItemId())
                    .orElseThrow(() -> new CustomSecurityException("Inventory item not found", HttpStatus.NOT_FOUND));
            transaction.setInventoryItem(item);
            return transactionRepository.save(transaction);
        } catch (Exception ex) {
            log.error("Error creating inventory transaction: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to create inventory transaction", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public InventoryTransaction updateTransaction(Long id, InventoryTransactionDto transactionDto) {
        InventoryTransaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory transaction not found with id: " + id, HttpStatus.NOT_FOUND));
        try {
            existingTransaction.setTransactionDate(transactionDto.getTransactionDate());
            existingTransaction.setQuantityChange(transactionDto.getQuantityChange());
            existingTransaction.setTransactionType(transactionDto.getTransactionType());
            existingTransaction.setReference(transactionDto.getReference());
            existingTransaction.setComments(transactionDto.getComments());
            if (transactionDto.getInventoryItemId() != null) {
                InventoryItem item = inventoryItemRepository.findById(transactionDto.getInventoryItemId())
                        .orElseThrow(() -> new CustomSecurityException("Inventory item not found", HttpStatus.NOT_FOUND));
                existingTransaction.setInventoryItem(item);
            }
            return transactionRepository.save(existingTransaction);
        } catch (Exception ex) {
            log.error("Error updating inventory transaction: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to update inventory transaction", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteTransaction(Long id) {
        InventoryTransaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory transaction not found with id: " + id, HttpStatus.NOT_FOUND));
        try {
            transactionRepository.delete(existingTransaction);
        } catch (Exception ex) {
            log.error("Error deleting inventory transaction: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to delete inventory transaction", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public InventoryTransaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory transaction not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<InventoryTransaction> getTransactionsByInventoryItemId(Long inventoryItemId) {
        try {
            return transactionRepository.findByInventoryItemId(inventoryItemId);
        } catch (Exception ex) {
            log.error("Error retrieving transactions: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to retrieve transactions", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<InventoryTransaction> getAllTransactions() {
        try {
            return transactionRepository.findAll();
        } catch (Exception ex) {
            log.error("Error retrieving transactions: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to retrieve transactions", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
