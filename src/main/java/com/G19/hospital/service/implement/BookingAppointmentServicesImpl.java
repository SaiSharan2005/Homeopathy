package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.BookingAppointmentDTO;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.service.BookingAppointmentServices;
import com.G19.hospital.service.DoctorScheduleServices;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@Service
public class BookingAppointmentServicesImpl implements BookingAppointmentServices {

    @Autowired
    private BookingAppointmentRepository bookingAppointmentRepository;

    @Autowired
    private DoctorScheduleServices doctorScheduleServices;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public BookingAppointment createBookingAppointment(BookingAppointmentDTO bookingAppointmentDTO) throws Exception {
        DoctorSchedule schedule = doctorScheduleServices.getScheduleById(bookingAppointmentDTO.getScheduleId());
        User doctor = new User();
        doctor.setId(bookingAppointmentDTO.getDoctorId());
        User patient = new User();
        patient.setId(bookingAppointmentDTO.getPatientId());

        BookingAppointment bookingAppointment = new BookingAppointment();
        bookingAppointment.setDoctor(doctor);
        bookingAppointment.setPatient(patient);
        bookingAppointment.setScheduleId(schedule);
        bookingAppointment.setAppointmentDate(schedule.getDate());
        bookingAppointment.setStatus(bookingAppointmentDTO.getStatus());
        bookingAppointment = bookingAppointmentRepository.save(bookingAppointment);
        
        // Generate the token
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDate = now.format(formatter);
        String uniqueToken = formattedDate + "-" + bookingAppointment.getBookingId();
        bookingAppointment.setToken(uniqueToken);

        doctorScheduleServices.bookSlot(schedule.getScheduleId());

        return bookingAppointment;
    }

    @Override
    public BookingAppointment updateBookingAppointment(Long bookingId, BookingAppointmentDTO bookingAppointmentDTO) throws Exception {
        BookingAppointment existingBookingAppointment = bookingAppointmentRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking appointment not found"));

        DoctorSchedule schedule = doctorScheduleServices.getScheduleById(bookingAppointmentDTO.getScheduleId());
        doctorScheduleServices.cancelSlot(existingBookingAppointment.getScheduleId().getScheduleId());
        doctorScheduleServices.bookSlot(schedule.getScheduleId());

        existingBookingAppointment.setScheduleId(schedule);

        User doctor = new User();
        doctor.setId(bookingAppointmentDTO.getDoctorId());
        User patient = new User();
        patient.setId(bookingAppointmentDTO.getPatientId());

        existingBookingAppointment.setDoctor(doctor);
        existingBookingAppointment.setPatient(patient);
        existingBookingAppointment.setStatus(bookingAppointmentDTO.getStatus());

        return bookingAppointmentRepository.save(existingBookingAppointment);
    }

    @Override
    public BookingAppointment completedAppointment(String tokenId) throws Exception {
        BookingAppointment existingBookingAppointment = bookingAppointmentRepository.findByToken(tokenId)
                .orElseThrow(() -> new Exception("Booking appointment not found"));
        existingBookingAppointment.setStatus("completed");
        return bookingAppointmentRepository.save(existingBookingAppointment);
    }
    
    @Override
    public void cancelBookingAppointment(Long bookingId) throws Exception {
        BookingAppointment bookingAppointment = bookingAppointmentRepository.findByBookingId(bookingId);
        bookingAppointment.setStatus("cancel");
        DoctorSchedule schedule = bookingAppointment.getScheduleId();
        doctorScheduleServices.cancelSlot(schedule.getScheduleId());
        bookingAppointmentRepository.save(bookingAppointment);
    }
    
    @Override
    public List<BookingAppointment> getAllBookingAppointments() {
        return bookingAppointmentRepository.findAll();
    }

    @Override
    public BookingAppointment getBookingAppointmentById(Long bookingId) throws Exception {
        return bookingAppointmentRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking appointment not found"));
    }

    @Override
    public List<BookingAppointment> getBookingsByDoctorId(User doctorId) {
        return bookingAppointmentRepository.findByDoctor(doctorId);
    }

    @Override
    public List<BookingAppointment> getBookingsByPatientId(User patientId) {
        return bookingAppointmentRepository.findByPatient(patientId);
    }

    @Override
    public List<BookingAppointment> getBookingsByScheduleId(DoctorSchedule scheduleId) {
        return bookingAppointmentRepository.findBySchedule(scheduleId);
    }

    @Override
    public Optional<BookingAppointment> getBookingByToken(String token) {
        return bookingAppointmentRepository.findByToken(token);
    }

    @Override
    public long getAppointmentCount() {
        return bookingAppointmentRepository.countByAppointDate(LocalDate.now());
    }

    // New method to update/add a prescription image to an existing booking appointment
    @Override
    public BookingAppointment updatePrescriptionImage(Long bookingId, MultipartFile file) throws Exception {
        BookingAppointment bookingAppointment = bookingAppointmentRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking appointment not found"));
        if (file != null && !file.isEmpty()) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = uploadResult.get("secure_url").toString();
                bookingAppointment.setPrescriptionImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image to Cloudinary", e);
            }
            bookingAppointment = bookingAppointmentRepository.save(bookingAppointment);
        }
        return bookingAppointment;
    }
}
