package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.AppointmentHistoryDTO;
import com.G19.hospital.model.AppointmentHistory;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User;  // Importing User model
import com.G19.hospital.repository.AppointmentHistoryRepository;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.AppointmentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentHistoryServiceImpl implements AppointmentHistoryService {

    @Autowired
    private AppointmentHistoryRepository appointmentHistoryRepository;

    @Autowired
    private BookingAppointmentRepository bookingAppointmentRepository;

    @Autowired
    private UserRepository userRepository;  
    @Override
    public AppointmentHistory addAppointmentHistory(AppointmentHistoryDTO appointmentHistoryDTO) {
        // Retrieve the booking appointment
        BookingAppointment bookingAppointment = bookingAppointmentRepository.findById(appointmentHistoryDTO.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking appointment not found for ID: " + appointmentHistoryDTO.getBookingId()));
        
        // Retrieve the user (previously DoctorRegister)
        User user = userRepository.findById(appointmentHistoryDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("User not found for ID: " + appointmentHistoryDTO.getDoctorId()));
        
        // Retrieve the admin
        User admin = userRepository.findById(appointmentHistoryDTO.getAdminId())
                .orElseThrow(() -> new RuntimeException("Staff not found for ID: " + appointmentHistoryDTO.getAdminId()));

        // Create and populate the AppointmentHistory entity
        AppointmentHistory appointmentHistory = new AppointmentHistory();
        appointmentHistory.setBookingAppointment(bookingAppointment);
        appointmentHistory.setDoctor(user);  // Set the user instead of doctor
        appointmentHistory.setAdminId(admin);
        appointmentHistory.setRole(appointmentHistoryDTO.getRole());
        appointmentHistory.setAction(appointmentHistoryDTO.getAction());
        appointmentHistory.setReasonForAction(appointmentHistoryDTO.getReasonForAction());

        // Save and return the appointment history
        return appointmentHistoryRepository.save(appointmentHistory);
    }

    @Override
    public List<AppointmentHistory> getAllAppointmentHistories() {
        return appointmentHistoryRepository.findAll();
    }
}
