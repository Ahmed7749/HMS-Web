package com.hospital.utils;

import com.hospital.pojos.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentSupplier {
    public static Appointment getAppointmentViaResultSet(ResultSet resultSet) throws SQLException {
        return new Appointment(
                resultSet.getInt("appointment_id"),
                resultSet.getObject("appointment_date", LocalDate.class),
                resultSet.getObject("appointment_time", LocalTime.class),
                resultSet.getInt("patient_id"),
                resultSet.getInt("doctor_id")
        );
    }
}
