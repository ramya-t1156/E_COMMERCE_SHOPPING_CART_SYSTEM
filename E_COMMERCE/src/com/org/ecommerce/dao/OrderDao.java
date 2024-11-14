package com.org.ecommerce.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.org.ecommerce.model.Order;
import com.org.ecommerce.model.OrderItem;
import com.org.ecommerce.util.DataBaseConnection;

public class OrderDao {
    
    private Connection connection;

    // Default constructor initializes the connection
    public OrderDao() {
        try {
            connection = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Constructor with connection parameter (for unit testing)
    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    // Create an order in the database
    public int createOrder(Order order) {
        String orderSql = "INSERT INTO Orders (user_id, total_price, status, payment_method, shipping_address) VALUES (?, ?, ?, ?, ?)";
        String itemSql = "INSERT INTO OrderItems (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        int orderId = 0;

        try (PreparedStatement orderStatement = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
            // Insert order details
            orderStatement.setInt(1, order.getUserId());
            orderStatement.setDouble(2, order.getTotalPrice());
            orderStatement.setString(3, order.getStatus());
            orderStatement.setString(4, order.getPaymentMethod());
            orderStatement.setString(5, order.getShippingAddress());

            // Execute the order insert and retrieve the generated order_id
            int affectedRows = orderStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1); // Get the generated order_id
                    }
                }

                // Now insert each order item associated with the order
                for (OrderItem item : order.getOrderItems()) {
                    try (PreparedStatement itemStatement = connection.prepareStatement(itemSql)) {
                        itemStatement.setInt(1, orderId); // Associate item with the generated orderId
                        itemStatement.setInt(2, item.getProductId());
                        itemStatement.setInt(3, item.getQuantity());
                        itemStatement.setDouble(4, item.getPrice());
                        itemStatement.executeUpdate(); // Insert order item
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderId;  // Return the generated orderId
    }

    // Get order by orderId
    public Order getOrderById(int orderId) {
        Order order = null;
        String orderSql = "SELECT * FROM Orders WHERE order_id = ?";
        String itemSql = "SELECT * FROM OrderItems WHERE order_id = ?";

        try (PreparedStatement orderStatement = connection.prepareStatement(orderSql)) {
            orderStatement.setInt(1, orderId);
            ResultSet resultSet = orderStatement.executeQuery();

            if (resultSet.next()) {
                order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setUserId(resultSet.getInt("user_id"));
                order.setTotalPrice(resultSet.getDouble("total_price"));
                order.setStatus(resultSet.getString("status"));
                order.setPaymentMethod(resultSet.getString("payment_method"));
                order.setShippingAddress(resultSet.getString("shipping_address"));

                // Retrieve order items
                List<OrderItem> orderItems = new ArrayList<>();
                try (PreparedStatement itemStatement = connection.prepareStatement(itemSql)) {
                    itemStatement.setInt(1, orderId);
                    ResultSet itemResultSet = itemStatement.executeQuery();
                    while (itemResultSet.next()) {
                        OrderItem item = new OrderItem();
                        item.setOrderItemId(itemResultSet.getInt("order_item_id"));
                        item.setProductId(itemResultSet.getInt("product_id"));
                        item.setQuantity(itemResultSet.getInt("quantity"));
                        item.setPrice(itemResultSet.getDouble("price"));
                        orderItems.add(item);
                    }
                }
                order.setOrderItems(orderItems);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    // Get all orders for a user
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String orderSql = "SELECT * FROM Orders WHERE user_id = ?";
        String itemSql = "SELECT * FROM OrderItems WHERE order_id = ?";

        try (PreparedStatement orderStatement = connection.prepareStatement(orderSql)) {
            orderStatement.setInt(1, userId);
            ResultSet resultSet = orderStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setUserId(resultSet.getInt("user_id"));
                order.setTotalPrice(resultSet.getDouble("total_price"));
                order.setStatus(resultSet.getString("status"));
                order.setPaymentMethod(resultSet.getString("payment_method"));
                order.setShippingAddress(resultSet.getString("shipping_address"));

                // Retrieve order items for this order
                List<OrderItem> orderItems = new ArrayList<>();
                try (PreparedStatement itemStatement = connection.prepareStatement(itemSql)) {
                    itemStatement.setInt(1, order.getOrderId());
                    ResultSet itemResultSet = itemStatement.executeQuery();
                    while (itemResultSet.next()) {
                        OrderItem item = new OrderItem();
                        item.setOrderItemId(itemResultSet.getInt("order_item_id"));
                        item.setProductId(itemResultSet.getInt("product_id"));
                        item.setQuantity(itemResultSet.getInt("quantity"));
                        item.setPrice(itemResultSet.getDouble("price"));
                        orderItems.add(item);
                    }
                }
                order.setOrderItems(orderItems);
                orders.add(order);  // Add this order to the orders list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;  // Return all orders for this user
    }

    // Update the status of an order
    public void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE Orders SET status = ? WHERE order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an order by its ID
    public void deleteOrder(int orderId) {
        String deleteItemsSql = "DELETE FROM OrderItems WHERE order_id = ?";
        String deleteOrderSql = "DELETE FROM Orders WHERE order_id = ?";

        try (PreparedStatement deleteItemsStatement = connection.prepareStatement(deleteItemsSql);
             PreparedStatement deleteOrderStatement = connection.prepareStatement(deleteOrderSql)) {

            // First delete all items associated with the order
            deleteItemsStatement.setInt(1, orderId);
            deleteItemsStatement.executeUpdate();

            // Then delete the order itself
            deleteOrderStatement.setInt(1, orderId);
            deleteOrderStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Placeholder saveOrder method (ensure you replace this method with proper logic)
    public void saveOrder(int productId, int quantity) {
        String sql = "INSERT INTO Orders (product_id, quantity) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
