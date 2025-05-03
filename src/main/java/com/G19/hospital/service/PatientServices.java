package com.G19.hospital.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.G19.hospital.DTO.PatientDetailsDTO;
import com.G19.hospital.DTO.PatientRegisterDTO;
import com.G19.hospital.model.PatientDetails;
import com.G19.hospital.model.User;

public interface PatientServices {
    User registerPatient(PatientRegisterDTO patientRegisterDTO) throws Exception;
    User loginPatient(String phoneNumber, String password) throws Exception;
    PatientDetails profilePatient(PatientDetailsDTO patientDetailsDTO ) throws Exception;
    User getPatientInfo(String patientId) throws Exception;
    Page<User> searchPatients(String keyword, Pageable pageable);
    long getPatientCount() throws Exception;
    User getPatientInfoByUserName(String phoneNumber) throws Exception;
    List<User> getAllPatients() throws Exception;
    Page<User> getAllPatients(Pageable pageable);

    PatientDetails updatePatientProfile(Long id, PatientDetailsDTO patientDetailsDTO) throws Exception;

    
    }
