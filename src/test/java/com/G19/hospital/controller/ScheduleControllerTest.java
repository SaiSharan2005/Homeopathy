package com.G19.hospital.controller;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.*;
import com.G19.hospital.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ScheduleControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    @Autowired
    private DoctorTimingRepository doctorTimingRepository;

    private User doctorUser;
    private Role doctorRole;
    private DoctorDetails doctorDetails;
    private DoctorTiming doctorTiming;
    private DoctorSchedule testSchedule;

    @BeforeEach
    void setUp() {
        cleanupTestData();

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

        // Create doctor timing
        doctorTiming = new DoctorTiming();
        doctorTiming.setDoctor(doctorUser);
        doctorTiming.setStartTime(LocalTime.of(9, 0));
        doctorTiming.setEndTime(LocalTime.of(17, 0));
        doctorTiming.setInUse(false);
        doctorTiming = doctorTimingRepository.save(doctorTiming);

        // Create test schedule
        testSchedule = new DoctorSchedule();
        testSchedule.setDoctor(doctorUser);
        testSchedule.setSlot(doctorTiming);
        testSchedule.setDate(LocalDate.now().plusDays(1));
        testSchedule.setStartTime(LocalTime.of(9, 0));
        testSchedule.setEndTime(LocalTime.of(17, 0));
        testSchedule.setBooked(false);
        testSchedule = doctorScheduleRepository.save(testSchedule);
    }

    @Test
    @WithMockUser(username = "doctor")
    void testCreateSchedule_Success() throws Exception {
        mockMvc.perform(post("/api/schedule/create/{date}", "2025-07-21"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetScheduleById_Success() throws Exception {
        mockMvc.perform(get("/api/schedule/byId/{scheduleId}", testSchedule.getScheduleId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetScheduleById_NotFound() throws Exception {
        mockMvc.perform(get("/api/schedule/byId/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllSlotsByDoctor_Success() throws Exception {
        mockMvc.perform(get("/api/schedule/doctor/{doctorId}", doctorUser.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllSlotsByDoctorAndDate_Success() throws Exception {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        mockMvc.perform(get("/api/schedule/doctor/{doctorId}/date/{date}", doctorUser.getId(), tomorrow))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAvailableSlots_Success() throws Exception {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        mockMvc.perform(get("/api/schedule/available/{date}", tomorrow))
                .andExpect(status().isOk());
    }

    @Test
    void testBookSlot_Success() throws Exception {
        mockMvc.perform(post("/api/schedule/book/{scheduleId}", testSchedule.getScheduleId()))
                .andExpect(status().isOk());
    }

    @Test
    void testBookSlot_NotFound() throws Exception {
        mockMvc.perform(post("/api/schedule/book/999"))
                .andExpect(status().isBadRequest());
    }
} 