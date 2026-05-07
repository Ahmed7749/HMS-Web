package com.hospital.daos;

import com.hospital.utils.DatabaseConnector;
import com.hospital.utils.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class GenericDAO{

    protected int executeUpdateReturnId(String sql, Object... parameters){
        Connection con = null;
        try{
            con = DatabaseConnector.getDatabaseConnection();
            try(PreparedStatement stmt = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)){
                for(int i = 0; i < parameters.length; i++){
                    stmt.setObject(i + 1, parameters[i]);
                }

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            return -1;
        } catch(SQLException e){
            throw new RuntimeException("Error in db", e);
        } finally {
            DatabaseConnector.returnConnection(con);
        }
    }




    protected boolean executeUpdate(String sql, Object... parameters){
        Connection con = null;
        try{
            con = DatabaseConnector.getDatabaseConnection();
            try(PreparedStatement stmt = con.prepareStatement(sql)){
                for(int i = 0; i < parameters.length; i++){
                    stmt.setObject(i + 1, parameters[i]);
                }

                return stmt.executeUpdate() > 0;
            }
        } catch(SQLException e){
            throw new RuntimeException("Error in db", e);
        } finally {
            DatabaseConnector.returnConnection(con);
        }
    }


    protected <T> Optional<T> executeQuerySingle(String sql, RowMapper<T> mapper, Object... parameters){
        Connection con = null;
        try{
            con = DatabaseConnector.getDatabaseConnection();
            try(PreparedStatement stmt = con.prepareStatement(sql)){
                for(int i = 0; i < parameters.length; i++){
                    stmt.setObject(i + 1, parameters[i]);
                }
                ResultSet resultSet = stmt.executeQuery();
                if(resultSet.next()){
                    return Optional.of(mapper.mapRow(resultSet));
                } else{
                    return Optional.empty();
                }
                }
            }
        catch (SQLException e){
            throw new RuntimeException("Db error", e);
        } finally {
            DatabaseConnector.returnConnection(con);
        }
    }


    protected <T> List<T> executeQueryList(String sql, RowMapper<T> mapper, Object... parameters){
        Connection con = null;
        try{
            con = DatabaseConnector.getDatabaseConnection();
            try(PreparedStatement stmt = con.prepareStatement(sql)){
                for(int i = 0; i < parameters.length; i++){
                    stmt.setObject(i + 1, parameters[i]);
                }
                ResultSet resultSet = stmt.executeQuery();
                List<T> list = new ArrayList<>();
                while(resultSet.next()){
                    list.add(mapper.mapRow(resultSet));
                }
                return list;
            }
        } catch (SQLException e){
            throw new RuntimeException("Error in db", e);
        } finally {
            DatabaseConnector.returnConnection(con);
        }
    }



    public void executeUpdateWithConnection(Connection con, String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();

        }
    }

    protected boolean executeExistsWithConnection(Connection con, String sql, Object... parameters) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                stmt.setObject(i + 1, parameters[i]);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
