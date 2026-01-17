package com.hospital.utils;

import com.hospital.Enums.Roles;
import com.hospital.pojos.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSupplier {
    public static User getUserViaResultSet(ResultSet r) throws SQLException {
        return new User(
                r.getInt("id"),
                r.getString("username"),
                r.getString("password"),
                Roles.valueOf(r.getString("role").toUpperCase())
        );
    }
}
