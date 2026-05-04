package com.hospital.servlets.Patient;

import com.hospital.daos.AppointmentDAO;
import com.hospital.daos.DoctorDAO;
import com.hospital.daos.PatientDAO;
import com.hospital.pojos.Appointment;
import com.hospital.pojos.User;
import com.hospital.utils.EmailService;
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

@WebServlet("/patient/book")
public class BookingServlet extends HttpServlet {
    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    private EmailService emailService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        appointmentDAO = new AppointmentDAO();
        patientDAO = new PatientDAO();
        doctorDAO = new DoctorDAO();
        emailService = new EmailService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("doctorList", doctorDAO.getListOfDoctors());
        req.getRequestDispatcher("/patient/book.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getAuthenticatedUser(req);

        if (user == null) {
            resp.sendRedirect("login.jsp?error=SessionExpired");
            return;
        }

        try {
            processBookingRequest(req, resp, user);
        } catch (Exception e) {
            redirectToBookPage(req, resp, "Invalid input data format.", null);
        }
    }



    private void processBookingRequest(HttpServletRequest req, HttpServletResponse resp, User user) throws Exception {
        LocalDate date = LocalDate.parse(req.getParameter("appointmentDate"));
        LocalTime time = LocalTime.parse(req.getParameter("appointmentTime"));
        int doctorId = Integer.parseInt(req.getParameter("doctorId"));
        int patientId = patientDAO.getPatientIdByUserId(user.getId());

        String validationError = validateBookingRules(date, time, doctorId, patientId);
        if (validationError != null) {
            redirectToBookPage(req, resp, validationError, null);
            return;
        }

        boolean isBooked = appointmentDAO.bookAppointment(new Appointment(date, time, patientId, doctorId));

        if (isBooked) {
            sendConfirmationEmailAsync(patientId, date, time);
            redirectToBookPage(req, resp, null, "Appointment Booked Successfully. A confirmation email has been sent!");
        } else {
            redirectToBookPage(req, resp, "Database error occurred while booking.", null);
        }
    }


    private void sendConfirmationEmailAsync(int patientId, LocalDate date, LocalTime time) {
        patientDAO.getPatientById(patientId)
                .filter(patient -> patient.getEmail() != null)
                .ifPresent(patient -> {
                    new Thread(() -> {
                        try {
                            String message = String.format("Your appointment is successfully booked for %s at %s.", date, time);
                            emailService.sendMail(patient.getEmail(), "Appointment Confirmed", message);
                        } catch (Exception e) {
                            System.err.println("Failed to send email to " + patient.getEmail() + ": " + e.getMessage());
                        }
                    }).start();
                });
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
        if (session != null) {
            if (error != null) session.setAttribute("error", error);
            if (success != null) session.setAttribute("AddedMessage", success);
        }
        resp.sendRedirect(req.getContextPath() + "/patient/book");
    }
}