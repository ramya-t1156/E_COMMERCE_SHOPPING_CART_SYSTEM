package com.org.ecommerce.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceService {

    private Connection connection;

    // Constructor with connection injection
    public BalanceService(Connection connection) {
        this.connection = connection;
    }

    // Method to get the current balance of a user
    public double getBalance(int userId) {
        double balance = 0.0;
        String query = "SELECT balance FROM UserBalance WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                balance = resultSet.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    // Method to update the balance after a transaction (e.g., after a purchase)
    public boolean updateBalance(int userId, double amount) {
        boolean updated = false;
        String query = "UPDATE UserBalance SET balance = balance + ? WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    // Method to deduct balance (e.g., for a purchase or order)
    public boolean deductBalance(int userId, double amount) {
        boolean deducted = false;
        String query = "UPDATE UserBalance SET balance = balance - ? WHERE user_id = ? AND balance >= ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            statement.setDouble(3, amount);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                deducted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deducted;
    }

    // Method to add funds to a user's balance (e.g., deposit or refund)
    public boolean addFunds(int userId, double amount) {
        boolean added = false;
        String query = "UPDATE UserBalance SET balance = balance + ? WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                added = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return added;
    }

    // Method to check if a user has sufficient balance for a transaction
    public boolean hasSufficientBalance(int userId, double amount) {
        double currentBalance = getBalance(userId);
        return currentBalance >= amount;
    }
}
