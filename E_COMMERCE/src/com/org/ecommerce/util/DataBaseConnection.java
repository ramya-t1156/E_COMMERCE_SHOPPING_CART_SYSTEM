package com.org.ecommerce.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_cart_system", "root", "Rxmyah@1156may");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database. Please check the credentials and try again.");
            throw e;  // Rethrow to handle it at a higher level if needed
        }
    }
}
