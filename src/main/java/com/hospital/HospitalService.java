package com.hospital;

import com.hospital.daos.AppointmentDAO;
import com.hospital.daos.DoctorDAO;
import com.hospital.daos.PatientDAO;
import com.hospital.daos.UserDAO;
import com.hospital.pojos.Appointment;
import com.hospital.pojos.Doctor;
import com.hospital.pojos.Patient;
import com.hospital.pojos.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class HospitalService {
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    private AppointmentDAO appointmentDAO;
    private UserDAO userDAO;
    public HospitalService() {
        this.patientDAO = new PatientDAO();
        this.doctorDAO = new DoctorDAO();
        this.appointmentDAO = new AppointmentDAO();
        this.userDAO = new UserDAO();
    }

    public HospitalService(PatientDAO patientDAO, DoctorDAO doctorDAO, AppointmentDAO appointmentDAO) {
        this.patientDAO = patientDAO;
        this.doctorDAO = doctorDAO;
        this.appointmentDAO = appointmentDAO;
    }

    public boolean admitPatient(Patient patient){
        Optional<Patient> patientOptional = patientDAO.findPatientByName(patient.getName());
        if(patientOptional.isPresent()){
            return false;
        }
        return patientDAO.addPatientToDB(patient);
    }



    public boolean updatePatientName(int patientId, String newName){
        Optional<Patient> foundPatient = patientDAO.getPatientById(patientId);
        return foundPatient.filter(patient -> patientDAO.updatePatientName(patient, newName)).isPresent();
    }

    public boolean deletePatientById(int id){
        Optional<Patient> foundPatient = patientDAO.getPatientById(id);
        return foundPatient.filter(patientDAO::deletePatientById).isPresent();
    }


    public List<Patient> getPatientsList(){
        return patientDAO.getPatientsList();
    }

    public boolean addDoctor(Doctor doctor){
        Optional<Doctor> foundDoctor = doctorDAO.getDoctorByName(doctor.getName());
        if(foundDoctor.isPresent()){
            return false;
        }
        return doctorDAO.addDoctorToDB(doctor);
    }


    public boolean updateDoctorName(int doctorId, String newName){
        Optional<Doctor> foundDoctor = doctorDAO.getDoctorById(doctorId);
        return foundDoctor.filter(doctor -> doctorDAO.updateDoctorName(doctor, newName)).isPresent();
    }

    public boolean deleteDoctorById(int id){
        Optional<Doctor> doctor = doctorDAO.getDoctorById(id);
        if(doctor.isPresent()){
            return doctorDAO.deleteDoctorById(id);
        }
        return false;
    }


    public List<Doctor> getDoctorsList(){
        return doctorDAO.getListOfDoctors();
    }


    public boolean bookAppointment(Appointment appointment){
        if (appointmentDAO.isBooked(appointment.getAppointmentTime(), appointment.getAppointmentDate(), appointment.getPatient_id(), appointment.getDoctor_id())) {
            return false;
        }
        return appointmentDAO.bookAppointment(appointment);
    }

    public boolean updateAppointmentTime(int appointmentId, LocalTime newTime){
        Optional<Appointment> appointment = appointmentDAO.getAppointmentById(appointmentId);
        if(appointment.isPresent()) {
            if (appointmentDAO.isBooked(appointment.get().getAppointmentTime(), appointment.get().getAppointmentDate(), appointment.get().getPatient_id(), appointment.get().getDoctor_id())) {
                return appointmentDAO.updateAppointmentTime(appointment.get(), newTime);
            }
            return false;
        }
        return false;
    }

    public boolean updateAppointmentDate(int appointmentId, LocalDate newDate){
        Optional<Appointment> appointment = appointmentDAO.getAppointmentById(appointmentId);
        if(appointment.isPresent()) {
            if (appointmentDAO.isBooked(appointment.get().getAppointmentTime(), appointment.get().getAppointmentDate(), appointment.get().getPatient_id(), appointment.get().getDoctor_id())) {
                return appointmentDAO.updateAppointmentDate(appointment.get(), newDate);
            }
            return false;
        }
        return false;
    }


    public boolean deleteAppointmentById(int appointmentId){
        Optional<Appointment> foundAppointment = appointmentDAO.getAppointmentById(appointmentId);
        return foundAppointment.filter(appointmentDAO::deleteAppointment).isPresent();
    }

    public List<Appointment> getAppointmentList(){
        return appointmentDAO.getListOfAppointments();
    }

    public List<Appointment> getAppointmentsForPatient(int patient_id){
        return appointmentDAO.getAppointmentsByPatientId(patient_id);
    }


    public List<Doctor> getDoctorsByMajor(String major){
        return doctorDAO.getDoctorsByMajor(major);
    }
}
