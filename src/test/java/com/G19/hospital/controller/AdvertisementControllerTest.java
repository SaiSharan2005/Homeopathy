package com.G19.hospital.controller;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.Advertisement;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.AdvertisementRepository;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdvertisementControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    private User adminUser;
    private Role adminRole;
    private Advertisement testAdvertisement;

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

        // Create test advertisement
        testAdvertisement = new Advertisement();
        testAdvertisement.setTitle("Special Homeopathic Consultation");
        testAdvertisement.setDescription("Get 20% off on your first consultation");
        testAdvertisement.setImageUrl("https://example.com/ad-image.jpg");
        testAdvertisement.setEndDate(LocalDate.now().plusDays(30));
        testAdvertisement.setIsActive(true);
        testAdvertisement = advertisementRepository.save(testAdvertisement);
    }

    @Test
    void testGetAllAdvertisements_Success() throws Exception {
        mockMvc.perform(get("/api/ads"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAdvertisementById_Success() throws Exception {
        mockMvc.perform(get("/api/ads/{id}", testAdvertisement.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAdvertisementById_NotFound() throws Exception {
        mockMvc.perform(get("/api/ads/999"))
                .andExpect(status().isInternalServerError()); // Controller throws RuntimeException
    }

    @Test
    void testGetActiveAdvertisements_Success() throws Exception {
        mockMvc.perform(get("/api/ads/active")
                .param("targetPage", "home"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAdvertisement_Success() throws Exception {
        mockMvc.perform(delete("/api/ads/{id}", testAdvertisement.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAdvertisement_NotFound() throws Exception {
        mockMvc.perform(delete("/api/ads/999"))
                .andExpect(status().isOk()); // Controller doesn't check if exists
    }

    @Test
    void testChangeStatus_Success() throws Exception {
        mockMvc.perform(get("/api/ads/{id}/status", testAdvertisement.getId())
                .param("isActive", "false"))
                .andExpect(status().isOk());
    }

    @Test
    void testSelectAdvertisement_Success() throws Exception {
        mockMvc.perform(patch("/api/ads/select/{id}", testAdvertisement.getId()))
                .andExpect(status().isOk());
    }
} 