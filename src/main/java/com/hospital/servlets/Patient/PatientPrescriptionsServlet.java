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
        patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");


        if (user == null) {
            redirectSafe(req, resp, "/login");
            return;
        }

        Optional<Patient> patientOptional = patientDAO.getPatientByUserId(user.getId());


        if (patientOptional.isEmpty()) {
            forwardWithError(req, resp, "Error: No patient profile linked to this account.");
            return;
        }

        Patient patient = patientOptional.get();
        List<Prescription> prescriptions = prescriptionsDAO.getPrescriptionsListByPatientId(patient.getId());


        if (prescriptions == null || prescriptions.isEmpty()) {
            forwardWithError(req, resp, "There are no active prescriptions.");
            return;
        }

        // Success Path: Attach data and render view
        req.setAttribute("prescriptions", prescriptions);
        req.setAttribute("doctorMap", buildPrescriptionDoctorMap(prescriptions));
        forwardSafe(req, resp, "/patient/prescriptions.jsp");
    }



    private Map<Integer, Doctor> buildPrescriptionDoctorMap(List<Prescription> prescriptions) {
        Set<Integer> doctorIds = prescriptions.stream()
                .map(Prescription::getDoctorId)
                .filter(id -> id > 0)
                .collect(Collectors.toSet());

        if (doctorIds.isEmpty()) return new HashMap<>();

        Map<Integer, Doctor> doctorsById = doctorDAO.getDoctorsByIds(doctorIds).stream()
                .collect(Collectors.toMap(Doctor::getId, d -> d));

        Map<Integer, Doctor> finalMap = new HashMap<>();
        for (Prescription p : prescriptions) {
            Doctor doctor = doctorsById.get(p.getDoctorId());
            if (doctor != null) {
                finalMap.put(p.getId(), doctor);
            }
        }
        return finalMap;
    }


    private void redirectSafe(HttpServletRequest req, HttpServletResponse resp, String path) {
        try {
            resp.sendRedirect(req.getContextPath() + path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to redirect", e);
        }
    }

    private void forwardSafe(HttpServletRequest req, HttpServletResponse resp, String path) {
        try {
            req.getRequestDispatcher(path).forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException("Failed to forward", e);
        }
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String errorMessage) {
        req.setAttribute("error", errorMessage);
        forwardSafe(req, resp, "/patient/prescriptions.jsp");
    }
}