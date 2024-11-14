package com.org.ecommerce.dao;

import java.sql.*;
import com.org.ecommerce.model.User;
import com.org.ecommerce.util.DataBaseConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserDao {

    private Connection connection;

    // Constructors
    public UserDao() {
    	try {
            this.connection = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    // Register a new user
    public boolean addUser(User user) {
        String sql = "INSERT INTO Users (username, password, email, name, created_at) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());  // Hash the password before saving
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getName());
            stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));  // Set creation time

            return stmt.executeUpdate() > 0;  // Return true if the user is added successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if username is already taken
    public boolean isUsernameTaken(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;  // Return true if username exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Return false if username does not exist
    }

    // Authenticate user login
    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        User user = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, hashPassword(password));  // Hash the password before checking
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = mapResultSetToUser(rs);  // Use helper method to map ResultSet to User
                updateLastLogin(user.getUserId());  // Update last login time
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // Get user details by username
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        User user = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = mapResultSetToUser(rs);  // Use helper method to map ResultSet to User
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // Get user details by userId
    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        User user = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = mapResultSetToUser(rs);  // Use helper method to map ResultSet to User
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // Update the last login timestamp
    private void updateLastLogin(int userId) {
        String sql = "UPDATE Users SET last_login = ? WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));  // Set the current time
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Utility method to hash the password
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");  // Use SHA-256 hash algorithm
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();  // Return the hashed password in hexadecimal format
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;  // Return null if there was an error hashing the password
        }
    }

    // Helper method to map ResultSet to User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }

    // Getter and Setter for connection
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
