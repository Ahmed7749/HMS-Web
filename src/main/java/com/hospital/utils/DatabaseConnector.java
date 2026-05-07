package com.hospital.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {

    private static HikariDataSource dataSource;

    static {
        try {
            System.out.println("Initializing HikariCP Connection Pool...");

            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");

            if(dbUrl == null) throw new RuntimeException("ENV variables not set!");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            config.setUsername(dbUser);
            config.setPassword(dbPassword);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");


            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(5000);
            config.setIdleTimeout(30000);

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);
            System.out.println("HikariCP Pool Initialized Successfully.");

        } catch (Exception e) {
            throw new RuntimeException("Fatal Error: Could not init HikariCP Pool", e);
        }
    }

    public static Connection getDatabaseConnection() {
        try {

            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting connection from Hikari pool", e);
        }
    }

    public static void returnConnection(Connection con) {
        if (con != null) {
            try {

                con.close();
            } catch (SQLException e) {
                System.err.println("Failed to return connection to pool: " + e.getMessage());
            }
        }
    }
}