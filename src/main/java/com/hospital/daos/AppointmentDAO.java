package com.hospital.daos;
import com.hospital.DTOs.PatientAppointmentDTO;
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

    public boolean isDoctorBusy(LocalTime time, LocalDate date, int doctor_id){
        String sql = "SELECT * FROM appointments WHERE appointment_time = ? AND appointment_date = ? AND doctor_id = ?";
        return executeQuerySingle(sql, AppointmentSupplier::getAppointmentViaResultSet,time, date ,doctor_id).isPresent();
    }


    public boolean deleteAppointment(int id){
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        return executeUpdate(sql, id);
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

    public boolean isPatientBusy(LocalTime time, LocalDate date, int patient_id) {
        String sql = "SELECT * FROM appointments WHERE appointment_time = ? AND appointment_date = ? AND patient_id = ?";
        return executeQuerySingle(sql, AppointmentSupplier::getAppointmentViaResultSet,
                Time.valueOf(time),
                Date.valueOf(date),
                patient_id).isPresent();
    }


    public List<PatientAppointmentDTO> getAppointmentsWithDoctorInfo(int patientId) {
        String sql = "SELECT a.appointment_id, a.appointment_date, a.appointment_time, " +
                "d.name, d.last_name, d.major " +
                "FROM appointments a " +
                "JOIN doctors d ON a.doctor_id = d.id " +
                "WHERE a.patient_id = ?";

        return executeQueryList(sql, rs -> new PatientAppointmentDTO(
                rs.getInt("appointment_id"),
                rs.getDate("appointment_date").toLocalDate(),
                rs.getTime("appointment_time").toLocalTime(),
                rs.getString("name"),
                rs.getString("last_name"),
                rs.getString("major")
        ), patientId);
    }

    public List<Appointment> getAppointmentsByDoctorId(int doctorId){
        String sql = "SELECT * FROM appointments WHERE doctor_id = ? ORDER BY appointment_date ASC, appointment_time ASC";
        return executeQueryList(sql, AppointmentSupplier::getAppointmentViaResultSet, doctorId);
    }

    public boolean isDoctorLocked(Connection con, int doctorId, LocalDate date, LocalTime time) throws SQLException {
        String sql = "SELECT 1 FROM appointments WHERE doctor_id = ? AND appointment_date = ? AND appointment_time = ? FOR UPDATE";
        return executeExistsWithConnection(con, sql,
                doctorId,
                Date.valueOf(date),
                Time.valueOf(time)
        );
    }

    public boolean isPatientLocked(Connection con, int patientId, LocalDate date, LocalTime time) throws SQLException {
        String sql = "SELECT 1 FROM appointments WHERE patient_id = ? AND appointment_date = ? AND appointment_time = ?";
        return executeExistsWithConnection(con, sql,
                patientId,
                Date.valueOf(date),
                Time.valueOf(time)
        );
    }

    public void insertAppointmentLocked(Connection con, Appointment appt) throws SQLException {
        String sql = "INSERT INTO appointments (appointment_date, appointment_time, patient_id, doctor_id) VALUES (?,?,?,?)";

        executeUpdateWithConnection(con, sql,
                Date.valueOf(appt.getAppointmentDate()),
                Time.valueOf(appt.getAppointmentTime()),
                appt.getPatient_id(),
                appt.getDoctor_id()
        );
    }
}
