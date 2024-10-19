package com.G19.hospital.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.G19.hospital.DTO.PatientDetailsDTO;
import com.G19.hospital.DTO.PatientRegisterDTO;
import com.G19.hospital.model.PatientDetails;
import com.G19.hospital.model.User;

public interface PatientServices {
    User registerPatient(PatientRegisterDTO patientRegisterDTO) throws Exception;
    User loginPatient(String phoneNumber, String password) throws Exception;
    PatientDetails profilePatient(PatientDetailsDTO patientDetailsDTO ) throws Exception;
    User getPatientInfo(String patientId) throws Exception;
    List<User> searchPatients(String keyword) throws Exception;
    long getPatientCount() throws Exception;
    public User getPatientInfoByUserName(String phoneNumber) throws Exception;

}
