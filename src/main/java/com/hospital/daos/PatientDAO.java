package com.hospital.daos;

import com.hospital.pojos.Patient;
import com.hospital.utils.PatientSupplier;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class PatientDAO extends GenericDAO{
    public void addPatientToDB(Patient patient){
        String sql = "INSERT INTO patients(name,gender,birth_date,middle_name,last_name, user_id, email) VALUES(?,?,?,?,?,?,?)";
        executeUpdate(sql,
                patient.getName(),
                patient.getGender().toString(),
                Date.valueOf(patient.getBirthDate()),
                patient.getMiddleName(),
                patient.getLastName(),
                patient.getUserId(),
                patient.getEmail());
    }


    public int getPatientIdByUserId(int userId) {
        String sql = "SELECT id FROM patients WHERE user_id = ?";
        Optional<Integer> foundId = executeQuerySingle(
                sql,
                rs -> rs.getInt("id"),
                userId
        );
        return foundId.orElse(-1);
    }

    public Optional<Patient> getPatientById(int id){
        String sql = "SELECT * FROM patients WHERE id = ?";
        return executeQuerySingle(sql, PatientSupplier::getPatientViaResultSet, id);
    }


    public Optional<Patient> findPatientByName(String patientName){
        String sql = "SELECT * FROM patients WHERE name = ?";
        return executeQuerySingle(sql, PatientSupplier::getPatientViaResultSet, patientName);
    }


    public boolean updatePatientName(Patient patient, String newName){
        String sql = "UPDATE patients SET name = ? WHERE id = ?";
        return super.executeUpdate(sql, newName, patient.getId());
    }

    public List<Patient> getPatientsList(){
        String sql = "SELECT * FROM patients";
        return executeQueryList(sql, PatientSupplier::getPatientViaResultSet);
    }

    public boolean deletePatientById(int id){
        String sql = "DELETE FROM patients WHERE id = ?";
        return executeUpdate(sql, id);
    }
}
