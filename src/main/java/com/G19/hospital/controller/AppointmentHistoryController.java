package com.G19.hospital.controller;

import com.G19.hospital.DTO.AppointmentHistoryDTO;
import com.G19.hospital.model.AppointmentHistory;
import com.G19.hospital.service.AppointmentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointmentHistory")
public class AppointmentHistoryController {

    @Autowired
    private AppointmentHistoryService appointmentHistoryService;

    @PostMapping("/add")
    public ResponseEntity<AppointmentHistory> addAppointmentHistory(@RequestBody AppointmentHistoryDTO appointmentHistoryDTO) {
        AppointmentHistory appointmentHistory = appointmentHistoryService.addAppointmentHistory(appointmentHistoryDTO);
        return ResponseEntity.ok(appointmentHistory);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentHistory>> getAllAppointmentHistories() {
        List<AppointmentHistory> appointmentHistories = appointmentHistoryService.getAllAppointmentHistories();
        return ResponseEntity.ok(appointmentHistories);
    }
}



