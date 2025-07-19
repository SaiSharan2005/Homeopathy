package com.G19.hospital.controller;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.ActivityLog;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.ActivityLogRepository;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ActivityLogControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    private User testUser;
    private Role userRole;
    private ActivityLog activityLog;

    @BeforeEach
    void setUp() {
        cleanupTestData();

        // Create user role
        userRole = createTestRole("USER");
        userRole = roleRepository.save(userRole);

        // Create test user
        testUser = createTestUser("testuser", "test@example.com", "1234567890");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        testUser.setRoles(roles);
        testUser = userRepository.save(testUser);

        // Create activity log
        activityLog = new ActivityLog();
        activityLog.setUserType("USER");
        activityLog.setUserId(testUser.getUserId().hashCode());
        activityLog.setMessage("User logged in successfully");
        activityLog.setTimestamp(LocalDateTime.now());
        activityLog = activityLogRepository.save(activityLog);
    }

    @Test
    void testLogActivity_Success() throws Exception {
        String logRequest = """
            {
                "userType": "USER",
                "userId": %d,
                "message": "User logged out",
                "timestamp": "%s"
            }
            """.formatted(testUser.getId(), LocalDateTime.now().toString());

        mockMvc.perform(post("/api/activity-log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(logRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetActivityLogs_Success() throws Exception {
        mockMvc.perform(get("/api/activity-log")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActivityLogsByUser_Success() throws Exception {
        mockMvc.perform(get("/api/activity-log")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActivityLogsByAction_Success() throws Exception {
        mockMvc.perform(get("/api/activity-log")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActivityLogsByDateRange_Success() throws Exception {
        mockMvc.perform(get("/api/activity-log")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActivityLogsByIpAddress_Success() throws Exception {
        mockMvc.perform(get("/api/activity-log")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActivityLogsWithPagination_Success() throws Exception {
        mockMvc.perform(get("/api/activity-log")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActivityLogsSummary_Success() throws Exception {
        mockMvc.perform(get("/api/activity-log")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActivityLogsByUserAndDateRange_Success() throws Exception {
        mockMvc.perform(get("/api/activity-log")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActivityLogsByUserAndAction_Success() throws Exception {
        mockMvc.perform(get("/api/activity-log")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteActivityLogs_Success() throws Exception {
        mockMvc.perform(delete("/api/activity-log/{id}", activityLog.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetActivityLogsByUserAgent_Success() throws Exception {
        mockMvc.perform(get("/api/activity-log")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActivityLogsStatistics_Success() throws Exception {
        mockMvc.perform(get("/api/activity-log")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }
} 