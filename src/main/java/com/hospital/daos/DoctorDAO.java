package com.hospital.daos;
import com.hospital.pojos.Doctor;
import com.hospital.utils.DatabaseConnector;
import com.hospital.utils.DoctorSupplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorDAO extends GenericDAO{
    public boolean addDoctorToDB(Doctor doctor){
        String sql = "INSERT INTO doctors (name,last_name,major,gender) VALUES (?,?,?,?)";
        return executeUpdate(sql,
                doctor.getName(),
                doctor.getLastName(),
                doctor.getMajor().toString(),
                doctor.getGender().toString());
    }


    public boolean updateDoctorName(Doctor doctor, String newName){
        String sql = "UPDATE doctors SET name = ? WHERE id = ?";
        return executeUpdate(sql, newName, doctor.getId());
    }


    public boolean deleteDoctorById(int id){
        String sql = "DELETE FROM doctors WHERE id = ?";
        return executeUpdate(sql, id);
    }


    public Optional<Doctor> getDoctorByName(String doctorName){
        String sql = "SELECT * FROM doctors WHERE name = ?";
        return executeQuerySingle(sql, DoctorSupplier::getDoctorViaResultSet, doctorName);
    }

    public Optional<Doctor> getDoctorById(int id){
        String sql = "SELECT * FROM doctors WHERE id = ?";
        return executeQuerySingle(sql, DoctorSupplier::getDoctorViaResultSet, id);
    }


    public List<Doctor> getDoctorsByMajor(String major){
        String sql = "SELECT * FROM doctors WHERE major = ?";
        return executeQueryList(sql, DoctorSupplier::getDoctorViaResultSet, major);
    }

    public List<Doctor> getListOfDoctors(){
        String sql = "SELECT * FROM doctors";
        return executeQueryList(sql, DoctorSupplier::getDoctorViaResultSet);
    }
}
