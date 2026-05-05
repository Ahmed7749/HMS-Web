package com.hospital.servlets.Admin;

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

import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@WebServlet("/admin/doctors")
public class ViewDoctorsServlet extends HttpServlet {
    private DoctorDAO doctorDAO;
    private UserDAO userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        doctorDAO = new DoctorDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        List<Doctor> doctorList = doctorDAO.getListOfDoctors();
        if (doctorList != null && !doctorList.isEmpty()) {
            Map<Integer, User> userMap = buildFinalMap(mapUsersList(getUsersByIds(doctorList)), doctorList);
            req.setAttribute("doctorList", doctorList);
            req.setAttribute("userMap", userMap);
        } else {
            req.setAttribute("error", "There are no doctors registered.");
        }


        try {
            req.getRequestDispatcher("/admin/doctors.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<Integer> getUserIds(List<Doctor> doctorList){
        return doctorList.stream()
                .map(Doctor::getUserId)
                .filter(id ->  id > 0)
                .collect(Collectors.toSet());
    }


    private List<User> getUsersByIds(List<Doctor> doctorList){
        return userDAO.getUsersByIds(getUserIds(doctorList));
    }


    private Map<Integer, User> mapUsersList(List<User> users){
        return users.stream().collect(Collectors.toMap( User::getId, u -> u));
    }


    private Map<Integer, User> buildFinalMap(Map<Integer, User> usersById, List<Doctor> docList) {
        Map<Integer, User> finalMap = new HashMap<>();
        for (Doctor doc : docList) {
            User user = usersById.get(doc.getUserId());
            if (user != null) {
                finalMap.put(doc.getId(), user);
            }
        }
        return finalMap;
    }
}