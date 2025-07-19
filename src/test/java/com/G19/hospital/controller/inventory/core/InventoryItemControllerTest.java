package com.G19.hospital.controller.inventory.core;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.core.Category;
import com.G19.hospital.model.inventory.core.InventoryItem;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.inventory.core.CategoryRepository;
import com.G19.hospital.repository.inventory.core.InventoryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = com.G19.hospital.HospitalmanagementApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")

class InventoryItemControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    private User adminUser;
    private Role adminRole;
    private Category testCategory;
    private InventoryItem testItem;

    @BeforeEach
    void setUp() {
        cleanupTestData();

        // Create admin role
        adminRole = createTestRole("ADMIN");
        adminRole = roleRepository.save(adminRole);

        // Create admin user
        adminUser = createTestUser("admin", "admin@hospital.com", "1234567890");
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        adminUser.setRoles(roles);
        adminUser = userRepository.save(adminUser);

        // Create test category
        testCategory = createTestCategory("Homeopathic Remedies");
        testCategory = categoryRepository.save(testCategory);

        // Create test inventory item
        testItem = createTestInventoryItem(testCategory, adminUser);
        testItem = inventoryItemRepository.save(testItem);
    }

    @Test
    void testCreateInventoryItem_Success() throws Exception {
        String itemRequest = """
            {
                "name": "Belladonna",
                "commonName": "Deadly Nightshade",
                "source": "Plant",
                "potency": "30C",
                "formulation": "Liquid",
                "description": "Used for treating fever and inflammation",
                "manufacturer": "Boiron",
                "unit": "bottle",
                "reorderLevel": 30,
                "storageConditions": "Store in cool, dry place",
                "indications": "Fever, inflammation",
                "contraindications": "None known",
                "sideEffects": "Rarely any side effects",
                "usageInstructions": "Take 3 drops under tongue 3 times daily",
                "regulatoryStatus": "Approved",
                "sellingPrice": 12.50,
                "categoryId": %d
            }
            """.formatted(testCategory.getId());

        mockMvc.perform(multipart("/api/inventory-items")
                .file(new MockMultipartFile("inventoryItem", "inventoryItem.json", 
                    "application/json", itemRequest.getBytes())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Belladonna"));
    }

    @Test
    void testCreateInventoryItem_InvalidCategory() throws Exception {
        String itemRequest = """
            {
                "name": "Test Item",
                "commonName": "Test Common Name",
                "source": "Plant",
                "potency": "30C",
                "formulation": "Liquid",
                "description": "Test description",
                "manufacturer": "Test Manufacturer",
                "unit": "bottle",
                "reorderLevel": 30,
                "sellingPrice": 12.50,
                "categoryId": 999
            }
            """;

        mockMvc.perform(multipart("/api/inventory-items")
                .file(new MockMultipartFile("inventoryItem", "inventoryItem.json", 
                    "application/json", itemRequest.getBytes())))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetAllInventoryItems_Success() throws Exception {
        mockMvc.perform(get("/api/inventory-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetInventoryItemById_Success() throws Exception {
        mockMvc.perform(get("/api/inventory-items/{id}", testItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Arsenicum Album"));
    }

    @Test
    void testGetInventoryItemById_NotFound() throws Exception {
        mockMvc.perform(get("/api/inventory-items/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateInventoryItem_Success() throws Exception {
        String updateRequest = """
            {
                "name": "Updated Arsenicum Album",
                "commonName": "Updated Arsenic Trioxide",
                "description": "Updated description",
                "sellingPrice": 18.00
            }
            """;

        MockMultipartFile multipartFile = new MockMultipartFile("inventoryItem", "inventoryItem.json", 
            "application/json", updateRequest.getBytes());

        mockMvc.perform(multipart("/api/inventory-items/{id}", testItem.getId())
                .file(multipartFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Arsenicum Album"));
    }

    @Test
    void testUpdateInventoryItem_NotFound() throws Exception {
        String updateRequest = """
            {
                "name": "Updated Item",
                "description": "Updated description"
            }
            """;

        MockMultipartFile multipartFile = new MockMultipartFile("inventoryItem", "inventoryItem.json", 
            "application/json", updateRequest.getBytes());

        mockMvc.perform(multipart("/api/inventory-items/999")
                .file(multipartFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteInventoryItem_Success() throws Exception {
        mockMvc.perform(delete("/api/inventory-items/{id}", testItem.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteInventoryItem_NotFound() throws Exception {
        mockMvc.perform(delete("/api/inventory-items/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateStock_Success() throws Exception {
        mockMvc.perform(patch("/api/inventory-items/{id}/stock", testItem.getId())
                .param("change", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testItem.getId()));
    }
} 