package com.G19.hospital.service;

import com.G19.hospital.DTOs.DoctorDetailsDTO;
import com.G19.hospital.DTOs.DoctorRegisterDTO;
import com.G19.hospital.model.Authentication.DoctorDetails;
import com.G19.hospital.model.Authentication.DoctorRegister;

import java.util.*;
public interface DoctorServices{
    
    DoctorRegister registerDoctor(DoctorRegister doctorRegisterDTO) throws Exception;
    DoctorRegister loginDoctor(String phoneNumber, String password) throws Exception;
    DoctorDetails profileDoctor(DoctorDetailsDTO doctorDetailsDTO) throws Exception;
    DoctorRegister getDoctorByDoctorId(String doctorId) throws Exception;
    List<DoctorRegister> getAllDoctors() throws Exception;

}
