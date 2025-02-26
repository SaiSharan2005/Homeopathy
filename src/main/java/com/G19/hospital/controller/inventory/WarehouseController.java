package com.G19.hospital.controller.inventory;

import com.G19.hospital.DTO.inventory.WarehouseDto;
import com.G19.hospital.model.inventory.Warehouse;
import com.G19.hospital.service.inventory.WarehouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseService.createWarehouse(warehouseDto);
        return ResponseEntity.ok(warehouse);
    }

    @PutMapping("/{warehouseId}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long warehouseId, @RequestBody WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseService.updateWarehouse(warehouseId, warehouseDto);
        return ResponseEntity.ok(warehouse);
    }

    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
        return ResponseEntity.ok("Warehouse deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseService.getAllWarehouses();
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long warehouseId) {
        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        return ResponseEntity.ok(warehouse);
    }
}
