package com.G19.hospital.controller;

import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.service.BookingAppointmentServices;
import com.G19.hospital.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sendEmail")
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private BookingAppointmentServices bookingService;

    @GetMapping
    public String sendEmail(@RequestParam String token) {
        // 1) Look up the appointment by token
        BookingAppointment appointment = bookingService
            .getBookingByToken(token)
            .orElseThrow(() -> new RuntimeException("No appointment with token " + token));

        // 2) Extract all of the fields you want in the email
        Map<String, Object> props = new HashMap<>();
        props.put("token",       appointment.getToken());
        props.put("date",        appointment.getAppointmenDate());
        props.put("status",      appointment.getStatus());
        props.put("doctorName",  appointment.getDoctor().getUsername());
        props.put("patientName", appointment.getPatient().getUsername());
        // …or any other doctorDetails/patientDetails fields…

        try {
            emailService.sendHtmlEmail(
                appointment.getPatient().getEmail(),
                "Appointment Confirmation – Homeopathy Clinic",
                props
            );
            return "Email sent successfully to " + appointment.getPatient().getEmail();
        } catch (MessagingException e) {
            // use a real logger in production!
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
}
