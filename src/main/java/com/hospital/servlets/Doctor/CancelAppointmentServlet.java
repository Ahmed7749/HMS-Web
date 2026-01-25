package com.hospital.servlets.Doctor;

import com.hospital.daos.AppointmentDAO;
import com.hospital.pojos.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/doctor/cancel")
public class CancelAppointmentServlet extends HttpServlet {
    private AppointmentDAO appointmentDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getAuthenticatedUser(req);
        if(user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        int appointmentId = fetchAppointmentId(req);

        if(appointmentId > 0) {
            boolean deleted = appointmentDAO.deleteAppointment(appointmentId);
            if(deleted) {
                req.getSession().setAttribute("success", "Appointment cancelled successfully.");
            } else {
                req.getSession().setAttribute("error", "Failed to cancel appointment.");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/doctor/home");
    }

    private int fetchAppointmentId(HttpServletRequest req){
        try {
            String appointmentId = req.getParameter("appointmentId");
            if(appointmentId != null){
                return Integer.parseInt(appointmentId);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }
    private User getAuthenticatedUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
    }
}