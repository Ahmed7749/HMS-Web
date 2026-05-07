package com.hospital.servlets.Patient;

import com.hospital.daos.DoctorDAO;
import com.hospital.daos.PatientDAO;
import com.hospital.pojos.Appointment;
import com.hospital.pojos.Patient;
import com.hospital.pojos.User;
import com.hospital.Services.BookingService;
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
import java.util.Optional;

@WebServlet("/patient/book")
public class BookingServlet extends HttpServlet {

    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    private EmailService emailService;
    private BookingService bookingService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        patientDAO = new PatientDAO();
        doctorDAO = new DoctorDAO();
        emailService = new EmailService();
        bookingService = new BookingService();
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
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            processBookingRequest(req, resp, user);
        } catch (Exception e) {
            e.printStackTrace();
            redirectToBookPage(req, resp, "An unexpected system error occurred.", null);
        }
    }

    private void processBookingRequest(HttpServletRequest req, HttpServletResponse resp, User user) throws Exception {
        LocalDate date = LocalDate.parse(req.getParameter("appointmentDate"));
        LocalTime time = LocalTime.parse(req.getParameter("appointmentTime"));
        int doctorId = Integer.parseInt(req.getParameter("doctorId"));


        if (date.isBefore(LocalDate.now())) {
            redirectToBookPage(req, resp, "Cannot set date in the past!", null);
            return;
        }

        Optional<Patient> patientOptional = patientDAO.getPatientByUserId(user.getId());
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();

            Appointment newAppt = new Appointment(date, time, patient.getId(), doctorId);


            String bookingError = bookingService.processBooking(newAppt);

            if (bookingError == null) {

                if (patient.getEmail() != null) {
                    String message = String.format("Your appointment is successfully booked for %s at %s.", date, time);
                    emailService.sendMailAsync(patient.getEmail(), "Appointment Confirmed", message);
                }

                redirectToBookPage(req, resp, null, "Appointment Booked Successfully. A confirmation email has been sent!");
            } else {

                redirectToBookPage(req, resp, bookingError, null);
            }
        } else {
            redirectToBookPage(req, resp, "You are not registered as a patient.", null);
        }
    }

    private User getAuthenticatedUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
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