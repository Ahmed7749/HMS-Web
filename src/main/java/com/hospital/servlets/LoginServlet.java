package com.hospital.servlets;

import com.hospital.daos.UserDAO;
import com.hospital.pojos.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.userDAO = new UserDAO();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(username == null || password == null){
            resp.sendRedirect("login.jsp?error=Wrong data");
            return;
        }
        Optional<User> foundUser = userDAO.loggedIn(username,password);
        if(foundUser.isPresent()){
            User user = foundUser.get();
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            switch(user.getRole().toString().toLowerCase()){
                case "admin" -> resp.sendRedirect("admin/home.jsp");
                case "doctor" -> resp.sendRedirect("doctor/home");
                case "patient" -> resp.sendRedirect("patient/home.jsp");
                default -> {
                    req.setAttribute("error", "Invalid Username or Password");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                }
            }
        } else{
            req.setAttribute("error", "Invalid Username or Password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
