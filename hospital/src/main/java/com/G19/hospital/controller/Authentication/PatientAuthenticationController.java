package com.G19.hospital.controller.Authentication;

import com.G19.hospital.DTOs.PatientRegisterDTO;
import com.G19.hospital.DTOs.PatientLoginDTO;
import com.G19.hospital.DTOs.PatientDetailsDTO;
import com.G19.hospital.DTOs.PatientInfoDTO;
import com.G19.hospital.model.Authentication.PatientRegister;
import com.G19.hospital.service.PatientServices;
import com.G19.hospital.service.implement.PatientServicesImplement;
import com.G19.hospital.model.Authentication.PatientDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientAuthenticationController {

    @Autowired
    private PatientServices patientServices;

    @PostMapping("/register")
    public String registerPatient(@RequestBody PatientRegisterDTO patientRegisterDTO) {
        try {
            
            PatientRegister registeredPatient = patientServices.registerPatient(patientRegisterDTO);
            String response="Registered sucessfully"+registeredPatient.getPatientId();
            return response;
        } catch (Exception e) {
            return "Registration failed: " + e.getMessage();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPatient(@RequestBody PatientLoginDTO loginRequest) {
        try {
            PatientRegister patient = patientServices.loginPatient(loginRequest.getPhoneNumber(), loginRequest.getPassword());
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }
    @PostMapping("/profile")
    public String profilePatient(@RequestBody PatientDetailsDTO patientDetailsDTO) {
        try {
            
            PatientDetails registeredPatient = patientServices.profilePatient(patientDetailsDTO);
            String response="Registered sucessfully"+registeredPatient.getPatientId();
            return response;
        } catch (Exception e) {
            return "Registration failed: " + e.getMessage();
        }
    }

    @Autowired
    private PatientServicesImplement patientService;
    
    @GetMapping("/{id}")
    public PatientRegister getPatientInfo(@PathVariable String id) {
        return patientService.getPatientInfo(id);
    }
    
}
