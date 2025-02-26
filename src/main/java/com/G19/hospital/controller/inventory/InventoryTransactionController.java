package com.G19.hospital.controller.inventory;

import com.G19.hospital.DTO.inventory.InventoryTransactionDto;
import com.G19.hospital.model.inventory.InventoryTransaction;
import com.G19.hospital.service.inventory.InventoryTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-transactions")
@Slf4j
public class InventoryTransactionController {

    @Autowired
    private InventoryTransactionService inventoryTransactionService;

    @PostMapping
    public ResponseEntity<InventoryTransaction> createTransaction(@RequestBody InventoryTransactionDto transactionDto) {
        InventoryTransaction transaction = inventoryTransactionService.createTransaction(transactionDto);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryTransaction> updateTransaction(@PathVariable Long id, @RequestBody InventoryTransactionDto transactionDto) {
        InventoryTransaction transaction = inventoryTransactionService.updateTransaction(id, transactionDto);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        inventoryTransactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryTransaction> getTransactionById(@PathVariable Long id) {
        InventoryTransaction transaction = inventoryTransactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/item/{inventoryItemId}")
    public ResponseEntity<List<InventoryTransaction>> getTransactionsByInventoryItemId(@PathVariable Long inventoryItemId) {
        List<InventoryTransaction> transactions = inventoryTransactionService.getTransactionsByInventoryItemId(inventoryItemId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping
    public ResponseEntity<List<InventoryTransaction>> getAllTransactions() {
        List<InventoryTransaction> transactions = inventoryTransactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
