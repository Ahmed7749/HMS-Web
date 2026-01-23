package com.hospital.servlets.Admin;

import com.hospital.Enums.Genders;
import com.hospital.Enums.Majors;
import com.hospital.Enums.Roles;
import com.hospital.daos.DoctorDAO;
import com.hospital.daos.UserDAO;
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

@WebServlet("/admin/registerDoctor")
public class DoctorRegisterServlet extends HttpServlet {
    private DoctorDAO doctorDAO;
    private UserDAO userDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/admin/registerDoctor.jsp").forward(req,resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        doctorDAO = new DoctorDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getAuthenticatedUser(req);
        HttpSession session = req.getSession(false);
        if(user == null || session == null){
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=SessionExpired");
            return;
        }
        try {
            int userId = userDAO.addUser(getUser(req));
            registered(userId, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            redirectToPage(req, resp, "Username already exists! Please choose another.", null);
        }
    }

    private void registered(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(userId > 0){
            Doctor doctor = getDoctorDetails(req, resp);
            if(doctor == null){
                return;
            }
            doctor.setUserId(userId);
            doctorDAO.addDoctorToDB(doctor);
            redirectToPage(req,resp,null, "Added successfully !");
        } else{
            redirectToPage(req,resp, "Error in db", null);
        }
    }

    private User getAuthenticatedUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
    }

    private Doctor getDoctorDetails(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String lastName = req.getParameter("lastName");
        String major = req.getParameter("major");
        String gender = req.getParameter("gender");
        if(major == null || gender == null || name == null || lastName ==null){
            redirectToPage(req,resp,"Invalid data sent", null);
            return null;
        }
        return new Doctor(name,lastName, Majors.valueOf(major.toUpperCase()), Genders.valueOf(gender.toUpperCase()));
    }


    private User getUser(HttpServletRequest req){
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        return new User(username, password, Roles.DOCTOR);
    }

    private void redirectToPage(HttpServletRequest req, HttpServletResponse resp, String error, String success) throws IOException {
        HttpSession session = req.getSession(false);
            if (error != null) {
                session.setAttribute("error", error);
            }
            if (success != null) {
                session.setAttribute("success", success);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/registerDoctor.jsp");
    }
}
