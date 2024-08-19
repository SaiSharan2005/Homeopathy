package com.G19.hospital.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.G19.hospital.model.DoctorRegister;

// Create a separate interface for DoctorRegister
public interface DoctorAuthenticationRepository extends JpaRepository<DoctorRegister, Long> {
    DoctorRegister findByPhoneNumber(String phoneNumber);
    DoctorRegister findByDoctorId(String doctorId);
    // DoctorRegister findById(Long id);
    List<DoctorRegister> findAll();
    long count();
    @Query("SELECT d FROM DoctorRegister d WHERE " +
    "LOWER(d.doctorName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    "LOWER(d.doctorDetails.specialization) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    "LOWER(d.doctorId) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<DoctorRegister> searchDoctors(@Param("keyword") String keyword);


}
