// src/main/java/com/G19/hospital/config/DataInitializer.java
package com.G19.hospital.config;

import com.G19.hospital.DTO.inventory.CategoryDto;
import com.G19.hospital.DTO.inventory.SupplierDto;
import com.G19.hospital.DTO.inventory.WarehouseDto;
import com.G19.hospital.model.Role;
import com.G19.hospital.DTO.UserRegisterDto;
import com.G19.hospital.repository.*;
import com.G19.hospital.repository.inventory.core.CategoryRepository;
import com.G19.hospital.repository.inventory.core.SupplierRepository;
import com.G19.hospital.repository.inventory.core.WarehouseRepository;
import com.G19.hospital.service.AuthService;
import com.G19.hospital.service.inventory.core.CategoryService;
import com.G19.hospital.service.inventory.core.SupplierService;
import com.G19.hospital.service.inventory.core.WarehouseService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final AuthService authService;

    private final CategoryRepository categoryRepo;
    private final CategoryService categoryService;

    private final SupplierRepository supplierRepo;
    private final SupplierService supplierService;

    private final WarehouseRepository warehouseRepo;
    private final WarehouseService warehouseService;

    public DataInitializer(
            RoleRepository roleRepo,
            UserRepository userRepo,
            AuthService authService,
            CategoryRepository categoryRepo,
            CategoryService categoryService,
            SupplierRepository supplierRepo,
            SupplierService supplierService,
            WarehouseRepository warehouseRepo,
            WarehouseService warehouseService) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.authService = authService;
        this.categoryRepo = categoryRepo;
        this.categoryService = categoryService;
        this.supplierRepo = supplierRepo;
        this.supplierService = supplierService;
        this.warehouseRepo = warehouseRepo;
        this.warehouseService = warehouseService;
    }

    @Override
    @Transactional
    public void run(String... args) {

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    "admin",
                    "Test@123");
        SecurityContextHolder.getContext().setAuthentication(auth);

        seedRoles();
        seedAdminUser();
        seedCategories();
        seedSuppliers();
        seedWarehouses();
    }

    private void seedRoles() {
        List<String> roles = List.of(
                "ACTIVITY", "ADMIN", "ADVERTISEMENT", "APPOINTMENT",
                "DOCTOR", "INVENTORY", "PATIENT", "PAYMENT",
                "STAFF", "TEST", "USERS");
        for (String name : roles) {
            if (!roleRepo.existsByName(name)) {
                Role r = new Role();
                r.setName(name);
                roleRepo.save(r);
            }
        }
    }

    private void seedAdminUser() {
        String email = "admin123@gmail.com";
        if (!userRepo.existsByEmail(email)) {
            UserRegisterDto admin = new UserRegisterDto(
                    "admin",
                    email,
                    "9959584192",
                    "Test@123",
                    new String[] { "ADMIN" });
            authService.register(admin);
        }
    }

    private void seedCategories() {
        CategoryDto analgesics = new CategoryDto(
                null,
                "Analgesics",
                "Medicines used to relieve pain");
        if (!categoryRepo.existsByName(analgesics.getName())) {
            categoryService.createCategory(analgesics);
        }
    }

    private void seedSuppliers() {
        SupplierDto boiron = new SupplierDto(
                null,
                "Boiron Homeopathic Supplies",
                "contact@boironhomeo.com",
                "Tel: +91-11-12345678",
                "123 Homeo Street, New Delhi, India");
        if (!supplierRepo.existsByName(boiron.getName())) {
            supplierService.createSupplier(boiron);
        }
        // add more suppliers here as needed...
    }

    private void seedWarehouses() {
        WarehouseDto hydMain = new WarehouseDto(
                "Hyderabad Main Warehouse",
                "Hyderabad, India");
        if (!warehouseRepo.existsByName(hydMain.getName())) {
            warehouseService.createWarehouse(hydMain);
        }
    }
}
