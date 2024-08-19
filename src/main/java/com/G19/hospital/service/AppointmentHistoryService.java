package com.G19.hospital.service;

import com.G19.hospital.DTO.AppointmentHistoryDTO;
import com.G19.hospital.model.AppointmentHistory;

import java.util.List;

public interface AppointmentHistoryService {
    AppointmentHistory addAppointmentHistory(AppointmentHistoryDTO appointmentHistoryDTO);
    List<AppointmentHistory> getAllAppointmentHistories();
}
