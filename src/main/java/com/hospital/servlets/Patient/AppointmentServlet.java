package com.hospital.servlets.Patient;

import com.hospital.DTOs.PatientAppointmentDTO;
import com.hospital.daos.AppointmentDAO;
import com.hospital.daos.PatientDAO;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getAuthenticatedUser(req);
        HttpSession session = req.getSession(false);
        if(user == null || session == null){
            resp.sendRedirect("login.jsp?error=SessionExpired");
            return;
        }
        int patientId = patientDAO.getPatientIdByUserId(user.getId());
        if(patientId != -1){
            List<PatientAppointmentDTO> appointmentList = appointmentDAO.getAppointmentsWithDoctorInfo(patientId);
            if(appointmentList.isEmpty()){
                forwardToAppointmentPage(req, resp, "you have no appointments", null);
            } else{
                req.setAttribute("appointmentList", appointmentList);
                req.getRequestDispatcher("/patient/appointments.jsp").forward(req, resp);
            }
        } else{
            forwardToAppointmentPage(req,resp, "No patient associated with the session", null);
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
