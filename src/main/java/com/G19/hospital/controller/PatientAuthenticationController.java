package com.G19.hospital.controller;

import com.G19.hospital.service.PatientServices;
import com.G19.hospital.DTO.PatientDetailsDTO;
import com.G19.hospital.DTO.PatientLoginDTO;
import com.G19.hospital.DTO.PatientRegisterDTO;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.PatientDetails;
import com.G19.hospital.model.User;
// import com.G19.hospital.repository.PatientDetailsRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/patient")
public class PatientAuthenticationController {

    @Autowired
    private PatientServices patientServices;

    // @Autowired
    // private PatientDetailsRepository patientDetailsRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody PatientRegisterDTO patientRegisterDTO) {
        try {
            User registeredPatient = patientServices.registerPatient(patientRegisterDTO);
            return ResponseEntity.ok(registeredPatient);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPatient(@RequestBody PatientLoginDTO loginRequest) {
        try {
            User patient = patientServices.loginPatient(loginRequest.getPhoneNumber(), loginRequest.getPassword());
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/profile")
    public ResponseEntity<?> profilePatient(@RequestBody PatientDetailsDTO patientDetailsDTO) {
        try {
            PatientDetails registeredPatient = patientServices.profilePatient(patientDetailsDTO);
            return ResponseEntity.ok(registeredPatient);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Profile creation failed: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedPatientDetails() {
        try {
            // Get the currently authenticated user's information
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Assuming phone number is used as the username

            // Retrieve the patient information based on the authenticated phone number
            User patient = patientServices.getPatientInfoByUserName(username);
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Patient not found: " + e.getMessage());
        }
    }@PostMapping("/addProfile/{username}")
    public ResponseEntity<?> addProfileByUsername(
            @PathVariable String username,
            @RequestBody PatientDetailsDTO patientDetailsDTO) {
        try {
            // 1) find the user by username
            User patient = patientServices.getPatientInfoByUserName(username);
    
            // 2) set the userId on the DTO
            patientDetailsDTO.setUserId(patient.getId());
    
            // 3) save the profile
            PatientDetails newProfile = patientServices.profilePatient(patientDetailsDTO);
    
            // 4) return it
            return ResponseEntity.status(HttpStatus.CREATED).body(newProfile);
        } catch (CustomSecurityException cse) {
            return ResponseEntity.status(cse.getHttpStatus()).body(cse.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to add profile: " + ex.getMessage());
        }
    }
    

    @GetMapping("/{patientId}")
    public ResponseEntity<?> getPatientByPatientId(@PathVariable String patientId) {
        try {
            User patient = patientServices.getPatientInfo(patientId);
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Patient not found: " + e.getMessage());
        }
    }

    @PostMapping("/CreateProfile")
    public ResponseEntity<?> addProfile(@RequestBody PatientDetailsDTO patientDetailsDTO) {
        try {
            // Extract the user ID from the authenticated token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Assuming phone number is used as username

            // Fetch the patient based on username
            User patient = patientServices.getPatientInfoByUserName(username);

            // Set userId in DTO to link it correctly
            patientDetailsDTO.setUserId(patient.getId());

            // Save patient profile
            PatientDetails newProfile = patientServices.profilePatient(patientDetailsDTO);
            return ResponseEntity.ok(newProfile);
        } catch (CustomSecurityException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add profile: " + e.getMessage());
        }
    }

    @PutMapping("/updateProfile/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody PatientDetailsDTO patientDetailsDTO) {
        try {
            // Implement logic for updating patient details using patient ID
            PatientDetails updatedProfile = patientServices.updatePatientProfile(id, patientDetailsDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (CustomSecurityException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update profile: " + e.getMessage());
        }
    }

    @PutMapping("/updateMyProfile")
    public ResponseEntity<?> updateProfile(@RequestBody PatientDetailsDTO patientDetailsDTO) {
        try {
            // Extract the user ID from the authenticated token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Assuming phone number is used as username

            // Fetch the patient based on username
            User patient = patientServices.getPatientInfoByUserName(username);

            System.out.println(patient.getId());
            // if (patient == null) {
            //     throw new CustomSecurityException("User not found", HttpStatus.UNAUTHORIZED);
            // } 
            Long id = patient.getPatientDetails().getId();
            // patientDetailsRepository.
            // Update the patient's profile using the user ID
            PatientDetails updatedProfile = patientServices.updatePatientProfile(id, patientDetailsDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (CustomSecurityException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update profile: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<User>> searchPatients(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<User> result = patientServices
                .searchPatients(query, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getPatientCount() {
        try {
            Long count = patientServices.getPatientCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    // @GetMapping("/all")
    // public ResponseEntity<?> getallPatient() {
    //     try {
    //         List<User> patient = patientServices.getAllPatients();
    //         return ResponseEntity.ok(patient);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>("Failed to fetch doctors: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllPatientsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<User> patientsPage = patientServices
                .getAllPatients(PageRequest.of(page, size));
            return ResponseEntity.ok(patientsPage);
        } catch (Exception e) {
            return new ResponseEntity<>(
                "Failed to fetch patients (paged): " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
