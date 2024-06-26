package com.G19.hospital.controller.Authentication;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.G19.hospital.model.Authentication.DoctorRegister;
// import com.G19.hospital.repository.DoctorAuthenticationRepository;
// import com.G19.hospital.service.BookingAppointmentServices;
// import com.G19.hospital.service.;
// import com.G19.hospital.service.DoctorServices;
import com.G19.hospital.service.DoctorScheduleServices;
import com.G19.hospital.service.DoctorServices;

import java.time.LocalDate;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import java.time.LocalDate;
import java.time.LocalTime;
// import java.util.List;

@RestController
@RequestMapping("/create-appointment-slots")
public class AppointmentSlotController {

    @Autowired
    public DoctorScheduleServices doctorScheduleServices;

    @Autowired
    public DoctorServices doctorServices;


    @PostMapping
    public ResponseEntity<List<LocalTime>> createAppointmentSlots() {
        try {
            List<LocalTime> appointmentSlots = doctorScheduleServices.generateAppointmentSlots();
            List<DoctorRegister> doctors = doctorServices.getAllDoctors();
            for(DoctorRegister doctor:doctors ){
                LocalTime prev = appointmentSlots.get(0);
                for(LocalTime slot:appointmentSlots.subList(1,appointmentSlots.size() )){
                    doctorScheduleServices.createScheduleForDate(doctor,LocalDate.now(),prev,slot,30);
                    prev = slot;
                }                
            }

            return ResponseEntity.ok(appointmentSlots);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Handle error response as needed
        }
    }
}
