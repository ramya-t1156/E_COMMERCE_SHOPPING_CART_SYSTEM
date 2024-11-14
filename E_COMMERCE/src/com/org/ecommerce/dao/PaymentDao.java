package com.org.ecommerce.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.org.ecommerce.model.Payment;
import com.org.ecommerce.util.DataBaseConnection;

public class PaymentDao {

    private Connection connection;

    // Constructor
    public PaymentDao() {
        try {
            this.connection = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    // Constructor for passing the connection (for testing or dependency injection)
    public PaymentDao(Connection connection) {
        this.connection = connection;
    }

    // Method to save payment to the database
    public boolean savePayment(Payment payment) {
        // Ensure the status is a valid ENUM value
        String status = payment.getStatus();
        if (!status.equals("pending") && !status.equals("completed") && !status.equals("failed")) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }

        String query = "INSERT INTO payments (order_id, payment_method, amount, payment_status, payment_date) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            stmt.setInt(1, payment.getOrderId());
            stmt.setString(2, payment.getPaymentMethod());
            stmt.setDouble(3, payment.getAmount());
            stmt.setString(4, payment.getStatus());
            stmt.setTimestamp(5, payment.getPaymentDate());

            // Execute the insert query and check if the payment was saved
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();  // Log error (should improve logging in production)
            return false;
        }
    }


    // Method to retrieve payment details by order ID
    public Payment getPaymentByOrderId(int orderId) {
        String query = "SELECT * FROM payments WHERE order_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Create and return Payment object from result set
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setOrderId(rs.getInt("order_id"));
                payment.setPaymentMethod(rs.getString("payment_method"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setStatus(rs.getString("payment_status"));
                payment.setPaymentDate(rs.getTimestamp("payment_date"));

                return payment;
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log error
        }

        return null;  // Return null if no payment found
    }
    
 // Method to get the payment method by user ID (from Orders table)
    public String getPaymentMethodByUserId(int userId) throws SQLException {
        String query = "SELECT payment_method FROM Orders WHERE user_id = ? ORDER BY order_date DESC LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("payment_method");
            } else {
                return null;  // No payment method found for this user
            }
        }
    }

    // Method to get the shipping address by user ID (from Orders table)
    public String getShippingAddressByUserId(int userId) throws SQLException {
        String query = "SELECT shipping_address FROM Orders WHERE user_id = ? ORDER BY order_date DESC LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("shipping_address");
            } else {
                return null;  // No shipping address found for this user
            }
        }
    }

    // Getter and setter for connection (useful for dependency injection or unit testing)
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
