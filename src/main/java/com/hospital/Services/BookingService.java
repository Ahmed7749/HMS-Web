package com.hospital.Services;

import com.hospital.daos.AppointmentDAO;
import com.hospital.pojos.Appointment;
import com.hospital.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class BookingService {

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    public String processBooking(Appointment appt) {
        Connection con = null;
        try {

            con = DatabaseConnector.getDatabaseConnection();
            con.setAutoCommit(false);


            if (appointmentDAO.isDoctorLocked(con, appt.getDoctor_id(), appt.getAppointmentDate(), appt.getAppointmentTime())) {
                con.rollback();
                return "This doctor is already booked at this time.";
            }
            if (appointmentDAO.isPatientLocked(con, appt.getPatient_id(), appt.getAppointmentDate(), appt.getAppointmentTime())) {
                con.rollback();
                return "You already have an appointment at this time!";
            }


            appointmentDAO.insertAppointmentLocked(con, appt);
            con.commit();
            return null;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException("Database error during booking transaction", e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DatabaseConnector.returnConnection(con);
            }
        }
    }
}