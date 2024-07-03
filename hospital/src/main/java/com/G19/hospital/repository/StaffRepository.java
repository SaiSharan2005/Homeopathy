package com.G19.hospital.repository;

import com.G19.hospital.model.Authentication.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByPhoneNumber(String phoneNumber);
}
