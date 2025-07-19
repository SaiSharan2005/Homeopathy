package com.G19.hospital.controller.inventory.billing;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.*;
import com.G19.hospital.model.inventory.Billing_Payment_Due.Invoice;
import com.G19.hospital.model.inventory.Billing_Payment_Due.InvoiceStatus;
import com.G19.hospital.repository.*;
import com.G19.hospital.repository.inventory.billing.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InvoiceControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    private User patientUser;
    private Role patientRole;
    private PatientDetails patientDetails;
    private Invoice testInvoice;

    @BeforeEach
    void setUp() {
        cleanupTestData();

        // Create patient role
        patientRole = createTestRole("PATIENT");
        patientRole = roleRepository.save(patientRole);

        // Create patient user
        patientUser = createTestUser("patient", "patient@test.com", "1234567890");
        Set<Role> roles = new HashSet<>();
        roles.add(patientRole);
        patientUser.setRoles(roles);
        patientUser = userRepository.save(patientUser);

        // Create patient details
        patientDetails = createTestPatientDetails(patientUser);
        patientDetails = patientDetailsRepository.save(patientDetails);

        // Create test invoice
        testInvoice = new Invoice();
        testInvoice.setPatient(patientUser);
        testInvoice.setIssueDate(LocalDate.now());
        testInvoice.setDueDate(LocalDate.now().plusDays(30));
        testInvoice.setTotalAmount(new BigDecimal("165.00"));
        testInvoice.setStatus(InvoiceStatus.PENDING);
        testInvoice = invoiceRepository.save(testInvoice);
    }

    @Test
    void testGetInvoiceById_Success() throws Exception {
        mockMvc.perform(get("/api/invoices/{id}", testInvoice.getInvoiceId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetInvoiceById_NotFound() throws Exception {
        mockMvc.perform(get("/api/invoices/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllInvoices_Success() throws Exception {
        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void testGetInvoicesByPatient_Success() throws Exception {
        mockMvc.perform(get("/api/invoices/patient/{patientId}", patientUser.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetInvoicesByStatus_Success() throws Exception {
        mockMvc.perform(get("/api/invoices/status/PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void testDeleteInvoice_Success() throws Exception {
        mockMvc.perform(delete("/api/invoices/{id}", testInvoice.getInvoiceId()))
                .andExpect(status().isNoContent());
    }
} 