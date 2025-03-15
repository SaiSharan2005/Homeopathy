package com.G19.hospital.service.implement.inventory;

import com.G19.hospital.DTO.inventory.WarehouseDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.Warehouse;
import com.G19.hospital.repository.inventory.WarehouseRepository;
import com.G19.hospital.service.inventory.WarehouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public Warehouse createWarehouse(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(warehouseDto.getName());
        warehouse.setLocation(warehouseDto.getLocation());
        return warehouseRepository.save(warehouse);
    }

    @Override
    public Warehouse updateWarehouse(Long warehouseId, WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new CustomSecurityException("Warehouse not found with id: " + warehouseId, HttpStatus.NOT_FOUND));

        warehouse.setName(warehouseDto.getName());
        warehouse.setLocation(warehouseDto.getLocation());

        return warehouseRepository.save(warehouse);
    }

    @Override
    public void deleteWarehouse(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new CustomSecurityException("Warehouse not found with id: " + warehouseId, HttpStatus.NOT_FOUND));

        warehouseRepository.delete(warehouse);
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @Override
    public Warehouse getWarehouseById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new CustomSecurityException("Warehouse not found with id: " + warehouseId, HttpStatus.NOT_FOUND));
    }
}
