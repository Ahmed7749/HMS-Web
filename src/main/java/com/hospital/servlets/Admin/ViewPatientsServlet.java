package com.hospital.servlets.Admin;

import com.hospital.daos.PatientDAO;
import com.hospital.daos.UserDAO;
import com.hospital.pojos.Doctor;
import com.hospital.pojos.Patient;
import com.hospital.pojos.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/admin/patients")
public class ViewPatientsServlet extends HttpServlet {
    private PatientDAO patientDAO;
    private UserDAO userDAO;
    @Override
    public void init(ServletConfig config) throws ServletException {
        patientDAO = new PatientDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Patient> patientList = patientDAO.getPatientsList();
        Map<Integer, User> userMap = new HashMap<>();

        if (patientList != null && !patientList.isEmpty()) {
            for (Patient patient : patientList) {
                int userId = patient.getUserId();
                if (userId > 0) {
                    Optional<User> userOpt = userDAO.getUserById(userId);
                    userOpt.ifPresent(user -> userMap.put(patient.getId(), user));
                }
            }
        } else {
            req.setAttribute("error", "There are no patients in the system.");
        }

        req.setAttribute("patientList", patientList);
        req.setAttribute("userMap", userMap);
        req.getRequestDispatcher("/admin/patients.jsp").forward(req, resp);
    }
}
