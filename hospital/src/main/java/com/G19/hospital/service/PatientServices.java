
package com.G19.hospital.service;

import com.G19.hospital.DTOs.PatientDetailsDTO;
import com.G19.hospital.DTOs.PatientRegisterDTO;
import com.G19.hospital.model.Authentication.PatientRegister;
import com.G19.hospital.model.Authentication.PatientDetails;


public interface PatientServices {
    PatientRegister registerPatient(PatientRegisterDTO patientRegisterDTO) throws Exception;
    PatientRegister loginPatient(String phoneNumber, String password) throws Exception;
    PatientDetails profilePatient(PatientDetailsDTO patientDetailsDTO ) throws Exception;
    PatientRegister  getPatientInfo(String patientId) throws Exception;
}
