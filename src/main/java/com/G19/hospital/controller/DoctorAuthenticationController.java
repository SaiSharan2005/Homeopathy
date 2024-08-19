package com.G19.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.G19.hospital.DTO.DoctorDetailsDTO;
import com.G19.hospital.DTO.DoctorLoginDTO;
import com.G19.hospital.DTO.DoctorRegisterDTO;
import com.G19.hospital.model.DoctorDetails;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.service.DoctorServices;
import org.springframework.web.bind.annotation.RequestParam;


// import com.G19.hospital.DTOs.DoctorRegisterDTO;
// import com.G19.hospital.DTOs.DoctorLoginDTO;
// import com.G19.hospital.DTOs.DoctorDetailsDTO;
// import com.G19.hospital.model.Authentication.DoctorRegister;
// // import com.G19.hospital.repository.DoctorAuthenticationRepository;
// import com.G19.hospital.model.Authentication.DoctorDetails;
// import com.G19.hospital.service.DoctorServices;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorAuthenticationController {

    @Autowired
    private DoctorServices doctorServices;



      
    @PostMapping("/register")
    public ResponseEntity<?> registerDoctor(@RequestBody DoctorRegisterDTO doctorRegisterDTO) {
        try {
            
            DoctorRegister registeredDoctor = doctorServices.registerDoctor(doctorRegisterDTO);

            return new ResponseEntity<>(registeredDoctor, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/profile")
    public ResponseEntity<?> DoctorProfile(@RequestBody DoctorDetailsDTO doctorDetailsDTO) {
        try {
            DoctorDetails doctorProfile = doctorServices.profileDoctor(doctorDetailsDTO);
            return new ResponseEntity<>(doctorProfile, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PostMapping("/login")
    public ResponseEntity<?> loginPatient(@RequestBody DoctorLoginDTO loginRequest) {
        try {
            DoctorRegister doctor = doctorServices.loginDoctor(loginRequest.getPhoneNumber(), loginRequest.getPassword());
            return ResponseEntity.ok(doctor);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable String id) {
        try {
            DoctorRegister doctor = doctorServices.getDoctorByDoctorId(id);
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
            List<DoctorRegister> doctors = doctorServices.getAllDoctors();
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch doctors: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchDoctors/{keyword}")
    public List<DoctorRegister> searchDoctors(@PathVariable String keyword) throws Exception {
        System.out.println(keyword);
        return doctorServices.searchDoctors(keyword);
    }
    
    @GetMapping("/count")
    public Long DoctorCount() throws Exception {
        return doctorServices.getDoctorCount();
    }
    
}
