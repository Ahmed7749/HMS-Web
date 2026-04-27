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
import java.util.*;
import java.util.stream.Collectors;

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
        if (patientList != null && !patientList.isEmpty()) {
            Map<Integer, User> userMap = buildFinalMap(mapUsersList(getUsersByIds(patientList)), patientList);
            req.setAttribute("patientList", patientList);
            req.setAttribute("userMap", userMap);
        } else {
            req.setAttribute("error", "There are no patients registered.");
        }


        req.getRequestDispatcher("/admin/patients.jsp").forward(req, resp);
    }

    private Set<Integer> getUserIds(List<Patient> patientList){
        return patientList.stream()
                .map(Patient::getUserId)
                .filter(id -> id > 0)
                .collect(Collectors.toSet());
    }


    private List<User> getUsersByIds(List<Patient> patientList){
        return userDAO.getUsersByIds(getUserIds(patientList));
    }


    private Map<Integer, User> mapUsersList(List<User> users){
        return users.stream().collect(Collectors.toMap( User::getId, u -> u));
    }


    private Map<Integer, User> buildFinalMap(Map<Integer, User> usersById, List<Patient> patientList) {
        Map<Integer, User> finalMap = new HashMap<>();
        for (Patient patient : patientList) {
            User user = usersById.get(patient.getUserId());
            if (user != null) {
                finalMap.put(patient.getId(), user);
            }
        }
        return finalMap;
    }

}
