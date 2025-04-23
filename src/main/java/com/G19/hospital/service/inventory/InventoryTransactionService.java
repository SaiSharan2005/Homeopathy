package com.G19.hospital.service.inventory;

import com.G19.hospital.DTO.inventory.InventoryTransactionDto;
import com.G19.hospital.model.inventory.InventoryTransaction;
import java.util.List;

public interface InventoryTransactionService {
    InventoryTransaction createTransaction(InventoryTransactionDto transactionDto);
    InventoryTransaction updateTransaction(Long id, InventoryTransactionDto transactionDto);
    void deleteTransaction(Long id);
    InventoryTransaction getTransactionById(Long id);
    List<InventoryTransaction> getTransactionsByInventoryItemId(Long inventoryItemId);
    List<InventoryTransaction> getAllTransactions();
}
