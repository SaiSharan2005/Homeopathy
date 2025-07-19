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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User testUser;
    private Role userRole;

    @BeforeEach
    void setUp() {
        cleanupTestData();

        // Create test role
        userRole = createTestRole("USER");
        userRole = roleRepository.save(userRole);

        // Create test user
        testUser = createTestUser("testuser", "test@example.com", "1234567890");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        testUser.setRoles(roles);
        testUser = userRepository.save(testUser);
    }

    @Test
    void testUserRegistration_Success() throws Exception {
        // Given
        String registrationRequest = """
            {
                "username": "newuser",
                "email": "newuser@example.com",
                "phoneNumber": "9876543210",
                "password": "password123",
                "roles": ["USER"]
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registrationRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testUserLogin_Success() throws Exception {
        // Given
        String loginRequest = """
            {
                "phoneNumber": "1234567890",
                "password": "password123"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequest))
                .andExpect(status().isOk());
    }
} 