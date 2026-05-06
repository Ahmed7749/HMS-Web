package com.hospital.servlets.Doctor;

import com.hospital.daos.DoctorDAO;
import com.hospital.daos.PatientDAO;
import com.hospital.daos.PrescriptionsDAO;
import com.hospital.pojos.Prescription;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/doctor/prescribe")
public class PrescribeServlet extends HttpServlet {
    private PrescriptionsDAO prescriptionsDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        prescriptionsDAO = new PrescriptionsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/doctor/prescribe.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addPrescription(req,resp);
    }

    private void addPrescription(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String medicationName = req.getParameter("medication_name");
            String dosage = req.getParameter("dosage");
            String instructions = req.getParameter("instructions");
            String pIdStr = req.getParameter("patient_id");
            String dIdStr = req.getParameter("doctor_id");
            System.out.println("Received Patient ID: '" + pIdStr + "'");
            System.out.println("Received Doctor ID: '" + dIdStr + "'");
            if (pIdStr == null || pIdStr.isEmpty() || dIdStr == null || dIdStr.isEmpty()) {
                throw new NumberFormatException("ID is missing");
            }
            int patientId = Integer.parseInt(pIdStr);
            int doctorId = Integer.parseInt(dIdStr);
            prescriptionsDAO.addPrescription(new Prescription(medicationName, dosage, instructions, doctorId, patientId));
            resp.sendRedirect(req.getContextPath() + "/doctor/home?success=Prescription+Added");

        } catch (NumberFormatException e) {
            System.out.println("Parsing error: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/doctor/home?error=Invalid+Data");
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/doctor/home?error=Database+Error");
        }
    }
}