package com.G19.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.G19.hospital.DTO.DoctorDetailsDTO;
import com.G19.hospital.DTO.DoctorLoginDTO;
import com.G19.hospital.DTO.DoctorRegisterDTO;
import com.G19.hospital.model.DoctorDetails;
import com.G19.hospital.model.User;
import com.G19.hospital.service.DoctorServices;

@RestController
@RequestMapping("/doctor")
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

    @PostMapping("/profile")
    public ResponseEntity<?> updateDoctorProfile(@RequestBody DoctorDetailsDTO doctorDetailsDTO) {
        try {
            DoctorDetails doctorProfile = doctorServices.profileDoctor(doctorDetailsDTO);
            return new ResponseEntity<>(doctorProfile, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Profile update failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginDoctor(@RequestBody DoctorLoginDTO loginRequest) {
        try {
            User doctor = doctorServices.loginDoctor(loginRequest.getPhoneNumber(), loginRequest.getPassword());
            return ResponseEntity.ok(doctor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable String id) {
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
