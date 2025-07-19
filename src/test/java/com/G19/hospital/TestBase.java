package com.G19.hospital;

import com.G19.hospital.model.User;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.DoctorDetails;
import com.G19.hospital.model.PatientDetails;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.DoctorTiming;
import com.G19.hospital.model.Advertisement;
import com.G19.hospital.model.ActivityLog;
import com.G19.hospital.model.inventory.core.Category;
import com.G19.hospital.model.inventory.core.InventoryItem;
import com.G19.hospital.model.inventory.core.Supplier;
import com.G19.hospital.model.inventory.prescription.Prescription;
import com.G19.hospital.model.inventory.prescription.PrescriptionItem;
import com.G19.hospital.model.Questionner.QuestionSet;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.DoctorDetailsRepository;
import com.G19.hospital.repository.PatientDetailsRepository;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.repository.DoctorScheduleRepository;
import com.G19.hospital.repository.DoctorTimingRepository;
import com.G19.hospital.repository.ActivityLogRepository;
import com.G19.hospital.repository.AdvertisementRepository;
import com.G19.hospital.repository.inventory.core.CategoryRepository;
import com.G19.hospital.repository.inventory.core.InventoryItemRepository;
import com.G19.hospital.repository.inventory.core.SupplierRepository;
import com.G19.hospital.repository.inventory.Prescription.PrescriptionRepository;
import com.G19.hospital.repository.inventory.Prescription.PrescriptionItemRepository;
import com.G19.hospital.repository.inventory.billing.InvoiceRepository;
import com.G19.hospital.repository.questionnere.QuestionSetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class TestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    // Repository autowiring for cleanup
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected DoctorDetailsRepository doctorDetailsRepository;
    @Autowired
    protected PatientDetailsRepository patientDetailsRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected InventoryItemRepository inventoryItemRepository;
    @Autowired
    protected SupplierRepository supplierRepository;
    @Autowired
    protected BookingAppointmentRepository bookingAppointmentRepository;
    @Autowired
    protected PrescriptionRepository prescriptionRepository;
    @Autowired
    protected PrescriptionItemRepository prescriptionItemRepository;
    @Autowired
    protected InvoiceRepository invoiceRepository;
    @Autowired
    protected ActivityLogRepository activityLogRepository;
    @Autowired
    protected AdvertisementRepository advertisementRepository;
    @Autowired
    protected DoctorScheduleRepository doctorScheduleRepository;
    @Autowired
    protected DoctorTimingRepository doctorTimingRepository;
    @Autowired
    protected QuestionSetRepository questionSetRepository;

    // Test Data Builders
    protected User createTestUser(String username, String email, String phoneNumber) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(passwordEncoder.encode("password123"));
        user.setUserId("USER" + System.currentTimeMillis());
        user.setVerified(true);
        return user;
    }

    protected Role createTestRole(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }

    protected DoctorDetails createTestDoctorDetails(User user) {
        DoctorDetails doctorDetails = new DoctorDetails();
        doctorDetails.setUser(user);
        doctorDetails.setSpecialization("Homeopathy");
        doctorDetails.setDoctorId("DOC" + System.currentTimeMillis());
        doctorDetails.setConsultationFee(100.0);
        return doctorDetails;
    }

    protected PatientDetails createTestPatientDetails(User user) {
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setUser(user);
        patientDetails.setAge(30);
        patientDetails.setGender("Male");
        patientDetails.setAddress("123 Test Street");
        return patientDetails;
    }

    protected Category createTestCategory(String name) {
        Category category = new Category();
        category.setName(name);
        category.setDescription("Test category description");
        return category;
    }

    protected Supplier createTestSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier@test.com");
        supplier.setContactDetails("+1234567890");
        supplier.setAddress("123 Supplier Street");
        return supplier;
    }

    protected InventoryItem createTestInventoryItem(Category category, User createdBy) {
        InventoryItem item = new InventoryItem();
        item.setName("Arsenicum Album");
        item.setCommonName("Arsenic Trioxide");
        item.setSource("Mineral");
        item.setPotency("30C");
        item.setFormulation("Liquid");
        item.setDescription("Used for treating food poisoning and digestive disorders");
        item.setManufacturer("Boiron");
        item.setUnit("bottle");
        item.setReorderLevel(50);
        item.setStorageConditions("Store in cool, dry place");
        item.setIndications("Food poisoning, digestive disorders");
        item.setContraindications("None known");
        item.setSideEffects("Rarely any side effects");
        item.setUsageInstructions("Take 3 drops under tongue 3 times daily");
        item.setRegulatoryStatus("Approved");
        item.setSellingPrice(new BigDecimal("15.00"));
        item.setCategory(category);
        item.setCreatedBy(createdBy);
        return item;
    }

    protected BookingAppointment createTestBookingAppointment(User patient, User doctor) {
        // First create a DoctorTiming
        DoctorTiming timing = createTestDoctorTiming(doctor);
        timing = doctorTimingRepository.save(timing);
        
        // Then create a DoctorSchedule
        DoctorSchedule schedule = createTestDoctorSchedule(doctor, timing);
        schedule = doctorScheduleRepository.save(schedule);
        
        BookingAppointment booking = new BookingAppointment();
        booking.setPatient(patient);
        booking.setDoctor(doctor);
        booking.setScheduleId(schedule); // Set the required schedule
        booking.setAppointmentDate(LocalDateTime.now().plusDays(1).toLocalDate());
        booking.setStatus("SCHEDULED");
        booking.setToken("BK" + System.currentTimeMillis());
        return booking;
    }

    protected DoctorTiming createTestDoctorTiming(User doctor) {
        DoctorTiming timing = new DoctorTiming();
        timing.setDoctor(doctor);
        timing.setStartTime(LocalTime.of(9, 0)); // 9:00 AM
        timing.setEndTime(LocalTime.of(17, 0)); // 5:00 PM
        timing.setInUse(false);
        return timing;
    }

    protected DoctorSchedule createTestDoctorSchedule(User doctor, DoctorTiming timing) {
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setSlot(timing);
        schedule.setDate(LocalDateTime.now().plusDays(1).toLocalDate());
        schedule.setStartTime(LocalTime.of(9, 0));
        schedule.setEndTime(LocalTime.of(17, 0));
        schedule.setBooked(false);
        return schedule;
    }

    protected Prescription createTestPrescription(User patient, User doctor, BookingAppointment booking) {
        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setPrescribedBy(doctor);
        prescription.setBookingAppointment(booking);
        prescription.setRxDate(LocalDateTime.now());
        prescription.setNotes("Test prescription notes");
        return prescription;
    }

    protected PrescriptionItem createTestPrescriptionItem(Prescription prescription, InventoryItem item) {
        PrescriptionItem prescriptionItem = new PrescriptionItem();
        prescriptionItem.setPrescription(prescription);
        prescriptionItem.setDrug(item);
        prescriptionItem.setQuantity(1);
        prescriptionItem.setFrequency("3 times daily");
        prescriptionItem.setDuration("7 days");
        prescriptionItem.setAdditionalInstructions("Take under tongue");
        return prescriptionItem;
    }

    protected String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cleanup method that deletes data in the correct order to avoid foreign key constraint violations
     */
    protected void cleanupTestData() {
        try {
            // Delete in order of dependencies (child tables first, then parent tables)
            
            // 1. Delete prescription items first (depends on prescriptions and inventory items)
            prescriptionItemRepository.deleteAll();
            
            // 2. Delete prescriptions (depends on users and booking appointments)
            prescriptionRepository.deleteAll();
            
            // 3. Delete booking appointments (depends on users)
            bookingAppointmentRepository.deleteAll();
            
            // 4. Delete invoices (depends on users)
            invoiceRepository.deleteAll();
            
            // 5. Delete activity logs (depends on users)
            activityLogRepository.deleteAll();
            
            // 6. Delete advertisements (depends on users)
            advertisementRepository.deleteAll();
            
            // 7. Delete doctor schedules (depends on users and doctor timings)
            doctorScheduleRepository.deleteAll();
            
            // 8. Delete doctor timings (depends on users)
            doctorTimingRepository.deleteAll();
            
            // 9. Delete question sets (depends on users)
            questionSetRepository.deleteAll();
            
            // 10. Delete inventory items (depends on categories and users)
            inventoryItemRepository.deleteAll();
            
            // 11. Delete categories (depends on users)
            categoryRepository.deleteAll();
            
            // 12. Delete suppliers (depends on users)
            supplierRepository.deleteAll();
            
            // 13. Delete doctor details (depends on users)
            doctorDetailsRepository.deleteAll();
            
            // 14. Delete patient details (depends on users)
            patientDetailsRepository.deleteAll();
            
            // 15. Delete user roles (junction table)
            // Note: This is handled automatically by JPA when users are deleted
            
            // 16. Delete users (parent table)
            userRepository.deleteAll();
            
            // 17. Delete roles (parent table)
            roleRepository.deleteAll();
            
        } catch (Exception e) {
            // Log the error but don't fail the test
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
} 