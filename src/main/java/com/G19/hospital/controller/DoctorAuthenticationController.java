package com.G19.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

import com.G19.hospital.DTO.DoctorDetailsDTO;
import com.G19.hospital.DTO.DoctorRegisterDTO;
import com.G19.hospital.model.DoctorDetails;
import com.G19.hospital.model.User;
import com.G19.hospital.service.DoctorServices;

@RestController
@RequestMapping("/api/doctor")
public class DoctorAuthenticationController {

    @Autowired
    private DoctorServices doctorServices;

    @PostMapping("/register")
    public ResponseEntity<?> registerDoctor(@RequestBody DoctorRegisterDTO doctorRegisterDTO) {
        try {
            User registeredDoctor = doctorServices.registerDoctor(doctorRegisterDTO);
            return new ResponseEntity<>(registeredDoctor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addProfile")
    public ResponseEntity<?> addDoctorProfile(@RequestBody DoctorDetailsDTO doctorDetailsDTO) {
        try {
            DoctorDetails doctorProfile = doctorServices.profileDoctor(doctorDetailsDTO);
            return new ResponseEntity<>(doctorProfile, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Profile update failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedPatientDetails() {
        try {
            // Get the currently authenticated user's information
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Assuming phone number is used as the username

            // Retrieve the patient information based on the authenticated phone number
            User patient = doctorServices.getDoctorInfoByUserName(username);
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Patient not found: " + e.getMessage());
        }
    }

    @PostMapping("/createMyProfile")
    public ResponseEntity<?> createMyProfile(@RequestBody DoctorDetailsDTO doctorDetailsDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Assuming phone number is used as the username

            // Retrieve the currently authenticated user
            User authenticatedDoctor = doctorServices.getDoctorInfoByUserName(username);

            // Set the userId in the DTO for profile creation
            doctorDetailsDTO.setUserId(authenticatedDoctor.getId());

            DoctorDetails createdProfile = doctorServices.profileDoctor(doctorDetailsDTO);
            return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Profile creation failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateMyProfile")
    public ResponseEntity<?> updateMyProfile(@RequestBody DoctorDetailsDTO doctorDetailsDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Retrieve the currently authenticated user
            User authenticatedDoctor = doctorServices.getDoctorInfoByUserName(username);

            // Set the userId in the DTO for profile update
            doctorDetailsDTO.setUserId(authenticatedDoctor.getId());

            DoctorDetails updatedProfile = doctorServices.updateDoctorProfile(doctorDetailsDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return new ResponseEntity<>("Profile update failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateProfileById/{id}")
    public ResponseEntity<?> updateProfileById(@PathVariable Long id, @RequestBody DoctorDetailsDTO doctorDetailsDTO) {
        try {
            // Set the userId in the DTO for profile update
            doctorDetailsDTO.setUserId(id);

            DoctorDetails updatedProfile = doctorServices.updateDoctorProfile(doctorDetailsDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return new ResponseEntity<>("Profile update failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PostMapping("/login")
    // public ResponseEntity<?> loginDoctor(@RequestBody DoctorLoginDTO
    // loginRequest) {
    // try {
    // User doctor = doctorServices.loginDoctor(loginRequest.getPhoneNumber(),
    // loginRequest.getPassword());
    // return ResponseEntity.ok(doctor);
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " +
    // e.getMessage());
    // }
    // }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        try {
            User doctor = doctorServices.getDoctorByDoctorId(id);
            if (doctor != null) {
                return ResponseEntity.ok(doctor);
            } else {
                return new ResponseEntity<>("Doctor not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch doctor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDoctors() {
        try {
            List<User> doctors = doctorServices.getAllDoctors();
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch doctors: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/availableDoctors")
    public ResponseEntity<?> getAllAvailableDoctors() {
        try {
            List<User> availableDoctors = doctorServices.getAllAvailableDoctors();
            return ResponseEntity.ok(availableDoctors);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch available doctors: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchDoctors/{keyword}")
    public ResponseEntity<?> searchDoctors(@PathVariable String keyword) {
        try {
            List<User> doctors = doctorServices.searchDoctors(keyword);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return new ResponseEntity<>("Search failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getDoctorCount() {
        try {
            long count = doctorServices.getDoctorCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
