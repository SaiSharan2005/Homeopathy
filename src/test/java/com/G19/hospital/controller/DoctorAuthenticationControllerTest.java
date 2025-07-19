package com.G19.hospital.controller;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.DoctorDetails;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.DoctorDetailsRepository;
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

class DoctorAuthenticationControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;

    private User doctorUser;
    private Role doctorRole;
    private DoctorDetails doctorDetails;

    @BeforeEach
    void setUp() {
        cleanupTestData();
        doctorDetailsRepository.deleteAll();

        // Create doctor role
        doctorRole = createTestRole("DOCTOR");
        doctorRole = roleRepository.save(doctorRole);

        // Create doctor user
        doctorUser = createTestUser("doctor", "doctor@hospital.com", "1234567890");
        Set<Role> roles = new HashSet<>();
        roles.add(doctorRole);
        doctorUser.setRoles(roles);
        doctorUser = userRepository.save(doctorUser);

        // Create doctor details
        doctorDetails = createTestDoctorDetails(doctorUser);
        doctorDetails = doctorDetailsRepository.save(doctorDetails);
    }

    @Test
    void testDoctorRegistration_Success() throws Exception {
        String registrationRequest = """
            {
                "username": "newdoctor",
                "email": "newdoctor@hospital.com",
                "phoneNumber": "9876543210",
                "password": "doctor123"
            }
            """;

        mockMvc.perform(post("/api/doctor/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registrationRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void testAddDoctorProfile_Success() throws Exception {
        // Create a new doctor user without profile for this test
        User newDoctorUser = createTestUser("newdoctor2", "newdoctor2@hospital.com", "1111111111");
        Set<Role> roles = new HashSet<>();
        roles.add(doctorRole);
        newDoctorUser.setRoles(roles);
        newDoctorUser = userRepository.save(newDoctorUser);

        String profileRequest = """
            {
                "specialization": "Homeopathy",
                "consultationFee": 150.0,
                "qualification": "MD Homeopathy",
                "experience": 5,
                "address": "123 Medical Center",
                "city": "Mumbai",
                "pincode": "400001",
                "gender": "Male",
                "age": 35
            }
            """;

        mockMvc.perform(post("/api/doctor/addProfile/{username}", newDoctorUser.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(profileRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetDoctorByDoctorId_Success() throws Exception {
        mockMvc.perform(get("/api/doctor/{doctorId}", doctorUser.getUserId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDoctorByDoctorId_NotFound() throws Exception {
        mockMvc.perform(get("/api/doctor/nonexistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetDoctorById_Success() throws Exception {
        mockMvc.perform(get("/api/doctor/byId/{id}", doctorUser.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDoctorById_NotFound() throws Exception {
        mockMvc.perform(get("/api/doctor/byId/999"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testSearchDoctors_Success() throws Exception {
        mockMvc.perform(get("/api/doctor/search")
                .param("keyword", "Homeopathy")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllDoctors_Success() throws Exception {
        mockMvc.perform(get("/api/doctor/all")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAvailableDoctors_Success() throws Exception {
        mockMvc.perform(get("/api/doctor/availableDoctors")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDoctorCount_Success() throws Exception {
        mockMvc.perform(get("/api/doctor/count"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProfileById_Success() throws Exception {
        String updateRequest = """
            {
                "specialization": "Advanced Homeopathy",
                "consultationFee": 200.0,
                "qualification": "MD Advanced Homeopathy",
                "experience": 8,
                "address": "456 Medical Center",
                "city": "Mumbai",
                "pincode": "400002",
                "gender": "Male",
                "age": 40
            }
            """;

        mockMvc.perform(put("/api/doctor/updateProfileById/{id}", doctorUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequest))
                .andExpect(status().isOk());
    }
} 