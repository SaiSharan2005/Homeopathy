package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.PatientDetailsDTO;
import com.G19.hospital.DTO.PatientRegisterDTO;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.PatientDetails;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.PatientDetailsRepository;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.PatientServices;
import com.G19.hospital.util.Constants.ApiMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Random;

// 
@Service
public class PatientServicesImplement implements PatientServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerPatient(PatientRegisterDTO patientRegisterDTO) throws Exception {
        // Check if phone number is already registered
        if (userRepository.existsByUsername(patientRegisterDTO.getPhoneNumber())) {
            throw new CustomSecurityException(ApiMessages.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }

        // Create a new User entity
        User patient = new User();
        patient.setUsername(patientRegisterDTO.getPatientName()); // Treat phone number as username
        patient.setEmail(patientRegisterDTO.getEmail());
        patient.setPhoneNumber(patientRegisterDTO.getPhoneNumber());
        patient.setPassword(passwordEncoder.encode(patientRegisterDTO.getPassword()));

        // Generate a unique patientId based on logic
        String firstNamePart = patientRegisterDTO.getPatientName().substring(0,
                Math.min(patientRegisterDTO.getPatientName().length(), 4));
        String lastNamePart = patientRegisterDTO.getPhoneNumber()
                .substring(Math.max(patientRegisterDTO.getPhoneNumber().length() - 4, 0));
        patient.setUserId("P29" + firstNamePart + lastNamePart); // Set patient ID

        // Assign the role of "PATIENT"
        Set<Role> roles = new HashSet<>();
        Role patientRole = roleRepository.findByName("PATIENT");
        roles.add(patientRole);
        patient.setRoles(roles);

        // Save and return the registered patient
        return userRepository.save(patient);
    }

    @Override
    public User loginPatient(String phoneNumber, String password) throws Exception {
        // Find patient by phone number (username) and validate password
        User patient = userRepository.findByUsername(phoneNumber)
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(password, patient.getPassword())) {
            throw new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
        }
        return patient;
    }

    @Override
    public PatientDetails profilePatient(PatientDetailsDTO patientDetailsDTO) throws Exception {
        // Find the associated patient (User)
        User patient = userRepository.findById(patientDetailsDTO.getUserId())
                .orElseThrow(() -> new Exception("Patient not found"));

        // Create and populate the PatientDetails entity
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setAge(patientDetailsDTO.getAge());
        patientDetails.setGender(patientDetailsDTO.getGender());
        patientDetails.setAddress(patientDetailsDTO.getAddress());
        patientDetails.setCity(patientDetailsDTO.getCity());
        patientDetails.setPincode(patientDetailsDTO.getPincode());
        patientDetails.setUser(patient); // Set the patient (User)

        return patientDetailsRepository.save(patientDetails);
    }

    @Override
    public User getPatientInfo(String patientId) {
        // Retrieve patient (User) information by patientId
        return userRepository.findByUserId(patientId)
                .orElseThrow(() -> new CustomSecurityException("Patient not found", HttpStatus.NOT_FOUND));
    }

    public User getPatientInfoByUserName(String phoneNumber) {
        try {
            // Logic to retrieve the patient from the database using the phone number
            return userRepository.findByUsername(phoneNumber)
                    .orElseThrow(() -> new Exception("Patient not found"));
        } catch (Exception e) {
            // Handle the exception (e.g., log it or throw a specific runtime exception)
            throw new RuntimeException("Error retrieving patient info: " + e.getMessage());
        }
    }

    @Override
    public long getPatientCount() {
        Role patientRole = roleRepository.findByName("PATIENT");
        return userRepository.countByRoles(patientRole);
    }

    @Override
    public List<User> searchPatients(String keyword) {
        // Search by username (phone number) or email for patients
        Role patientRole = roleRepository.findByName("PATIENT");
        return userRepository.searchUsers(keyword, patientRole);
    }

    @Override
    public List<User> getAllPatients() {
        // Search by username (phone number) or email for patients
        List<User> patients = userRepository.findAll();
        return patients;

    }

    @Override
    public PatientDetails updatePatientProfile(Long id, PatientDetailsDTO patientDetailsDTO) throws Exception {
        // Find the profile by ID
        PatientDetails existingProfile = patientDetailsRepository.findById(id).get();
                // .orElseThrow(() -> new CustomSecurityException("Profile not found", HttpStatus.NOT_FOUND));

        // Update fields
        existingProfile.setAge(patientDetailsDTO.getAge());
        existingProfile.setGender(patientDetailsDTO.getGender());
        existingProfile.setAddress(patientDetailsDTO.getAddress());
        existingProfile.setCity(patientDetailsDTO.getCity());
        existingProfile.setPincode(patientDetailsDTO.getPincode());

        // Save and return updated profile
        return patientDetailsRepository.save(existingProfile);
    }

}
