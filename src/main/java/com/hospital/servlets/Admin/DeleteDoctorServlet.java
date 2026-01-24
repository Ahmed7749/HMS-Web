package com.hospital.servlets.Admin;

import com.hospital.daos.DoctorDAO;
import com.hospital.daos.UserDAO;
import com.hospital.pojos.Doctor;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/admin/deleteDoctor")
public class DeleteDoctorServlet extends HttpServlet {
    private DoctorDAO doctorDAO;
    private UserDAO userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        doctorDAO = new DoctorDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int doctorId = fetchDoctorId(req);
        if (doctorId > 0) {
            int userId = fetchUserId(doctorId);
            boolean doctorDeleted = doctorDAO.deleteDoctorById(doctorId);
            if (doctorDeleted && userId > 0) {
                userDAO.deleteUser(userId);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/doctors");
    }

    private int fetchDoctorId(HttpServletRequest req) {
        try {
            String idParam = req.getParameter("doctorId");
            if (idParam != null) {
                return Integer.parseInt(idParam);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int fetchUserId(int doctorId) {
        Optional<Doctor> doctorOpt = doctorDAO.getDoctorById(doctorId);
        return doctorOpt.map(Doctor::getUserId).orElse(-1);
    }
}