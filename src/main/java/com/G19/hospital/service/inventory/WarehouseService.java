package com.G19.hospital.service.inventory;

import com.G19.hospital.DTO.inventory.WarehouseDto;
import com.G19.hospital.model.inventory.Warehouse;

import java.util.List;

public interface WarehouseService {
    Warehouse createWarehouse(WarehouseDto warehouseDto);
    Warehouse updateWarehouse(Long warehouseId, WarehouseDto warehouseDto);
    void deleteWarehouse(Long warehouseId);
    List<Warehouse> getAllWarehouses();
    Warehouse getWarehouseById(Long warehouseId);
}
