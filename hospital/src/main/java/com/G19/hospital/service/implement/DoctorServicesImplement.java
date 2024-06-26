package com.G19.hospital.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.G19.hospital.DTOs.DoctorRegisterDTO;
import com.G19.hospital.DTOs.DoctorDetailsDTO;
import com.G19.hospital.model.Authentication.DoctorDetails;
import com.G19.hospital.model.Authentication.DoctorRegister;
import com.G19.hospital.repository.DoctorAuthenticationRepository;
import com.G19.hospital.repository.DoctorDetailsRepository;
import com.G19.hospital.service.DoctorServices;
@Service
public class DoctorServicesImplement implements DoctorServices  {


    @Autowired
    private DoctorAuthenticationRepository doctorRepository;

    @Override
    public DoctorRegister registerDoctor(DoctorRegisterDTO doctorRegisterDTO) throws Exception {
        if (doctorRepository.findByPhoneNumber(doctorRegisterDTO.getPhoneNumber()) != null) {
            throw new Exception("Phone number already in use");
        }

        DoctorRegister patientRegister = new DoctorRegister();
        patientRegister.setDoctorName(doctorRegisterDTO.getDoctorName());
        patientRegister.setPhoneNumber(doctorRegisterDTO.getPhoneNumber());
        patientRegister.setPassword(doctorRegisterDTO.getPassword());
        patientRegister.setEmail(doctorRegisterDTO.getEmail());
         // Set patientId based on the specified logic
         String firstNamePart = doctorRegisterDTO.getDoctorName().substring(0, Math.min(doctorRegisterDTO.getDoctorName().length(), 4));
         String lastNamePart = doctorRegisterDTO.getPhoneNumber().substring(Math.max(doctorRegisterDTO.getPhoneNumber().length() - 4, 0));
 
         patientRegister.setDoctorId("D29"+firstNamePart + lastNamePart);

        return doctorRepository.save(patientRegister);
    }

    @Override
    public DoctorRegister loginDoctor(String phoneNumber, String password) throws Exception {
        DoctorRegister patient = doctorRepository.findByPhoneNumber(phoneNumber);
        if (patient == null || !patient.getPassword().equals(password)) {
            throw new Exception("Invalid phone number or password");
        }
        return patient;
    }

    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;
    @Override
    public DoctorDetails profileDoctor(DoctorDetailsDTO doctorDetailsDTO) throws Exception {

        DoctorDetails doctorDetails = new DoctorDetails();
        doctorDetails.setAge(doctorDetailsDTO.getAge());
        doctorDetails.setGender(doctorDetailsDTO.getGender());
        doctorDetails.setAddress(doctorDetailsDTO.getAddress());
        doctorDetails.setCity(doctorDetailsDTO.getCity());
        doctorDetails.setPincode(doctorDetailsDTO.getPincode());
        doctorDetails.setConsultationFee(doctorDetailsDTO.getConsultationFee());
        doctorDetails.setSpecialization(doctorDetailsDTO.getSpecialization());
        doctorDetails.setRemuneration(doctorDetailsDTO.getRemuneration());
         
        return doctorDetailsRepository.save(doctorDetails);
    }

    @Override
    public DoctorRegister getDoctorById(String doctorId) {
        return doctorRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<DoctorRegister> getAllDoctors() {
        return doctorRepository.findAll();
    }

        


    }
