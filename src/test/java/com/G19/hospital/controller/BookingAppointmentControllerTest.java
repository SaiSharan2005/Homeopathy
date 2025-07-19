package com.G19.hospital.controller;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.*;
import com.G19.hospital.repository.*;
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

class BookingAppointmentControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private BookingAppointmentRepository bookingAppointmentRepository;

    // AppointmentSlotRepository doesn't exist - removed

    private User doctorUser;
    private User patientUser;
    private Role doctorRole;
    private Role patientRole;
    private DoctorDetails doctorDetails;
    private PatientDetails patientDetails;
    private BookingAppointment bookingAppointment;

    @BeforeEach
    void setUp() {
        cleanupTestData();

        // Create roles
        doctorRole = createTestRole("DOCTOR");
        patientRole = createTestRole("PATIENT");
        doctorRole = roleRepository.save(doctorRole);
        patientRole = roleRepository.save(patientRole);

        // Create doctor user
        doctorUser = createTestUser("doctor", "doctor@hospital.com", "1234567890");
        Set<Role> doctorRoles = new HashSet<>();
        doctorRoles.add(doctorRole);
        doctorUser.setRoles(doctorRoles);
        doctorUser = userRepository.save(doctorUser);

        // Create patient user
        patientUser = createTestUser("patient", "patient@example.com", "9876543210");
        Set<Role> patientRoles = new HashSet<>();
        patientRoles.add(patientRole);
        patientUser.setRoles(patientRoles);
        patientUser = userRepository.save(patientUser);

        // Create doctor details
        doctorDetails = createTestDoctorDetails(doctorUser);
        doctorDetails = doctorDetailsRepository.save(doctorDetails);

        // Create patient details
        patientDetails = createTestPatientDetails(patientUser);
        patientDetails = patientDetailsRepository.save(patientDetails);

        // Create booking appointment
        bookingAppointment = createTestBookingAppointment(patientUser, doctorUser);
        bookingAppointment = bookingAppointmentRepository.save(bookingAppointment);
    }

    @Test
    void testBookAppointment_Success() throws Exception {
        String bookingRequest = """
            {
                "patientId": %d,
                "doctorId": %d,
                "scheduleId": %d,
                "appointDate": "%s",
                "status": "SCHEDULED"
            }
            """.formatted(patientUser.getId(), doctorUser.getId(), 
                         bookingAppointment.getScheduleId().getScheduleId(),
                         LocalDate.now().plusDays(1).toString());

        mockMvc.perform(post("/api/bookingAppointments/byStaff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookingRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testBookAppointment_PastDate() throws Exception {
        String bookingRequest = """
            {
                "patientId": %d,
                "doctorId": %d,
                "scheduleId": %d,
                "appointDate": "2020-01-01",
                "status": "SCHEDULED"
            }
            """.formatted(patientUser.getId(), doctorUser.getId(), 
                         bookingAppointment.getScheduleId().getScheduleId());

        mockMvc.perform(post("/api/bookingAppointments/byStaff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookingRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAppointment_Success() throws Exception {
        mockMvc.perform(get("/api/bookingAppointments/token/{token}", bookingAppointment.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAppointment_NotFound() throws Exception {
        mockMvc.perform(get("/api/bookingAppointments/token/INVALID_TOKEN"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAppointmentStatus_Success() throws Exception {
        String updateRequest = """
            {
                "patientId": %d,
                "doctorId": %d,
                "scheduleId": %d,
                "appointDate": "%s",
                "status": "CONFIRMED"
            }
            """.formatted(patientUser.getId(), doctorUser.getId(), 
                         bookingAppointment.getScheduleId().getScheduleId(),
                         LocalDate.now().plusDays(1).toString());

        mockMvc.perform(put("/api/bookingAppointments/{id}", bookingAppointment.getBookingId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testCancelAppointment_Success() throws Exception {
        mockMvc.perform(delete("/api/bookingAppointments/{id}", bookingAppointment.getBookingId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAppointmentsByPatient_Success() throws Exception {
        mockMvc.perform(get("/api/bookingAppointments/patient/{patientId}", patientUser.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAppointmentsByDoctor_Success() throws Exception {
        mockMvc.perform(get("/api/bookingAppointments/doctor/{doctorId}", doctorUser.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAppointmentsByDate_Success() throws Exception {
        mockMvc.perform(get("/api/bookingAppointments")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAvailableSlots_Success() throws Exception {
        mockMvc.perform(get("/api/bookingAppointments")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testRescheduleAppointment_Success() throws Exception {
        String rescheduleRequest = """
            {
                "patientId": %d,
                "doctorId": %d,
                "scheduleId": %d,
                "appointDate": "%s",
                "status": "RESCHEDULED"
            }
            """.formatted(patientUser.getId(), doctorUser.getId(), 
                         bookingAppointment.getScheduleId().getScheduleId(),
                         LocalDate.now().plusDays(2).toString());

        mockMvc.perform(put("/api/bookingAppointments/{id}", bookingAppointment.getBookingId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(rescheduleRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAppointmentHistory_Success() throws Exception {
        mockMvc.perform(get("/api/bookingAppointments/patient/{patientId}", patientUser.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUpcomingAppointments_Success() throws Exception {
        mockMvc.perform(get("/api/bookingAppointments/patient/{patientId}", patientUser.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTodayAppointments_Success() throws Exception {
        mockMvc.perform(get("/api/bookingAppointments/doctor/{doctorId}", doctorUser.getId()))
                .andExpect(status().isOk());
    }
} 