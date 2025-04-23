package com.G19.hospital.service.implement.inventory;

import com.G19.hospital.DTO.inventory.SupplierDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.Supplier;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.inventory.SupplierRepository;
import com.G19.hospital.service.inventory.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Supplier createSupplier(SupplierDto supplierDto) {
        try {
            Supplier supplier = new Supplier();
            // Set the business identifier if needed; otherwise, omit if generated
            // automatically.
            supplier.setName(supplierDto.getName());
            supplier.setContactDetails(supplierDto.getContactDetails());
            supplier.setAddress(supplierDto.getAddress());
            supplier.setEmail(supplierDto.getEmail());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Assuming username is the unique identifier

            // Fetch the doctor (User object) by username
            User doctorData = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            supplier.setCreatedBy(doctorData);
            supplier.setCreatedAt(LocalDateTime.now());

            return supplierRepository.save(supplier);
        } catch (Exception ex) {
            log.error("Error creating supplier: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to create supplier", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Supplier updateSupplier(Long supplierId, SupplierDto supplierDto) {
        Supplier existingSupplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new CustomSecurityException("Supplier not found with id: " + supplierId,
                        HttpStatus.NOT_FOUND));
        try {
            existingSupplier.setName(supplierDto.getName());
            existingSupplier.setContactDetails(supplierDto.getContactDetails());
            existingSupplier.setAddress(supplierDto.getAddress());
            existingSupplier.setEmail(supplierDto.getEmail());
            existingSupplier.setUpdatedAt(LocalDateTime.now());

            return supplierRepository.save(existingSupplier);
        } catch (Exception ex) {
            log.error("Error updating supplier: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to update supplier", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteSupplier(Long supplierId) {
        Supplier existingSupplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new CustomSecurityException("Supplier not found with id: " + supplierId,
                        HttpStatus.NOT_FOUND));
        try {
            supplierRepository.delete(existingSupplier);
        } catch (Exception ex) {
            log.error("Error deleting supplier: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to delete supplier", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        try {
            return supplierRepository.findAll();
        } catch (Exception ex) {
            log.error("Error fetching suppliers: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to fetch suppliers", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Supplier getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new CustomSecurityException("Supplier not found with id: " + supplierId,
                        HttpStatus.NOT_FOUND));
    }
}
