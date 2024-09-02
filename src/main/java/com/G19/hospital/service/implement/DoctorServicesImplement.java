package com.G19.hospital.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.G19.hospital.DTO.DoctorRegisterDTO;
import com.G19.hospital.DTO.DoctorDetailsDTO;
import com.G19.hospital.model.DoctorDetails;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.repository.DoctorAuthenticationRepository;
import com.G19.hospital.repository.DoctorDetailsRepository;
import com.G19.hospital.service.DoctorServices;

@Service
public class DoctorServicesImplement implements DoctorServices {

    @Autowired
    private DoctorAuthenticationRepository doctorRepository;

    @Override
    public DoctorRegister registerDoctor(DoctorRegisterDTO doctorRegisterDTO) throws Exception {
        if (doctorRepository.findByPhoneNumber(doctorRegisterDTO.getPhoneNumber()) != null) {
            throw new Exception("Phone number already in use");
        }

        DoctorRegister doctorRegister = new DoctorRegister();
        doctorRegister.setDoctorName(doctorRegisterDTO.getDoctorName());
        doctorRegister.setPhoneNumber(doctorRegisterDTO.getPhoneNumber());
        doctorRegister.setPassword(doctorRegisterDTO.getPassword());
        doctorRegister.setEmail(doctorRegisterDTO.getEmail());
        // Set patientId based on the specified logic
        String firstNamePart = doctorRegisterDTO.getDoctorName().substring(0,
                Math.min(doctorRegisterDTO.getDoctorName().length(), 4));
        String lastNamePart = doctorRegisterDTO.getPhoneNumber()
                .substring(Math.max(doctorRegisterDTO.getPhoneNumber().length() - 4, 0));

        doctorRegister.setDoctorId("D29" + firstNamePart + lastNamePart);

        return doctorRepository.save(doctorRegister);
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
        Optional<DoctorRegister> optionalDoctor = doctorRepository.findById(doctorDetailsDTO.getDoctorId());
        DoctorRegister doctorRegister = optionalDoctor.get();
        doctorDetails.setDoctorRegister(doctorRegister);
        return doctorDetailsRepository.save(doctorDetails);
    }

    @Override
    public DoctorRegister getDoctorByDoctorId(String doctorId) {
        return doctorRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<DoctorRegister> getAllDoctors() {
        return doctorRepository.findAll();
    }
    @Override
    public List<DoctorRegister> searchDoctors(String keyword) {
        return doctorRepository.searchDoctors(keyword);
    }

    @Override
    public long getDoctorCount() {
        return doctorRepository.count();
    }
}
// }package com.G19.hospital.service.implement;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.*;

// import com.G19.hospital.DTO.DoctorDetailsDTO;
// import com.G19.hospital.DTO.DoctorRegisterDTO;
// import com.G19.hospital.model.DoctorDetails;
// import com.G19.hospital.model.DoctorRegister;
// import com.G19.hospital.repository.DoctorAuthenticationRepository;
// import com.G19.hospital.repository.DoctorDetailsRepository;
// import com.G19.hospital.service.DoctorServices;

// @Service
// public class DoctorServicesImplement implements DoctorServices {

// @Autowired
// private DoctorAuthenticationRepository doctorRepository;

// @Autowired
// private DoctorDetailsRepository doctorDetailsRepository;

// @Override
// public DoctorRegister registerDoctor(DoctorRegister doctorRegister) throws
// Exception {
// if (doctorRepository.findByPhoneNumber(doctorRegister.getPhoneNumber()) !=
// null) {
// throw new Exception("Phone number already in use");
// }

// // DoctorRegister doctorRegister = new DoctorRegister();
// // doctorRegister.setDoctorName(doctorRegisterDTO.getDoctorName());
// // doctorRegister.setPhoneNumber(doctorRegisterDTO.getPhoneNumber());
// // doctorRegister.setPassword(doctorRegisterDTO.getPassword());
// // doctorRegister.setEmail(doctorRegisterDTO.getEmail());
// String firstNamePart = doctorRegister.getDoctorName().substring(0,
// Math.min(doctorRegister.getDoctorName().length(), 4));
// String lastNamePart =
// doctorRegister.getPhoneNumber().substring(Math.max(doctorRegister.getPhoneNumber().length()
// - 4, 0));
// doctorRegister.setDoctorId("D29"+firstNamePart + lastNamePart);
// // doctorRepository.save(doctorRegister);

// // DoctorDetails doctorDetails = new DoctorDetails();
// // doctorDetails = doctorRegister.getDoctorDetails();
// // doctorDetails.setDoctorRegister(doctorRegister2);
// // // doctorRegister.setDoctorDetails(doctorRegister.getDoctorDetails());
// // doctorDetailsRepository.save(doctorDetails);

// return doctorRepository.save(doctorRegister);
// }

// @Override
// public DoctorRegister loginDoctor(String phoneNumber, String password) throws
// Exception {
// DoctorRegister doctor = doctorRepository.findByPhoneNumber(phoneNumber);
// if (doctor == null || !doctor.getPassword().equals(password)) {
// throw new Exception("Invalid phone number or password");
// }
// return doctor;
// }

// @Override
// public DoctorDetails profileDoctor(DoctorDetailsDTO doctorDetailsDTO) throws
// Exception {
// DoctorRegister doctorRegister =
// doctorRepository.findById(doctorDetailsDTO.getDoctorId())
// .orElseThrow(() -> new Exception("Doctor not found"));

// DoctorDetails doctorDetails = new DoctorDetails();
// doctorDetails.setAge(doctorDetailsDTO.getAge());
// doctorDetails.setGender(doctorDetailsDTO.getGender());
// doctorDetails.setAddress(doctorDetailsDTO.getAddress());
// doctorDetails.setCity(doctorDetailsDTO.getCity());
// doctorDetails.setPincode(doctorDetailsDTO.getPincode());
// doctorDetails.setConsultationFee(doctorDetailsDTO.getConsultationFee());
// doctorDetails.setSpecialization(doctorDetailsDTO.getSpecialization());
// doctorDetails.setRemuneration(doctorDetailsDTO.getRemuneration());
// doctorDetails.setDoctorRegister(doctorRegister);

// return doctorDetailsRepository.save(doctorDetails);
// }

// @Override
// public DoctorRegister getDoctorByDoctorId(String doctorId) {
// return doctorRepository.findByDoctorId(doctorId);
// }

// @Override
// public List<DoctorRegister> getAllDoctors() {
// return doctorRepository.findAll();
// }
// }
