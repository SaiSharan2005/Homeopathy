package com.G19.hospital.controller;

import com.G19.hospital.service.PatientServices;
import com.G19.hospital.service.implement.PatientServicesImplement;
import com.G19.hospital.DTO.PatientDetailsDTO;
import com.G19.hospital.DTO.PatientInfoDTO;
import com.G19.hospital.DTO.PatientLoginDTO;
import com.G19.hospital.DTO.PatientRegisterDTO;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.PatientDetails;
import com.G19.hospital.model.PatientRegister;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientAuthenticationController {

    @Autowired
    private PatientServices patientServices;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody PatientRegisterDTO patientRegisterDTO) {
        try {
            
            PatientRegister registeredPatient = patientServices.registerPatient(patientRegisterDTO);
            // String response="Registered sucessfully"+registeredPatient.getPatientId();
            // return response;
            return ResponseEntity.ok(registeredPatient);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
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
    public ResponseEntity<?> profilePatient(@RequestBody PatientDetailsDTO patientDetailsDTO) {
        try {
            
            PatientDetails registeredPatient = patientServices.profilePatient(patientDetailsDTO);
            return ResponseEntity.ok(registeredPatient);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }

    // @Autowired
    // private PatientServicesImplement patientService;
    
 
    @GetMapping("/{patientId}")
    public PatientRegister getPatientByPatientId(@PathVariable String patientId) throws Exception {
        return patientServices.getPatientInfo(patientId);
    }


    @GetMapping("/searchPatient/{keyword}")
    public List<PatientRegister> searchDoctors(@PathVariable String keyword) throws Exception {
        // System.out.println(keyword);
        return patientServices.searchPatients(keyword);
    }

    
    @GetMapping("/count")
    public Long PatientCount() throws Exception {
        return patientServices.getPatientCount();
    }
    
}