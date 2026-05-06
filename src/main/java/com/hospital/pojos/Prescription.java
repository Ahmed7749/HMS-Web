package com.hospital.pojos;
import com.hospital.utils.Validations;

import java.time.LocalDateTime;

public class Prescription {
    private int id;
    private String medicationName;
    private String dosage;
    private String instructions;
    private final LocalDateTime datePrescribed;
    private int doctorId;
    private int patientId;

    public Prescription(int id, String medicationName, String dosage, String instructions, int doctorId, int patientId) {
        this.id = id;
        this.medicationName = Validations.checkNotNull(medicationName, "Medication must have a name");
        this.dosage = Validations.checkNotNull(dosage, "You must put a dosage for the medication");
        this.instructions = Validations.checkNotNull(instructions, "You must include instructions for the medication");
        this.datePrescribed = LocalDateTime.now();
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public Prescription(String medicationName, String dosage, String instructions, int doctorId, int patientId) {
        this.medicationName = Validations.checkNotNull(medicationName, "Medication must have a name");
        this.dosage = Validations.checkNotNull(dosage, "You must put a dosage for the medication");
        this.instructions = Validations.checkNotNull(instructions, "You must include instructions for the medication");
        this.datePrescribed = LocalDateTime.now();
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
