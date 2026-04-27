package com.hospital.filters;

import com.hospital.Enums.Roles;
import com.hospital.pojos.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/doctor/*", "/patient/*"})
public class loginFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (!isLoggedIn(session)) {
            res.sendRedirect(req.getContextPath() + "/login.jsp?error=unauthorized");
            return;
        }
        User user = (User) session.getAttribute("user");
        String path = req.getServletPath();
        if (isAuthorized(user, path)) {
            chain.doFilter(request, response);
        } else {
            handleUnauthorizedAccess(req, res, user.getRole());
        }
    }
    private boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute("user") != null;
    }

    private boolean isAuthorized(User user, String path) {
        Roles role = user.getRole();
        if (path.startsWith("/admin") && role == Roles.ADMIN) return true;
        if (path.startsWith("/doctor") && role == Roles.DOCTOR) return true;
        if (path.startsWith("/patient") && role == Roles.PATIENT) return true;
        return false;
    }

    private void handleUnauthorizedAccess(HttpServletRequest req, HttpServletResponse res, Roles role)
            throws IOException {
        String destination = switch (role) {
            case ADMIN -> "/admin/dashboard.jsp";
            case DOCTOR -> "/doctor/schedule.jsp";
            default -> "/patient/home.jsp";
        };
        res.sendRedirect(req.getContextPath() + destination + "?error=access_denied");
    }
}

