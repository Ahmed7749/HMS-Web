package com.hospital.daos;
import com.hospital.pojos.Doctor;
import com.hospital.utils.DatabaseConnector;
import com.hospital.utils.DoctorSupplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DoctorDAO extends GenericDAO{
    public boolean addDoctorToDB(Doctor doctor){
        String sql = "INSERT INTO doctors (name,last_name,major,gender,user_id) VALUES (?,?,?,?,?)";
        return executeUpdate(sql,
                doctor.getName(),
                doctor.getLastName(),
                doctor.getMajor().toString(),
                doctor.getGender().toString(),
                doctor.getUserId());
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

    public int getDoctorIdByUserId(int userId){
        String sql = "SELECT id FROM doctors WHERE user_id = ?";
        Optional<Integer> doctorId = executeQuerySingle(
                sql,
                rs -> rs.getInt("id"),
                userId
        );
        return doctorId.orElse(-1);
    }

    public List<Doctor> getDoctorsByMajor(String major){
        String sql = "SELECT * FROM doctors WHERE major = ?";
        return executeQueryList(sql, DoctorSupplier::getDoctorViaResultSet, major);
    }

    public List<Doctor> getListOfDoctors(){
        String sql = "SELECT * FROM doctors";
        return executeQueryList(sql, DoctorSupplier::getDoctorViaResultSet);
    }


    public Optional<Doctor> getDoctorByUserId(int userId){
        String sql = "SELECT * FROM doctors WHERE user_id = ?";
        return executeQuerySingle(sql, DoctorSupplier::getDoctorViaResultSet, userId);
    }

    public List<Doctor> getDoctorsByIds(Set<Integer> doctorIds){
        String mappedIds = getMappedIds(doctorIds);
        String sql = "SELECT * FROM doctors WHERE id IN (" + mappedIds +")";
        return executeQueryList(sql, DoctorSupplier::getDoctorViaResultSet, doctorIds.toArray());
    }


    private String getMappedIds(Set<Integer> doctorIds){
        return doctorIds.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));
    }
}
