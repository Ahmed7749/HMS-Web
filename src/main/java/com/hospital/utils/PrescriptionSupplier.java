package com.hospital.utils;
import com.hospital.pojos.Prescription;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PrescriptionSupplier{
    public static Prescription getPrescriptionViaResultSet(ResultSet resultSet) throws SQLException {
        return new Prescription(
                resultSet.getInt("id"),
                resultSet.getString("medication_name"),
                resultSet.getString("dosage"),
                resultSet.getString("instructions"),
                resultSet.getInt("doctor_id"),
                resultSet.getInt("patient_id")
        );
    }
}
