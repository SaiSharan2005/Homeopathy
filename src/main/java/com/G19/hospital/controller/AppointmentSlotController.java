package com.G19.hospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorSchedule;
// import com.G19.hospital.repository.DoctorAuthenticationRepository;
// import com.G19.hospital.service.BookingAppointmentServices;
// import com.G19.hospital.service.;
// import com.G19.hospital.service.DoctorServices;
import com.G19.hospital.service.DoctorScheduleServices;
import com.G19.hospital.service.DoctorServices;

import java.sql.Date;
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


    @PostMapping("/date/{date}")
    public ResponseEntity<List<DoctorSchedule>> createAppointmentSlots(
        @RequestBody DoctorRegister doctorId,
        @PathVariable LocalDate date) {

    try {
        List<DoctorSchedule> responseEntity = doctorScheduleServices.createScheduleForDate(doctorId, date);
        return ResponseEntity.ok(responseEntity);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null); // Handle error response as needed
    }

}
}