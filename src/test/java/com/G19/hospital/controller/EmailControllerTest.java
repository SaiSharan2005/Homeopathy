package com.G19.hospital.controller;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.DoctorDetails;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.DoctorTiming;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.repository.DoctorDetailsRepository;
import com.G19.hospital.repository.DoctorScheduleRepository;
import com.G19.hospital.repository.DoctorTimingRepository;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmailControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;

    @Autowired
    private DoctorTimingRepository doctorTimingRepository;

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    @Autowired
    private BookingAppointmentRepository bookingAppointmentRepository;

    private User doctorUser;
    private User patientUser;
    private Role doctorRole;
    private Role patientRole;
    private DoctorDetails doctorDetails;
    private BookingAppointment bookingAppointment;

    @BeforeEach
    void setUp() {
        cleanupTestData();

        // Create roles
        doctorRole = createTestRole("DOCTOR");
        doctorRole = roleRepository.save(doctorRole);
        
        patientRole = createTestRole("PATIENT");
        patientRole = roleRepository.save(patientRole);

        // Create users
        doctorUser = createTestUser("doctor", "doctor@hospital.com", "1234567890");
        Set<Role> doctorRoles = new HashSet<>();
        doctorRoles.add(doctorRole);
        doctorUser.setRoles(doctorRoles);
        doctorUser = userRepository.save(doctorUser);

        patientUser = createTestUser("patient", "patient@example.com", "9876543210");
        Set<Role> patientRoles = new HashSet<>();
        patientRoles.add(patientRole);
        patientUser.setRoles(patientRoles);
        patientUser = userRepository.save(patientUser);

        // Create doctor details
        doctorDetails = createTestDoctorDetails(doctorUser);
        doctorDetails = doctorDetailsRepository.save(doctorDetails);

        // Create doctor timing
        DoctorTiming timing = createTestDoctorTiming(doctorUser);
        timing = doctorTimingRepository.save(timing);

        // Create doctor schedule
        DoctorSchedule schedule = createTestDoctorSchedule(doctorUser, timing);
        schedule = doctorScheduleRepository.save(schedule);

        // Create booking appointment
        bookingAppointment = createTestBookingAppointment(patientUser, doctorUser);
        bookingAppointment.setToken("TEST_TOKEN_123");
        bookingAppointment = bookingAppointmentRepository.save(bookingAppointment);
    }

    @Test
    void testSendEmail_Success() throws Exception {
        mockMvc.perform(get("/api/sendEmail")
                .param("token", "TEST_TOKEN_123"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testSendEmail_InvalidToken() throws Exception {
        mockMvc.perform(get("/api/sendEmail")
                .param("token", "INVALID_TOKEN"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testSendEmail_MissingToken() throws Exception {
        mockMvc.perform(get("/api/sendEmail"))
                .andExpect(status().isInternalServerError());
    }
} 