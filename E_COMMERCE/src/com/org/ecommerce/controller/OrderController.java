package com.org.ecommerce.controller;

import java.sql.Connection;
import java.util.List;

import com.org.ecommerce.dao.ProductDao;
import com.org.ecommerce.model.Order;
import com.org.ecommerce.service.CartService;
import com.org.ecommerce.service.OrderService;
import com.org.ecommerce.service.PaymentService;

public class OrderController {
    private OrderService orderService;

    // Default constructor
    public OrderController() {
        this.orderService = new OrderService(); // Initializing the OrderService
    }

    // Constructor with OrderService dependency injection
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    // Constructor with all dependencies for OrderService
    public OrderController(Connection connection, CartService cartService, PaymentService paymentService, ProductDao productDao) {
        this.orderService = new OrderService(connection, cartService, paymentService, productDao);
    }

    // Method to place an order
    public void placeOrder(int userId) {
        try {
            int orderId = orderService.placeOrder(userId);
            System.out.println("Order placed successfully with Order ID: " + orderId);
        } catch (Exception e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
    }

    // Method to view order history for a given user
    public void viewOrderHistory(int userId) {
        try {
            List<Order> orders = orderService.viewOrderHistory(userId);
            if (orders.isEmpty()) {
                System.out.println("You have no orders in your history.");
            } else {
                orders.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving order history: " + e.getMessage());
        }
    }

    // Method to update the status of an order
    public void updateOrderStatus(int orderId, String status) {
        try {
            orderService.updateOrderStatus(orderId, status);
            System.out.println("Order status updated to: " + status);
        } catch (Exception e) {
            System.out.println("Error updating order status: " + e.getMessage());
        }
    }
}
