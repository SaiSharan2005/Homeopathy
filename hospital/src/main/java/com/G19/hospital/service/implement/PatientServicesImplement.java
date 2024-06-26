package com.G19.hospital.service.implement;

import com.G19.hospital.DTOs.PatientRegisterDTO;
import com.G19.hospital.DTOs.PatientDetailsDTO;
import com.G19.hospital.DTOs.PatientInfoDTO;
import com.G19.hospital.model.Authentication.PatientRegister;
import com.G19.hospital.model.Authentication.PatientDetails;
import com.G19.hospital.repository.PatientAuthenticationRepository;
import com.G19.hospital.repository.PatientDetailsRepository;
import com.G19.hospital.service.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServicesImplement implements PatientServices {

    @Autowired
    private PatientAuthenticationRepository patientRepository;

    @Override
    public PatientRegister registerPatient(PatientRegisterDTO patientRegisterDTO) throws Exception {
        if (patientRepository.findByPhoneNumber(patientRegisterDTO.getPhoneNumber()) != null) {
            throw new Exception("Phone number already in use");
        }

        PatientRegister patientRegister = new PatientRegister();
        patientRegister.setPatientName(patientRegisterDTO.getPatientName());
        patientRegister.setPhoneNumber(patientRegisterDTO.getPhoneNumber());
        patientRegister.setPassword(patientRegisterDTO.getPassword());
        patientRegister.setEmail(patientRegisterDTO.getEmail());
         // Set patientId based on the specified logic
         String firstNamePart = patientRegisterDTO.getPatientName().substring(0, Math.min(patientRegisterDTO.getPatientName().length(), 4));
         String lastNamePart = patientRegisterDTO.getPhoneNumber().substring(Math.max(patientRegisterDTO.getPhoneNumber().length() - 4, 0));
 
         patientRegister.setPatientId("P29"+firstNamePart + lastNamePart);

        return patientRepository.save(patientRegister);
    }

    @Override
    public PatientRegister loginPatient(String phoneNumber, String password) throws Exception {
        PatientRegister patient = patientRepository.findByPhoneNumber(phoneNumber);
        if (patient == null || !patient.getPassword().equals(password)) {
            throw new Exception("Invalid phone number or password");
        }
        return patient;
    }
    @Autowired
    private PatientDetailsRepository patientDetailsRepository;
    
    @Override
    public PatientDetails profilePatient(PatientDetailsDTO patientDetailsDTO) throws Exception {
       
        PatientDetails patientDetails = new PatientDetails();

        patientDetails.setId(patientDetailsDTO.getId());
        patientDetails.setPatientId(patientDetailsDTO.getPatientId());
        patientDetails.setAge(patientDetailsDTO.getAge());
        patientDetails.setAddress(patientDetailsDTO.getAddress());
        patientDetails.setGender(patientDetailsDTO.getGender());
        patientDetails.setCity(patientDetailsDTO.getCity());
        patientDetails.setPincode(patientDetailsDTO.getPincode());
        return patientDetailsRepository.save(patientDetails);
    }

    // public PatientInfoDTO getPatientInfo(String patientId) {
    //     PatientDetails patientDetails = patientDetailsRepository.findByPatientId(patientId);
    //     PatientRegister patientRegister = patientRepository.findByPatientId(patientId);

    //     if (patientDetails == null || patientRegister == null) {
    //         throw new RuntimeException("Patient not found");
    //     }

    //     return new PatientInfoDTO(patientDetails, patientRegister);
    // }
    @Override
    public PatientRegister getPatientInfo(String patientId) {
        // PatientDetails patientDetails = patientDetailsRepository.findByPatientId(patientId);
        PatientRegister patientRegister = patientRepository.findByPatientId(patientId);

        // if (patientDetails == null || patientRegister == null) {
        //     throw new RuntimeException("Patient not found");
        // }

        return patientRegister;
    }


}
