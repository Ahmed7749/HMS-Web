package com.hospital.servlets;

import com.hospital.Enums.Genders;
import com.hospital.Enums.Roles;
import com.hospital.daos.PatientDAO;
import com.hospital.daos.UserDAO;
import com.hospital.pojos.Patient;
import com.hospital.pojos.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private UserDAO userDAO;
    private PatientDAO patientDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userDAO = new UserDAO();
        patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("signup.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String middleName = req.getParameter("middleName");
        String gender = req.getParameter("gender");
        String birthDate = req.getParameter("birthdate");
        String lastName = req.getParameter("lastName");
        if (userDAO.userExists(username).isPresent()) {
            req.setAttribute("error", "Username already exists!");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
            return;
        }
        User newUser = new User(0, username, password, Roles.PATIENT);
        int newUserId = userDAO.addUser(newUser);
        if (newUserId != -1) {
            Patient newPatient = new Patient(name, Genders.valueOf(gender.toUpperCase()), LocalDate.parse(birthDate), middleName, lastName, newUserId);
            patientDAO.addPatientToDB(newPatient);
            resp.sendRedirect("login.jsp?success=Account Created!");
        } else {
            req.setAttribute("error", "Registration Failed. Database Error.");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        }
    }
}
