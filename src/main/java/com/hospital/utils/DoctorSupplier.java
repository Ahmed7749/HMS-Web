package com.hospital.utils;
import com.hospital.Enums.Genders;
import com.hospital.Enums.Majors;
import com.hospital.pojos.Doctor;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorSupplier{
    public static Doctor getDoctorViaResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String lastName = resultSet.getString("last_name");
        String majorString = resultSet.getString("major");
        Majors major = Majors.valueOf(majorString);
        String genderString = resultSet.getString("gender");
        Genders gender = Genders.valueOf(genderString);
        return new Doctor(id, name, lastName, major, gender);
    }
}
