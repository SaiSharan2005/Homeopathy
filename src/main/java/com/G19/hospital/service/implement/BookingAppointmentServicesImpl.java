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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookingAppointmentServicesImpl implements BookingAppointmentServices {

    @Autowired
    private BookingAppointmentRepository bookingAppointmentRepository;

    @Autowired
    private DoctorScheduleServices doctorScheduleServices;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public BookingAppointment createBookingAppointment(BookingAppointmentDTO dto) throws Exception {
        DoctorSchedule schedule = doctorScheduleServices.getScheduleById(dto.getScheduleId());
        User doctor = new User(); doctor.setId(dto.getDoctorId());
        User patient = new User(); patient.setId(dto.getPatientId());

        BookingAppointment ba = new BookingAppointment();
        ba.setDoctor(doctor);
        ba.setPatient(patient);
        ba.setScheduleId(schedule);
        ba.setAppointmentDate(schedule.getDate());
        ba.setStatus(dto.getStatus());
        ba = bookingAppointmentRepository.save(ba);

        // token
        String timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        ba.setToken(timestamp + "-" + ba.getBookingId());
        doctorScheduleServices.bookSlot(schedule.getScheduleId());
        return bookingAppointmentRepository.save(ba);
    }

    @Override
    public BookingAppointment updateBookingAppointment(Long id, BookingAppointmentDTO dto) throws Exception {
        BookingAppointment existing = bookingAppointmentRepository.findById(id)
            .orElseThrow(() -> new Exception("Booking not found"));

        // unbook old slot / book new
        doctorScheduleServices.cancelSlot(existing.getScheduleId().getScheduleId());
        DoctorSchedule newSched = doctorScheduleServices.getScheduleById(dto.getScheduleId());
        doctorScheduleServices.bookSlot(newSched.getScheduleId());
        existing.setScheduleId(newSched);

        User doctor = new User(); doctor.setId(dto.getDoctorId());
        User patient = new User(); patient.setId(dto.getPatientId());
        existing.setDoctor(doctor);
        existing.setPatient(patient);
        existing.setStatus(dto.getStatus());

        return bookingAppointmentRepository.save(existing);
    }

    @Override
    public BookingAppointment completedAppointment(String token) throws Exception {
        BookingAppointment ba = bookingAppointmentRepository.findByToken(token)
            .orElseThrow(() -> new Exception("Booking not found"));
        ba.setStatus("completed");
        return bookingAppointmentRepository.save(ba);
    }

    @Override
    public void cancelBookingAppointment(Long id) throws Exception {
        BookingAppointment ba = bookingAppointmentRepository.findByBookingId(id);
        ba.setStatus("cancel");
        doctorScheduleServices.cancelSlot(ba.getScheduleId().getScheduleId());
        bookingAppointmentRepository.save(ba);
    }

    // plain list
    @Override
    public List<BookingAppointment> getAllBookingAppointments() {
        return bookingAppointmentRepository.findAll();
    }
    // paged
    @Override
    public Page<BookingAppointment> getAllBookingAppointments(Pageable pageable) {
        return bookingAppointmentRepository.findAll(pageable);
    }

    @Override
    public BookingAppointment getBookingAppointmentById(Long id) throws Exception {
        return bookingAppointmentRepository.findById(id)
            .orElseThrow(() -> new Exception("Booking not found"));
    }

    // by doctor
    @Override
    public List<BookingAppointment> getBookingsByDoctorId(User doctor) {
        return bookingAppointmentRepository.findByDoctor(doctor);
    }
    @Override
    public Page<BookingAppointment> getBookingsByDoctorId(User doctor, Pageable pageable) {
        return bookingAppointmentRepository.findByDoctor(doctor, pageable);
    }

    // by patient
    @Override
    public List<BookingAppointment> getBookingsByPatientId(User patient) {
        return bookingAppointmentRepository.findByPatient(patient);
    }
    @Override
    public Page<BookingAppointment> getBookingsByPatientId(User patient, Pageable pageable) {
        return bookingAppointmentRepository.findByPatient(patient, pageable);
    }

    // by schedule
    @Override
    public List<BookingAppointment> getBookingsByScheduleId(DoctorSchedule schedule) {
        return bookingAppointmentRepository.findBySchedule(schedule);
    }
    @Override
    public Page<BookingAppointment> getBookingsByScheduleId(DoctorSchedule schedule, Pageable pageable) {
        return bookingAppointmentRepository.findBySchedule(schedule, pageable);
    }

    @Override
    public Optional<BookingAppointment> getBookingByToken(String token) {
        return bookingAppointmentRepository.findByToken(token);
    }

    @Override
    public long getAppointmentCount() {
        return bookingAppointmentRepository.countByAppointDate(LocalDate.now());
    }

    @Override
    public BookingAppointment updatePrescriptionImage(Long id, MultipartFile file) throws Exception {
        BookingAppointment ba = bookingAppointmentRepository.findById(id)
            .orElseThrow(() -> new Exception("Booking not found"));
        if (file != null && !file.isEmpty()) {
            Map<?,?> res = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            ba.setPrescriptionImageUrl(res.get("secure_url").toString());
            ba = bookingAppointmentRepository.save(ba);
        }
        return ba;
    }
}
