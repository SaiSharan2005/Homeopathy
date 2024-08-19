package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.PatientDetailsDTO;
import com.G19.hospital.DTO.PatientRegisterDTO;
import com.G19.hospital.model.PatientDetails;
import com.G19.hospital.model.PatientRegister;
import com.G19.hospital.repository.PatientAuthenticationRepository;
import com.G19.hospital.repository.PatientDetailsRepository;
import com.G19.hospital.service.PatientServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServicesImplement implements PatientServices {

    @Autowired
    private PatientAuthenticationRepository patientRepository;

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Override
    public PatientRegister registerPatient(PatientRegisterDTO patientRegisterDTO) throws Exception {
        // Check if phone number is already registered
        if (patientRepository.findByPhoneNumber(patientRegisterDTO.getPhoneNumber()) != null) {
            throw new Exception("Phone number already in use");
        }

        // Create a new patient register entity
        PatientRegister patientRegister = new PatientRegister();
        patientRegister.setPatientName(patientRegisterDTO.getPatientName());
        patientRegister.setPhoneNumber(patientRegisterDTO.getPhoneNumber());
        patientRegister.setPassword(patientRegisterDTO.getPassword());
        patientRegister.setEmail(patientRegisterDTO.getEmail());

        // Generate patientId based on the specified logic
        String firstNamePart = patientRegisterDTO.getPatientName().substring(0, Math.min(patientRegisterDTO.getPatientName().length(), 4));
        String lastNamePart = patientRegisterDTO.getPhoneNumber().substring(Math.max(patientRegisterDTO.getPhoneNumber().length() - 4, 0));
        patientRegister.setPatientId("P29" + firstNamePart + lastNamePart);

        // Save and return the registered patient
        return patientRepository.save(patientRegister);
    }

    @Override
    public PatientRegister loginPatient(String phoneNumber, String password) throws Exception {
        // Find patient by phone number and validate password
        PatientRegister patient = patientRepository.findByPhoneNumber(phoneNumber);
        if (patient == null || !patient.getPassword().equals(password)) {
            throw new Exception("Invalid phone number or password");
        }
        return patient;
    }

    @Override
    public PatientDetails profilePatient(PatientDetailsDTO patientDetailsDTO) throws Exception {
        PatientRegister patientRegister = patientRepository.findByPatientId(patientDetailsDTO.getPatientId());
        if (patientRegister == null) {
            throw new Exception("Patient not found");
        }
    
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setAge(patientDetailsDTO.getAge());
        patientDetails.setGender(patientDetailsDTO.getGender());
        patientDetails.setAddress(patientDetailsDTO.getAddress());
        patientDetails.setCity(patientDetailsDTO.getCity());
        patientDetails.setPincode(patientDetailsDTO.getPincode());
        patientDetails.setPatientRegister(patientRegister); // Set the patient register
    
        return patientDetailsRepository.save(patientDetails);
    }
    

    @Override
    public PatientRegister getPatientInfo(String patientId) {
        // Retrieve patient register information by patientId
        return patientRepository.findByPatientId(patientId);
    }
    @Override
    public long getPatientCount() {
        return patientRepository.count();
    }

    @Override
    public List<PatientRegister> searchPatients(String keyword) {
        return patientRepository.searchPatients(keyword);
    }
}
