package com.hospital.pojos;


import com.hospital.utils.Validations;

import java.time.LocalDate;
import java.time.LocalTime;
public class Appointment {
    private int id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private int patient_id;
    private int doctor_id;

    public Appointment(int id, LocalDate appointmentDate, LocalTime appointmentTime, int patient_id, int doctor_id) {
        this.id = id;
        this.appointmentDate = Validations.checkNotNull(appointmentDate, "Appointment date cannot be null");
        this.appointmentTime = Validations.checkNotNull(appointmentTime, "Appointment time cannot be null");
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
    }


    public Appointment(LocalDate appointmentDate, LocalTime appointmentTime, int patient_id, int doctor_id) {
        this.appointmentDate = Validations.checkNotNull(appointmentDate, "Appointment date cannot be null");
        this.appointmentTime = Validations.checkNotNull(appointmentTime, "Appointment time cannot be null");
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    @Override
    public String toString(){
        System.out.println("-".repeat(100));
        return "%12s %15s | %12s %16d |\n%12s %15s | %10s %20d | %12s %10d \n%s\n".formatted("Appointment time: ", appointmentTime,"Appointment id: ", id,"Appointment date: ",appointmentDate,"patient id: ",patient_id, "Doctor id: ",doctor_id, "-".repeat(100));
    }
}
