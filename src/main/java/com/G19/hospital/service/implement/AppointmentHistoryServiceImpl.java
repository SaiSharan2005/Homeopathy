package com.G19.hospital.service.implement;
import java.util.Optional;

import com.G19.hospital.DTO.AppointmentHistoryDTO;
import com.G19.hospital.model.AppointmentHistory;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.Staff;
import com.G19.hospital.repository.AppointmentHistoryRepository;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.repository.DoctorAuthenticationRepository;
import com.G19.hospital.repository.StaffRepository;

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
    private DoctorAuthenticationRepository doctorAuthenticationRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public AppointmentHistory addAppointmentHistory(AppointmentHistoryDTO appointmentHistoryDTO) {
        BookingAppointment bookingAppointment = bookingAppointmentRepository.findById(appointmentHistoryDTO.getBookingId()).orElseThrow();
        // DoctorRegister doctor = doctorAuthenticationRepository.findById(appointmentHistoryDTO.getDoctorId());
        Optional<DoctorRegister> optionalDoctor = doctorAuthenticationRepository.findById(appointmentHistoryDTO.getDoctorId());
        DoctorRegister doctor = optionalDoctor.get();

        
        Staff admin = staffRepository.findById(appointmentHistoryDTO.getAdminId()).orElseThrow();

        AppointmentHistory appointmentHistory = new AppointmentHistory();
        appointmentHistory.setBookingAppointment(bookingAppointment);
        appointmentHistory.setDoctorId(doctor);
        appointmentHistory.setAdminId(admin);
        appointmentHistory.setRole(appointmentHistoryDTO.getRole());
        appointmentHistory.setAction(appointmentHistoryDTO.getAction());
        appointmentHistory.setReasonForAction(appointmentHistoryDTO.getReasonForAction());

        return appointmentHistoryRepository.save(appointmentHistory);
    }

    @Override
    public List<AppointmentHistory> getAllAppointmentHistories() {
        return appointmentHistoryRepository.findAll();
    }
}
