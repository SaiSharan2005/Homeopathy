package com.G19.hospital.service;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AdminServiceTest extends TestBase {

    @Autowired
    private AdminService adminService;

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
        adminUser = createTestUser("admin", "admin@example.com", "1234567890");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRoles(adminRoles);
        adminUser = userRepository.save(adminUser);
    }

    @Test
    void testGetAllStaff_Success() {
        // When
        List<User> users = adminService.getAllStaff();

        // Then
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("admin", users.get(0).getUsername());
    }

    @Test
    void testGetUsersByRole_Success() {
        // When
        List<User> admins = adminService.getUsersByRole("ADMIN");

        // Then
        assertEquals(1, admins.size());
        assertEquals("admin", admins.get(0).getUsername());
    }
} 