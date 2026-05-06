package com.hospital.servlets.Doctor;

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
        req.getRequestDispatcher("/doctor/prescribe.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {

            Prescription prescription = buildPrescriptionFromRequest(req);


            prescriptionsDAO.addPrescription(prescription);


            resp.sendRedirect(req.getContextPath() + "/doctor/home?success=Prescription+Added");

        } catch (IllegalArgumentException e) {

            resp.sendRedirect(req.getContextPath() + "/doctor/home?error=Invalid+Data");
        } catch (Exception e) {

            resp.sendRedirect(req.getContextPath() + "/doctor/home?error=Database+Error");
        }
    }



    private Prescription buildPrescriptionFromRequest(HttpServletRequest req) {
        String medicationName = req.getParameter("medication_name");
        String dosage = req.getParameter("dosage");
        String instructions = req.getParameter("instructions");

        int patientId = parseRequiredInt(req.getParameter("patient_id"));
        int doctorId = parseRequiredInt(req.getParameter("doctor_id"));

        return new Prescription(medicationName, dosage, instructions, doctorId, patientId);
    }

    private int parseRequiredInt(String param) {
        if (param == null || param.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required ID parameter");
        }
        return Integer.parseInt(param.trim());
    }
}