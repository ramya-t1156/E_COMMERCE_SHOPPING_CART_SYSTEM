package com.org.ecommerce.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.org.ecommerce.model.CartItem;

public class CartItemDao {
    
    private Connection connection;

    // Default constructor
    public CartItemDao() {
    	try {
            // Replace with your actual database URL, username, and password
            String url = "jdbc:mysql://localhost:3306/ecommerce_cart_system";
            String username = "root";
            String password = "Rxmyah@1156";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database");
        }
    }

    // Constructor with a connection parameter
    public CartItemDao(Connection connection) {
        this.setConnection(connection);
    }

    // Add a CartItem to the database
    public void addCartItem(CartItem cartItem) {
        String sql = "INSERT INTO CartItems (user_id, product_id, quantity, added_at) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cartItem.getUserId());
            statement.setInt(2, cartItem.getProductId());
            statement.setInt(3, cartItem.getQuantity());
            
            // Set the added_at value using the Timestamp of createdAt
            statement.setTimestamp(4, cartItem.getCreatedAt());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cart item added successfully.");
            } else {
                System.out.println("Failed to add cart item.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Update a CartItem in the database
    public void updateCartItem(CartItem cartItem) {
        String sql = "UPDATE CartItems SET quantity = ?, updated_at = ? WHERE cart_item_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cartItem.getQuantity());
            
            statement.setInt(3, cartItem.getCartItemId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cart item updated successfully.");
            } else {
                System.out.println("Failed to update cart item.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Delete a CartItem from the database
    public void deleteCartItem(int cartItemId) {
        String sql = "DELETE FROM CartItems WHERE cart_item_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cartItemId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cart item deleted successfully.");
            } else {
                System.out.println("Failed to delete cart item.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get CartItems by user ID
    public List<CartItem> getCartItemsByUserId(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM CartItems WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId); // Set user ID parameter

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setCartItemId(resultSet.getInt("cart_item_id"));
                cartItem.setUserId(resultSet.getInt("user_id"));
                cartItem.setProductId(resultSet.getInt("product_id"));
                cartItem.setQuantity(resultSet.getInt("quantity"));

                // Set created_at
                Timestamp createdAtTimestamp = resultSet.getTimestamp("added_at");
                if (createdAtTimestamp != null) {
                    cartItem.setCreatedAt(createdAtTimestamp);
                }

                cartItems.add(cartItem); // Add the CartItem to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException
        }

        return cartItems; // Return the list of CartItems
    }

    public double getProductPriceById(int productId) {
        String sql = "SELECT price FROM Products WHERE product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;  // Return 0.0
    }
    // Get the database connection
    public Connection getConnection() {
        return connection;
    }

    // Set the database connection
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
