package com.G19.hospital.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DailyAppointmentSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int totalAppointments;

    @Column(nullable = false)
    private int missedAppointments;

    @Column(nullable = false)
    private int completedAppointments;

    @Column(nullable = false)
    private int canceledAppointments;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public int getMissedAppointments() {
        return missedAppointments;
    }

    public void setMissedAppointments(int missedAppointments) {
        this.missedAppointments = missedAppointments;
    }

    public int getCompletedAppointments() {
        return completedAppointments;
    }

    public void setCompletedAppointments(int completedAppointments) {
        this.completedAppointments = completedAppointments;
    }

    public int getCanceledAppointments() {
        return canceledAppointments;
    }

    public void setCanceledAppointments(int canceledAppointments) {
        this.canceledAppointments = canceledAppointments;
    }
}
