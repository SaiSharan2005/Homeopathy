package com.G19.hospital.controller;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.PatientDetails;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.PatientDetailsRepository;
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

class PatientAuthenticationControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    private User patientUser;
    private Role patientRole;

    @BeforeEach
    void setUp() {
        cleanupTestData();

        // Create patient role
        patientRole = createTestRole("PATIENT");
        patientRole = roleRepository.save(patientRole);

        // Create patient user with phone number as username (as expected by login)
        patientUser = new User();
        patientUser.setUsername("1234567890"); // Phone number as username
        patientUser.setEmail("patient@example.com");
        patientUser.setPhoneNumber("1234567890");
        patientUser.setPassword(passwordEncoder.encode("password123")); // Encoded password
        patientUser.setUserId("P29John1234");
        patientUser.setVerified(true);
        
        Set<Role> roles = new HashSet<>();
        roles.add(patientRole);
        patientUser.setRoles(roles);
        patientUser = userRepository.save(patientUser);

        // Don't create patient details here - let the test create them
    }

    @Test
    void testPatientLogin_Success() throws Exception {
        String loginRequest = """
            {
                "phoneNumber": "1234567890",
                "password": "password123"
            }
            """;

        mockMvc.perform(post("/api/patient/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testPatientRegistration_Success() throws Exception {
        String registrationRequest = """
            {
                "patientName": "John Doe",
                "username": "newpatient",
                "email": "newpatient@example.com",
                "phoneNumber": "9876543210",
                "password": "patient123"
            }
            """;

        mockMvc.perform(post("/api/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registrationRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testAddPatientProfile_Success() throws Exception {
        String profileRequest = """
            {
                "patientName": "John Doe",
                "age": 30,
                "gender": "MALE",
                "address": "123 Main St",
                "city": "New York",
                "pincode": "10001"
            }
            """;

        mockMvc.perform(post("/api/patient/addProfile/{username}", patientUser.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(profileRequest))
                .andExpect(status().isCreated()); // Changed from isOk() to isCreated()
    }

    @Test
    void testGetPatientById_Success() throws Exception {
        // Use the userId (patientId) instead of the database id
        mockMvc.perform(get("/api/patient/{patientId}", patientUser.getUserId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPatientById_NotFound() throws Exception {
        mockMvc.perform(get("/api/patient/nonexistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePatientProfile_Success() throws Exception {
        // First create patient details
        PatientDetails patientDetails = createTestPatientDetails(patientUser);
        patientDetails = patientDetailsRepository.save(patientDetails);
        
        String updateRequest = """
            {
                "patientName": "Updated John Doe",
                "age": 31,
                "gender": "MALE",
                "address": "456 Updated St",
                "city": "New York",
                "pincode": "10002"
            }
            """;

        mockMvc.perform(put("/api/patient/updateProfile/{id}", patientDetails.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllPatients_Success() throws Exception {
        mockMvc.perform(get("/api/patient/all"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPatientCount_Success() throws Exception {
        mockMvc.perform(get("/api/patient/count"))
                .andExpect(status().isOk());
    }

    @Test
    void testSearchPatients_Success() throws Exception {
        mockMvc.perform(get("/api/patient/search")
                .param("query", "John"))
                .andExpect(status().isOk());
    }
} 