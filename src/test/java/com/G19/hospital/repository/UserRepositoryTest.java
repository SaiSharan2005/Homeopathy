package com.G19.hospital.repository;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest extends TestBase {

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
        userRole = createTestRole("ROLE_USER");
        userRole = roleRepository.save(userRole);

        // Create test user
        testUser = createTestUser("testuser", "test@example.com", "1234567890");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        testUser.setRoles(roles);
        testUser = userRepository.save(testUser);
    }

    @Test
    void testFindByUsername_Success() {
        // When
        Optional<User> foundUser = userRepository.findByUsername("testuser");

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    void testFindByUsername_NotFound() {
        // When
        Optional<User> foundUser = userRepository.findByUsername("nonexistentuser");

        // Then
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindByPhoneNumber_Success() {
        // When
        Optional<User> foundUser = userRepository.findByPhoneNumber("1234567890");

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
        assertEquals("1234567890", foundUser.get().getPhoneNumber());
    }
} 