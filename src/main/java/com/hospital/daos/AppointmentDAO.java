package com.hospital.daos;
import com.hospital.pojos.Appointment;
import com.hospital.utils.AppointmentSupplier;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class AppointmentDAO extends GenericDAO{
    public boolean bookAppointment(Appointment appointment){
        String sql = "INSERT INTO appointments (appointment_date, appointment_time,patient_id,doctor_id) VALUES (?,?,?,?)";
        return executeUpdate(sql,
                Date.valueOf(appointment.getAppointmentDate()),
                Time.valueOf(appointment.getAppointmentTime()),
                appointment.getPatient_id(),
                appointment.getDoctor_id());
    }

    public Optional<Appointment> getAppointmentById(int id){
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";
        return executeQuerySingle(sql, AppointmentSupplier::getAppointmentViaResultSet, id);
    }

    public List<Appointment> getAppointmentsByPatientId(int patientId){
        String sql = "SELECT * FROM appointments WHERE patient_id = ?";
        return executeQueryList(sql, AppointmentSupplier::getAppointmentViaResultSet, patientId);
    }

    public boolean isBooked(LocalTime time,LocalDate date,int patient_id, int doctor_id){
        String sql = "SELECT * FROM appointments WHERE appointment_time = ? AND patient_id = ? AND appointment_date = ? AND doctor_id = ?";
        return executeQuerySingle(sql, AppointmentSupplier::getAppointmentViaResultSet,time, patient_id, date ,doctor_id).isPresent();
    }


    public boolean deleteAppointment(Appointment appointment){
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        return executeUpdate(sql, appointment.getId());
    }


    public boolean updateAppointmentTime(Appointment appointment,LocalTime newTime){
        String sql = "UPDATE appointments SET appointment_time = ? WHERE appointment_id = ?";
        return executeUpdate(sql, newTime, appointment.getId());
    }


    public boolean updateAppointmentDate(Appointment appointment, LocalDate newDate){
        String sql = "UPDATE appointments SET appointment_date = ? WHERE appointment_id = ?";
        return executeUpdate(sql, newDate, appointment.getId());
    }


    public List<Appointment> getListOfAppointments(){
        String sql = "SELECT * FROM appointments";
        return executeQueryList(sql, AppointmentSupplier::getAppointmentViaResultSet);
    }
}
