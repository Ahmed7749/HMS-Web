package com.hospital.filters;

import com.hospital.Enums.Roles;
import com.hospital.pojos.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/doctor/*", "/patient/*"})
public class loginFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);


        if (!isLoggedIn) {
            res.sendRedirect(req.getContextPath() + "/login.jsp?error=Access Denied: Login Required");
            return;
        }
        User user = (User) session.getAttribute("user");
        Roles role = user.getRole();
        String uri = req.getRequestURI();

        boolean isAllowed = false;

        if (uri.contains("/admin/") && role == Roles.ADMIN) {
            isAllowed = true;
        }
        else if (uri.contains("/doctor/") && role == Roles.DOCTOR) {
            isAllowed = true;
        }
        else if (uri.contains("/patient/") && role == Roles.PATIENT) {
            isAllowed = true;
        }
        if (isAllowed) {
            chain.doFilter(request, response);
        } else {
            if (role == Roles.ADMIN) res.sendRedirect(req.getContextPath() + "/admin/dashboard.jsp");
            else if (role == Roles.DOCTOR) res.sendRedirect(req.getContextPath() + "/doctor/schedule.jsp");
            else res.sendRedirect(req.getContextPath() + "/patient/home.jsp");
        }
    }
}

