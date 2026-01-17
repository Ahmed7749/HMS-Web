package com.hospital.utils;

import com.hospital.Enums.Genders;
import com.hospital.pojos.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PatientSupplier {
    public static Patient getPatientViaResultSet(ResultSet resultSet) throws SQLException {
        return new Patient(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                Genders.valueOf(resultSet.getString("gender").toUpperCase()),
                resultSet.getObject("birth_date", LocalDate.class),
                resultSet.getString("middle_name"),
                resultSet.getString("last_name"));
    }
}
