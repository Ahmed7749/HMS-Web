package com.hospital.servlets.Patient;

import com.hospital.daos.AppointmentDAO;
import com.hospital.daos.DoctorDAO;
import com.hospital.daos.PatientDAO;
import com.hospital.pojos.Appointment;
import com.hospital.pojos.Doctor;
import com.hospital.pojos.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/patient/book")
public class BookingServlet extends HttpServlet{
    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    @Override
    public void init(ServletConfig config) throws ServletException {
        appointmentDAO = new AppointmentDAO();
        patientDAO = new PatientDAO();
        doctorDAO = new DoctorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Doctor> doctors = doctorDAO.getListOfDoctors();
        req.setAttribute("doctorList", doctors);
        req.getRequestDispatcher("/patient/book.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = getAuthenticatedUser(req);
            if (user == null) {
                resp.sendRedirect("login.jsp?error=SessionExpired");
                return;
            }
            LocalDate date = LocalDate.parse(req.getParameter("appointmentDate"));
            LocalTime time = LocalTime.parse(req.getParameter("appointmentTime"));
            int doctorId = Integer.parseInt(req.getParameter("doctorId"));
            int patientId = patientDAO.getPatientIdByUserId(user.getId());
            String errorMessage = validateBookingRules(date, time, doctorId, patientId);
            if (errorMessage != null) {
                redirectToBookPage(req, resp, errorMessage, null);
                return;
            }
            boolean success = appointmentDAO.bookAppointment(new Appointment(date, time, patientId, doctorId));
            if (success) {
                redirectToBookPage(req, resp, null, "Appointment Booked Successfully");
            } else {
                redirectToBookPage(req, resp, "Database error occurred.", null);
            }
        } catch (Exception e) {
            redirectToBookPage(req, resp, "Invalid input data.", null);
        }
    }
    private User getAuthenticatedUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
    }
    private String validateBookingRules(LocalDate date, LocalTime time, int docId, int patId) {
        if (patId == -1) return "You are not registered as a patient.";
        if (date.isBefore(LocalDate.now())) return "Cannot set date in the past!";
        if (appointmentDAO.isDoctorBusy(time, date, docId)) return "This doctor is already booked.";
        if (appointmentDAO.isPatientBusy(time, date, patId)) return "You already have an appointment at this time!";
        return null; 
    }
    private void redirectToBookPage(HttpServletRequest req, HttpServletResponse resp, String error, String success)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (error != null) {
            session.setAttribute("error", error);
        }
        if (success != null) {
            session.setAttribute("AddedMessage", success);
        }
        resp.sendRedirect(req.getContextPath() + "/patient/book");
    }
}
