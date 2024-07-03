package com.G19.hospital.service;

import com.G19.hospital.DTOs.AppointmentHistoryDTO;
import com.G19.hospital.model.Authentication.AppointmentHistory;

import java.util.List;

public interface AppointmentHistoryService {
    AppointmentHistory addAppointmentHistory(AppointmentHistoryDTO appointmentHistoryDTO);
    List<AppointmentHistory> getAllAppointmentHistories();
}
