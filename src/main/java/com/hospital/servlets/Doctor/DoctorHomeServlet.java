package com.hospital.servlets.Doctor;

import com.hospital.daos.AppointmentDAO;
import com.hospital.daos.DoctorDAO;
import com.hospital.daos.PatientDAO;
import com.hospital.pojos.Appointment;
import com.hospital.pojos.Doctor;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/doctor/home")
public class DoctorHomeServlet extends HttpServlet {

    private DoctorDAO doctorDAO;
    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        doctorDAO = new DoctorDAO();
        appointmentDAO = new AppointmentDAO();
        patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getAuthenticatedUser(req);

        if (user == null) {
            redirectWithError(req, resp, "SessionExpired");
            return;
        }

        Optional<Doctor> currentDoctor = doctorDAO.getDoctorByUserId(user.getId());

        if (currentDoctor.isPresent()) {
            loadDashboardData(req, currentDoctor.get());
            req.getRequestDispatcher("/doctor/home.jsp").forward(req, resp);
        } else {
            redirectWithError(req, resp, "ProfileNotFound");
        }
    }

    private User getAuthenticatedUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            return (User) session.getAttribute("user");
        }
        return null;
    }

    private void loadDashboardData(HttpServletRequest req, Doctor doctor) {
        List<Appointment> schedule = appointmentDAO.getAppointmentsByDoctorId(doctor.getId());
        Map<Integer, Patient> patientMap = buildPatientMap(schedule);
        req.setAttribute("doctor", doctor);
        req.setAttribute("schedule", schedule);
        req.setAttribute("patientMap", patientMap);
    }

    private Map<Integer, Patient> buildPatientMap(List<Appointment> schedule) {
        Map<Integer, Patient> map = new HashMap<>();
        for (Appointment appt : schedule) {
            int patientId = appt.getPatient_id();
            if (!map.containsKey(patientId)) {
                patientDAO.getPatientById(patientId).ifPresent(p -> map.put(p.getId(), p));
            }
        }
        return map;
    }

    private void redirectWithError(HttpServletRequest req, HttpServletResponse resp, String error) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login.jsp?error=" + error);
    }
}