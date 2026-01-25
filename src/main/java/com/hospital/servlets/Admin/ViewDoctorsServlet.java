package com.hospital.servlets.Admin;

import com.hospital.daos.DoctorDAO;
import com.hospital.daos.UserDAO;
import com.hospital.pojos.Doctor;
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

@WebServlet("/admin/doctors")
public class ViewDoctorsServlet extends HttpServlet {
    private DoctorDAO doctorDAO;
    private UserDAO userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        doctorDAO = new DoctorDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Doctor> doctorList = doctorDAO.getListOfDoctors();
        Map<Integer, User> userMap = new HashMap<>();

        if (doctorList != null && !doctorList.isEmpty()) {
            for (Doctor doc : doctorList) {
                int userId = doc.getUserId();
                if (userId > 0) {
                    Optional<User> userOpt = userDAO.getUserById(userId);
                    userOpt.ifPresent(user -> userMap.put(doc.getId(), user));
                }
            }
        } else {
            req.setAttribute("error", "There are no doctors registered.");
        }

        req.setAttribute("doctorList", doctorList);
        req.setAttribute("userMap", userMap);
        req.getRequestDispatcher("/admin/doctors.jsp").forward(req, resp);
    }
}