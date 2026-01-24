package com.hospital.servlets.Admin;

import com.hospital.daos.PatientDAO;
import com.hospital.pojos.Patient;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/patients")
public class ViewPatientsServlet extends HttpServlet {
    private PatientDAO patientDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Patient> patientList = patientDAO.getPatientsList();
        if(patientList.isEmpty()){
            req.setAttribute("error", "no patients in the system");
            req.getRequestDispatcher("/admin/patients.jsp").forward(req,resp);
            return;
        }
        req.setAttribute("patientList", patientList);
        req.getRequestDispatcher("/admin/patients.jsp").forward(req,resp);
    }
}
