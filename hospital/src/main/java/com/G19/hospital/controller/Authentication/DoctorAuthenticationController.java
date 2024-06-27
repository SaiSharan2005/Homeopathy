package com.G19.hospital.controller.Authentication;

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
import com.G19.hospital.DTOs.DoctorDetailsDTO;
import com.G19.hospital.DTOs.DoctorLoginDTO;
import com.G19.hospital.model.Authentication.DoctorDetails;
import com.G19.hospital.model.Authentication.DoctorRegister;
import com.G19.hospital.service.DoctorServices;

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


    // {
    //     "doctorName": "John Doe",
    //     "phoneNumber": "45678",
    //     "password": "securepass123",
    //     "email": "johndoe@example.com",
    //     "doctorDetails": {
    //       "age": 40,
    //       "gender": "Male",
    //       "address": "456 Elm Street",
    //       "city": "Townsville",
    //       "pincode": "54321",
    //       "consultationFee": 120.0,
    //       "specialization": "Orthopedics",
    //       "remuneration": 6000.0
    //     }
    //   }
      
    @PostMapping("/register")
    public String registerDoctor(@RequestBody DoctorRegister doctorRegisterDTO) {
        try {
            
            DoctorRegister registeredDoctor = doctorServices.registerDoctor(doctorRegisterDTO);
            String response="Registered sucessfully"+registeredDoctor.getDoctorId();
            return response;
        } catch (Exception e) {
            return "Registration failed: " + e.getMessage();
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

    @PostMapping("/profile")
    public String profileDoctor(@RequestBody DoctorDetailsDTO doctordetailsDTO) {
        try {
            
            DoctorDetails registeredDoctor = doctorServices.profileDoctor(doctordetailsDTO);
            String response="Registered sucessfully" +registeredDoctor.getCity();
            return response;
        } catch (Exception e) {
            return "doctor detials entry failed: " + e.getMessage();
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


    

}
