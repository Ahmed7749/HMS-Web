package com.hospital.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;

public class PatientAppointmentDTO {
    private int appointmentId;
    private LocalDate date;
    private LocalTime time;
    private String doctorName;
    private String doctorLastName;
    private String doctorMajor;
    public PatientAppointmentDTO(int id, LocalDate date, LocalTime time, String name, String lastName, String major) {
        this.appointmentId = id;
        this.date = date;
        this.time = time;
        this.doctorName = name;
        this.doctorLastName = lastName;
        this.doctorMajor = major;
    }
    public int getAppointmentId() { return appointmentId; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getDoctorName() { return doctorName; }
    public String getDoctorLastName() { return doctorLastName; }
    public String getDoctorMajor() { return doctorMajor; }
}