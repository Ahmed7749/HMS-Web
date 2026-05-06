package com.hospital.servlets.Patient;

import com.hospital.daos.DoctorDAO;
import com.hospital.daos.PatientDAO;
import com.hospital.daos.PrescriptionsDAO;
import com.hospital.pojos.Doctor;
import com.hospital.pojos.Patient;
import com.hospital.pojos.Prescription;
import com.hospital.pojos.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/patient/prescriptions")
public class PatientPrescriptionsServlet extends HttpServlet {
    private PrescriptionsDAO prescriptionsDAO;
    private DoctorDAO doctorDAO;
    private PatientDAO patientDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        prescriptionsDAO = new PrescriptionsDAO();
        doctorDAO = new DoctorDAO();
        patientDAO = new PatientDAO(); // Initialize PatientDAO
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        // 1. Grab the general User object from the session
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            try {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        Optional<Patient> patientOptional = patientDAO.getPatientByUserId(user.getId());
        if(patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            List<Prescription> prescriptionList = prescriptionsDAO.getPrescriptionsListByPatientId(patient.getId());
            if (prescriptionList != null && !prescriptionList.isEmpty()) {
                Map<Integer, Doctor> doctorMap = buildFinalMap(mapDoctorsList(getDoctorsByIds(prescriptionList)), prescriptionList);
                req.setAttribute("prescriptions", prescriptionList);
                req.setAttribute("doctorMap", doctorMap);
            } else {
                req.setAttribute("error", "There are no active prescriptions.");
            }

            try {
                req.getRequestDispatcher("/patient/prescriptions.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Set<Integer> getDoctorIds(List<Prescription> prescriptionList){
        return prescriptionList.stream()
                .map(Prescription::getDoctorId)
                .filter(id -> id > 0)
                .collect(Collectors.toSet());
    }

    private List<Doctor> getDoctorsByIds(List<Prescription> prescriptionList){
        return doctorDAO.getDoctorsByIds(getDoctorIds(prescriptionList));
    }

    private Map<Integer, Doctor> mapDoctorsList(List<Doctor> doctors){
        return doctors.stream().collect(Collectors.toMap(Doctor::getId, d -> d));
    }

    private Map<Integer, Doctor> buildFinalMap(Map<Integer, Doctor> doctorsById, List<Prescription> prescriptionList) {
        Map<Integer, Doctor> finalMap = new HashMap<>();
        for (Prescription prescription : prescriptionList) {
            Doctor doctor = doctorsById.get(prescription.getDoctorId());
            if (doctor != null) {
                finalMap.put(prescription.getId(), doctor);
            }
        }
        return finalMap;
    }
}