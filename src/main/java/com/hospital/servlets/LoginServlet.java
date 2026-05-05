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
import org.mindrot.jbcrypt.BCrypt;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(falseUser(username, password)){
            redirectInvalidUser(resp);
            return;
        }
        try {
            evaluateUserLogin(username, password, req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void evaluateUserLogin(String username, String password, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> foundUser = userDAO.findByUsername(username);
        if(foundUser.isPresent()){
            User user = foundUser.get();
            if(!verifyPassword(password, user, resp,req)){
                return;
            }
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            loginUser(user, session, req, resp);

        } else {
            req.setAttribute("error", "Invalid Username or Password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }


    private boolean falseUser(String username, String password){
        return (username == null || password == null);
    }

    private boolean verifyPassword(String password, User user, HttpServletResponse resp, HttpServletRequest req) throws ServletException, IOException {
        if(!BCrypt.checkpw(password,user.getPassword())) {
            invalidCredentials(req,resp);
            return false;
        }
        return true;
    }

    private void redirectInvalidUser(HttpServletResponse resp){
        try {
            resp.sendRedirect("login.jsp?error=Wrong data");
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void loginUser(User user, HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        try {
            switch (user.getRole().toString().toLowerCase()) {
                case "admin" -> resp.sendRedirect("admin/home.jsp");
                case "doctor" -> resp.sendRedirect("doctor/home");
                case "patient" -> resp.sendRedirect("patient/home.jsp");
                default -> invalidCredentials(req,resp);
            }
        } catch (IOException | ServletException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void invalidCredentials(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("error", "Invalid Username or Password");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
