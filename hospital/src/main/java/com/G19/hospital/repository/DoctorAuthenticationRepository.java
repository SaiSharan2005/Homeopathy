package com.G19.hospital.repository;
import java.util.List;
import com.G19.hospital.model.Authentication.DoctorRegister;
import org.springframework.data.jpa.repository.JpaRepository;

// Create a separate interface for DoctorRegister
public interface DoctorAuthenticationRepository extends JpaRepository<DoctorRegister, Long> {
    DoctorRegister findByPhoneNumber(String phoneNumber);
    DoctorRegister findByDoctorId(String doctorId);
    List<DoctorRegister> findAll();
}