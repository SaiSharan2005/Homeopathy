package com.G19.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.Authentication.BookingAppointment;


public interface BookingAppointmentRepository extends JpaRepository<BookingAppointment, Long> {
    
}

