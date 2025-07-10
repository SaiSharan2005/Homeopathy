package com.G19.hospital.service.inventory.core;

import com.G19.hospital.DTO.inventory.SupplierDto;
import com.G19.hospital.model.inventory.core.Supplier;

import java.util.List;

public interface SupplierService {
    Supplier createSupplier(SupplierDto supplierDto);
    Supplier updateSupplier(Long supplierId, SupplierDto supplierDto);
    void deleteSupplier(Long supplierId);
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(Long supplierId);
}
