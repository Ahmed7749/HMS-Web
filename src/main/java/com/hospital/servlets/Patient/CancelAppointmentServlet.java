package com.hospital.servlets.Patient;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            req.getRequestDispatcher("/appointments.jsp").forward(req,resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        User user = getAuthenticatedUser(req);
        if(user == null) {
            try {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        int appointmentId = fetchAppointmentId(req);
        if(appointmentId > 0) {
            boolean deleted = appointmentDAO.deleteAppointment(appointmentId);
            if(deleted) {
                req.getSession().setAttribute("success", "Appointment deleted successfully!");
            } else {
                req.getSession().setAttribute("error", "Could not delete appointment.");
            }
        }
        try {
            resp.sendRedirect(req.getContextPath() + "/patient/appointments");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int fetchAppointmentId(HttpServletRequest req){
        String appointmentId = req.getParameter("appointmentId");
        if(appointmentId == null){
            return -1;
        }
        return Integer.parseInt(appointmentId);
    }

    private User getAuthenticatedUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
    }
}
