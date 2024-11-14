package com.org.ecommerce.model;

import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private double totalPrice;
    private String status;
    private String paymentMethod;
    private String shippingAddress;
    private List<OrderItem> orderItems; // List to store order items

    // Default constructor
    public Order() {}

    // Constructor with parameters for easy object creation
    public Order(int orderId, int userId, double totalPrice, String status, String paymentMethod, 
                 String shippingAddress, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice > 0 ? totalPrice : 0.0;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        if (totalPrice > 0) {
            this.totalPrice = totalPrice;
        } else {
            System.out.println("Invalid totalPrice. It must be positive.");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{orderId=" + orderId + ", userId=" + userId + ", totalPrice=" + totalPrice
                + ", status='" + status + "', paymentMethod='" + paymentMethod + "', shippingAddress='" + shippingAddress
                + "', orderItems=" + orderItems + "}";
    }
}
