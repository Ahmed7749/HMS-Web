package com.hospital.servlets.Admin;

import com.hospital.daos.DoctorDAO;
import com.hospital.pojos.Doctor;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/doctors")
public class ViewDoctorsServlet extends HttpServlet {
    private DoctorDAO doctorDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        doctorDAO = new DoctorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Doctor> doctorList = doctorDAO.getListOfDoctors();
        if(doctorList.isEmpty()){
            req.setAttribute("error", "There are no doctors ): ");
            req.getRequestDispatcher("/admin/doctors.jsp").forward(req,resp);
            return;
        }
        req.setAttribute("doctorList", doctorList);
        req.getRequestDispatcher("/admin/doctors.jsp").forward(req,resp);
    }
}
