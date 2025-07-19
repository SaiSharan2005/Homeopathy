package com.G19.hospital.controller.inventory.Prescription;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.*;
import com.G19.hospital.model.inventory.core.Category;
import com.G19.hospital.model.inventory.core.InventoryItem;
import com.G19.hospital.model.inventory.prescription.Prescription;
import com.G19.hospital.repository.*;
import com.G19.hospital.repository.inventory.core.CategoryRepository;
import com.G19.hospital.repository.inventory.core.InventoryItemRepository;
import com.G19.hospital.repository.inventory.Prescription.PrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PrescriptionControllerTest extends TestBase {

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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    private User doctorUser;
    private User patientUser;
    private Role doctorRole;
    private Role patientRole;
    private DoctorDetails doctorDetails;
    private PatientDetails patientDetails;
    private BookingAppointment bookingAppointment;
    private Category testCategory;
    private InventoryItem testItem;
    private Prescription testPrescription;
    private Prescription prescription;

    @BeforeEach
    void setUp() {
        cleanupTestData();
        
        // Create roles
        patientRole = createTestRole("ROLE_PATIENT");
        doctorRole = createTestRole("ROLE_DOCTOR");
        patientRole = roleRepository.save(patientRole);
        doctorRole = roleRepository.save(doctorRole);
        
        // Create users
        patientUser = createTestUser("patient", "patient@test.com", "1234567890");
        doctorUser = createTestUser("doctor", "doctor@test.com", "0987654321");
        
        Set<Role> patientRoles = new HashSet<>();
        patientRoles.add(patientRole);
        patientUser.setRoles(patientRoles);
        patientUser = userRepository.save(patientUser);
        
        Set<Role> doctorRoles = new HashSet<>();
        doctorRoles.add(doctorRole);
        doctorUser.setRoles(doctorRoles);
        doctorUser = userRepository.save(doctorUser);
        
        // Create doctor details
        doctorDetails = createTestDoctorDetails(doctorUser);
        doctorDetails = doctorDetailsRepository.save(doctorDetails);
        
        // Create booking appointment with token
        bookingAppointment = createTestBookingAppointment(patientUser, doctorUser);
        bookingAppointment.setToken("TEST_TOKEN");
        bookingAppointment = bookingAppointmentRepository.save(bookingAppointment);
        
        // Create prescription
        prescription = createTestPrescription(patientUser, doctorUser, bookingAppointment);
        prescription = prescriptionRepository.save(prescription);
        
        // Set testPrescription to the same prescription for backward compatibility
        testPrescription = prescription;
    }

    @Test
    void testCreatePrescription_Success() throws Exception {
        String prescriptionRequest = """
            {
                "patientId": %d,
                "doctorId": %d,
                "bookingAppointmentId": %d,
                "notes": "Patient has mild fever and headache"
            }
            """.formatted(patientUser.getId(), doctorUser.getId(), bookingAppointment.getBookingId());

        mockMvc.perform(post("/api/prescriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(prescriptionRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetPrescriptionById_Success() throws Exception {
        mockMvc.perform(get("/api/prescriptions/{id}", testPrescription.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPrescriptionById_NotFound() throws Exception {
        mockMvc.perform(get("/api/prescriptions/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePrescription_Success() throws Exception {
        String updateRequest = """
            {
                "patientId": %d,
                "prescribedBy": %d,
                "bookingId": %d,
                "notes": "Updated prescription notes"
            }
            """.formatted(patientUser.getId(), doctorUser.getId(), 
                         bookingAppointment.getBookingId());

        mockMvc.perform(put("/api/prescriptions/{id}", testPrescription.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePrescription_Success() throws Exception {
        mockMvc.perform(delete("/api/prescriptions/{id}", testPrescription.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllPrescriptions_Success() throws Exception {
        mockMvc.perform(get("/api/prescriptions")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPrescriptionByBooking_Success() throws Exception {
        mockMvc.perform(get("/api/prescriptions/booking/{bookingId}", bookingAppointment.getBookingId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPrescriptionByToken_Success() throws Exception {
        mockMvc.perform(get("/api/prescriptions/token/{token}", "TEST_TOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPrescriptionsByDoctor_Success() throws Exception {
        mockMvc.perform(get("/api/prescriptions/doctor/{doctorId}", doctorUser.getId())
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPrescriptionsByPatient_Success() throws Exception {
        mockMvc.perform(get("/api/prescriptions/patient/{patientId}", patientUser.getId())
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetInstructionsByPatient_Success() throws Exception {
        mockMvc.perform(get("/api/prescriptions/patient/{patientId}/instructions", patientUser.getId()))
                .andExpect(status().isOk());
    }
} 