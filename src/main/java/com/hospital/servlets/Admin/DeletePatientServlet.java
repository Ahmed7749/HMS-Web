package com.hospital.servlets.Admin;

import com.hospital.daos.PatientDAO;
import com.hospital.daos.UserDAO;
import com.hospital.pojos.Patient;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/admin/deletePatient")
public class DeletePatientServlet extends HttpServlet {
    private PatientDAO patientDAO;
    private UserDAO userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        patientDAO = new PatientDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int patientId = fetchPatientId(req);
        if (patientId > 0) {
            int userId = fetchUserId(patientId);
            boolean patientDeleted = patientDAO.deletePatientById(patientId);
            if (patientDeleted && userId > 0) {
                userDAO.deleteUser(userId);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/patients");
    }

    private int fetchPatientId(HttpServletRequest req) {
        try {
            String id = req.getParameter("patientId");
            if (id != null) {
                return Integer.parseInt(id);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int fetchUserId(int patientId) {
        Optional<Patient> patientOptional = patientDAO.getPatientById(patientId);
        return patientOptional.map(Patient::getUserId).orElse(-1);
    }
}
