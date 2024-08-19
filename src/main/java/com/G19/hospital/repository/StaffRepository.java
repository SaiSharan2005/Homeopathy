package com.G19.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByPhoneNumber(String phoneNumber);
}
