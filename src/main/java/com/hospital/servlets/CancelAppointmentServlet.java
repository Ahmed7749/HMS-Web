package com.hospital.servlets;

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

@WebServlet("/patient/cancel")
public class CancelAppointmentServlet extends HttpServlet {
    private AppointmentDAO appointmentDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/appointments.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getAuthenticatedUser(req);
        if(user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        String appointmentId = req.getParameter("appointmentId");
        if(appointmentId != null) {
            int id = Integer.parseInt(appointmentId);
            boolean deleted = appointmentDAO.deleteAppointment(id);
            if(deleted) {
                req.getSession().setAttribute("success", "Appointment deleted successfully!");
            } else {
                req.getSession().setAttribute("error", "Could not delete appointment.");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/patient/appointments");
    }

    private User getAuthenticatedUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
    }

    private void forwardToAppointmentPage(HttpServletRequest req, HttpServletResponse resp, String errorMessage, String successMessage) throws ServletException, IOException{
        HttpSession session = req.getSession(false);
        if(errorMessage != null){
            session.setAttribute("error", errorMessage);
        } else if(successMessage != null){
            session.setAttribute("success", successMessage);
        }
        req.getRequestDispatcher("/patient/appointments.jsp").forward(req,resp);
    }
}
