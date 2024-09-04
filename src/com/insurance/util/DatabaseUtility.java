package com.insurance.util;

import com.insurance.exception.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtility {

    private static final String URL = "jdbc:mysql://localhost:3306/insurancedb";
    private static final String USER = "root";
    private static final String PASSWORD = "tanjiro@23";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws DatabaseConnectionException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to connect to the database", e);
        }
    }
}
