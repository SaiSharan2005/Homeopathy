package com.G19.hospital.controller;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminAuthenticationControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User adminUser;
    private Role adminRole;

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
    }

    // Note: Login is handled by AuthController, not AdminAuthenticationController
    // AdminAuthenticationController only handles staff registration and profile management

    @Test
    void testAdminRegistration_Success() throws Exception {
        String registrationRequest = """
            {
                "name": "New Admin",
                "role": "ADMIN",
                "phoneNumber": "9876543210",
                "email": "newadmin@hospital.com",
                "password": "admin123"
            }
            """;

        mockMvc.perform(post("/api/admin/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registrationRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void testAdminRegistration_DuplicatePhoneNumber() throws Exception {
        String registrationRequest = """
            {
                "name": "Duplicate Admin",
                "role": "ADMIN",
                "phoneNumber": "1234567890",
                "email": "duplicate@hospital.com",
                "password": "admin123"
            }
            """;

        mockMvc.perform(post("/api/admin/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registrationRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAdminProfile_Success() throws Exception {
        mockMvc.perform(get("/api/admin/{id}", adminUser.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateAdminProfile_Success() throws Exception {
        String updateRequest = """
            {
                "name": "Updated Admin",
                "role": "ADMIN",
                "phoneNumber": "1234567890",
                "email": "updated@hospital.com",
                "password": "password123"
            }
            """;

        mockMvc.perform(put("/api/admin/updateProfileById/{id}", adminUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequest))
                .andExpect(status().isOk());
    }

    // Note: Password change functionality is not implemented in AdminAuthenticationController
    // This would typically be handled by a separate service or controller
} 