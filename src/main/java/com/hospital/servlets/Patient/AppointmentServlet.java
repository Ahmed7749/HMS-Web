package com.hospital.servlets.Patient;

import com.hospital.DTOs.PatientAppointmentDTO;
import com.hospital.daos.AppointmentDAO;
import com.hospital.daos.PatientDAO;
import com.hospital.pojos.Patient;
import com.hospital.pojos.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/patient/appointments")
public class AppointmentServlet extends HttpServlet {
    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;
    @Override
    public void init(ServletConfig config) throws ServletException {
        appointmentDAO = new AppointmentDAO();
        patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        User user = getAuthenticatedUser(req);
        HttpSession session = req.getSession(false);
        if(user == null || session == null){
            try {
                resp.sendRedirect("login.jsp?error=SessionExpired");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        Optional<Patient> patientOptional = patientDAO.getPatientByUserId(user.getId());
        if(patientOptional.isPresent()){
            Patient patient = patientOptional.get();
            List<PatientAppointmentDTO> appointmentList = appointmentDAO.getAppointmentsWithDoctorInfo(patient.getId());
            if(appointmentList.isEmpty()){
                try {
                    forwardToAppointmentPage(req, resp, "you have no appointments", null);
                } catch (ServletException | IOException e) {
                    throw new RuntimeException(e);
                }
            } else{
                req.setAttribute("appointmentList", appointmentList);
                try {
                    req.getRequestDispatcher("/patient/appointments.jsp").forward(req, resp);
                } catch (ServletException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else{
            try {
                forwardToAppointmentPage(req,resp, "No patient associated with the session", null);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private User getAuthenticatedUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
    }

    private void forwardToAppointmentPage(HttpServletRequest req, HttpServletResponse resp, String errorMessage, String successMessage) throws ServletException, IOException{
        if (errorMessage != null) {
            req.setAttribute("error", errorMessage);
        } else if (successMessage != null) {
            req.setAttribute("success", successMessage);
        }
        req.getRequestDispatcher("/patient/appointments.jsp").forward(req, resp);
    }

}
