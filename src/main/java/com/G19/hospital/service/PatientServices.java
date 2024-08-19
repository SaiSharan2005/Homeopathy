
package com.G19.hospital.service;

import java.util.List;

import com.G19.hospital.DTO.PatientDetailsDTO;
import com.G19.hospital.DTO.PatientRegisterDTO;
import com.G19.hospital.model.PatientDetails;
import com.G19.hospital.model.PatientRegister;


public interface PatientServices {
    PatientRegister registerPatient(PatientRegisterDTO patientRegisterDTO) throws Exception;
    PatientRegister loginPatient(String phoneNumber, String password) throws Exception;
    PatientDetails profilePatient(PatientDetailsDTO patientDetailsDTO ) throws Exception;
    PatientRegister  getPatientInfo(String patientId) throws Exception;
    List<PatientRegister> searchPatients(String keyword) throws Exception;
        long getPatientCount() throws Exception ;

}
