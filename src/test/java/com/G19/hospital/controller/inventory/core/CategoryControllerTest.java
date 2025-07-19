package com.G19.hospital.controller.inventory.core;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.core.Category;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.inventory.core.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = com.G19.hospital.HospitalmanagementApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private User adminUser;
    private Role adminRole;
    private Category testCategory;

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

        // Set up authentication for the test
        UsernamePasswordAuthenticationToken authentication = 
            new UsernamePasswordAuthenticationToken(adminUser.getUsername(), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testCreateCategory_Success() throws Exception {
        String categoryRequest = """
            {
                "name": "Mineral Remedies",
                "description": "Homeopathic remedies derived from minerals"
            }
            """;

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mineral Remedies"));
    }

    @Test
    void testCreateCategory_DuplicateName() throws Exception {
        String categoryRequest = """
            {
                "name": "Homeopathic Remedies",
                "description": "Duplicate category"
            }
            """;

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Homeopathic Remedies"));
    }

    @Test
    void testGetAllCategories_Success() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetCategoryById_Success() throws Exception {
        mockMvc.perform(get("/api/categories/{id}", testCategory.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Homeopathic Remedies"));
    }

    @Test
    void testGetCategoryById_NotFound() throws Exception {
        mockMvc.perform(get("/api/categories/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCategory_Success() throws Exception {
        String updateRequest = """
            {
                "name": "Updated Homeopathic Remedies",
                "description": "Updated description for homeopathic remedies"
            }
            """;

        mockMvc.perform(put("/api/categories/{id}", testCategory.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Homeopathic Remedies"));
    }

    @Test
    void testUpdateCategory_NotFound() throws Exception {
        String updateRequest = """
            {
                "name": "Updated Category",
                "description": "Updated description"
            }
            """;

        mockMvc.perform(put("/api/categories/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequest))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCategory_Success() throws Exception {
        mockMvc.perform(delete("/api/categories/{id}", testCategory.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCategory_NotFound() throws Exception {
        mockMvc.perform(delete("/api/categories/999"))
                .andExpect(status().isNotFound());
    }

    // Note: The actual CategoryController only supports basic CRUD operations.
    // Advanced features like pagination, search, statistics, and bulk operations
    // are not implemented in the current controller.
} 