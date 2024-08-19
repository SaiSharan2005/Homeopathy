package com.G19.hospital.service;

import com.G19.hospital.DTO.DoctorDetailsDTO;
import com.G19.hospital.DTO.DoctorRegisterDTO;
import com.G19.hospital.model.DoctorDetails;
import com.G19.hospital.model.DoctorRegister;

import java.util.*;
public interface DoctorServices{
    
    DoctorRegister registerDoctor(DoctorRegisterDTO doctorRegisterDTO) throws Exception;
    DoctorRegister loginDoctor(String phoneNumber, String password) throws Exception;
    DoctorDetails profileDoctor(DoctorDetailsDTO doctorDetailsDTO) throws Exception;
    DoctorRegister getDoctorByDoctorId(String doctorId) throws Exception;
    List<DoctorRegister> getAllDoctors() throws Exception;
    List<DoctorRegister> searchDoctors(String keyword) throws Exception;

    long getDoctorCount() throws Exception ;
}
