package com.hospital.daos;

import com.hospital.pojos.Prescription;
import com.hospital.utils.PrescriptionSupplier;

import java.util.List;
import java.util.Optional;

public class    PrescriptionsDAO extends GenericDAO{
    public boolean addPrescription(Prescription prescription){
        String sql = "INSERT INTO prescriptions (medication_name, dosage,instructions, doctor_id, patient_id) VALUES (?,?,?,?,?)";
        return executeUpdate(sql,
                prescription.getMedicationName(),
                prescription.getDosage(),
                prescription.getInstructions(),
                prescription.getDoctorId(),
                prescription.getPatientId());
    }

    public List<Prescription> getPrescriptionsList(){
        String sql = "SELECT * FROM prescriptions";
        return executeQueryList(sql, PrescriptionSupplier::getPrescriptionViaResultSet);
    }

    public Optional<Prescription> getPrescriptionByDoctorId(int doctorId){
        String sql = "SELECT * FROM prescriptions WHERE doctor_id = ?";
        return executeQuerySingle(sql, PrescriptionSupplier::getPrescriptionViaResultSet, doctorId);
    }

    public Optional<Prescription> getPrescriptionByPatientId(int patientId){
        String sql = "SELECT * FROM prescriptions WHERE patient_id = ?";
        return executeQuerySingle(sql,PrescriptionSupplier::getPrescriptionViaResultSet, patientId);
    }

    public List<Prescription> getPrescriptionsListByPatientId(int patientId) {
        String sql = "SELECT * FROM prescriptions WHERE patient_id = ?";
        return executeQueryList(sql, PrescriptionSupplier::getPrescriptionViaResultSet, patientId);
    }

    public Optional<Prescription> getPrescriptionById(int id) {
        String sql = "SELECT * FROM prescriptions WHERE id = ?";
        return executeQuerySingle(sql, PrescriptionSupplier::getPrescriptionViaResultSet, id);
    }
}
