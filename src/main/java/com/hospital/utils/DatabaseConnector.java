package com.hospital.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DatabaseConnector {


    private static BlockingQueue<Connection> connectionPool;
    private static final int POOL_SIZE = 5;

    static {
        try {
            System.out.println("Initializing Connection Pool...");
            connectionPool = new ArrayBlockingQueue<>(POOL_SIZE);
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");

            if(dbUrl == null) throw new RuntimeException("ENV variables not set!");

            Class.forName("com.mysql.cj.jdbc.Driver");

            for (int i = 0; i < POOL_SIZE; i++) {
                Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                connectionPool.offer(con);
            }
            System.out.println("Pool Initialized with " + POOL_SIZE + " connections.");

        } catch (Exception e) {
            throw new RuntimeException("Fatal Error: Could not init DB Pool", e);
        }
    }

    public static Connection getDatabaseConnection() {
        try {
            return connectionPool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error waiting for connection", e);
        }
    }

    public static void returnConnection(Connection con) {
        if (con != null) {
            connectionPool.offer(con);
        }
    }
}