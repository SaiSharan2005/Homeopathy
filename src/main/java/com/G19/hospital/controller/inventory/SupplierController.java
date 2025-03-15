package com.G19.hospital.controller.inventory;

import com.G19.hospital.DTO.inventory.SupplierDto;
import com.G19.hospital.model.inventory.Supplier;
import com.G19.hospital.service.inventory.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Slf4j
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // 1. Create Supplier
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody SupplierDto supplierDto) {
        Supplier createdSupplier = supplierService.createSupplier(supplierDto);
        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

    // 2. Update Supplier
    @PutMapping("/{supplierId}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long supplierId, @RequestBody SupplierDto supplierDto) {
        Supplier updatedSupplier = supplierService.updateSupplier(supplierId, supplierDto);
        return ResponseEntity.ok(updatedSupplier);
    }

    // 3. Remove Supplier
    @DeleteMapping("/{supplierId}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.noContent().build();
    }

    // 4. Get All Suppliers
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    // 5. Find Supplier by Id
    @GetMapping("/{supplierId}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long supplierId) {
        Supplier supplier = supplierService.getSupplierById(supplierId);
        return ResponseEntity.ok(supplier);
    }
}
