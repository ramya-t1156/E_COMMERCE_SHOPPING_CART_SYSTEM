package com.org.ecommerce.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.org.ecommerce.dao.OrderDao;
import com.org.ecommerce.dao.ProductDao;  // Assuming you have a ProductDao to fetch product prices
import com.org.ecommerce.model.CartItem;
import com.org.ecommerce.model.Order;
import com.org.ecommerce.model.OrderItem;

public class OrderService {

    private OrderDao orderDao;
    private CartService cartService;
    private PaymentService paymentService;
    private ProductDao productDao;  // You need this to fetch product prices
	private Connection connection;

    // Constructor with dependencies (OrderDao, CartService, PaymentService, ProductDao)
    public OrderService() {}

    public OrderService(CartService cartService, ProductDao productDao, OrderDao orderDao, PaymentService paymentService) {
        this.cartService = cartService;
        this.productDao = productDao;
        this.orderDao = orderDao;
        this.paymentService = paymentService;
    }
    
    public OrderService(Connection connection, CartService cartService, PaymentService paymentService, ProductDao productDao) {
        this.connection = connection;
        this.cartService = cartService;
        this.paymentService = paymentService;
        this.productDao = productDao;
    }
    // Method to place an order
    public int placeOrder(int userId) {
        List<CartItem> cartItems = null;
        try {
            cartItems = cartService.viewCart(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // or handle as necessary
        }

        double totalPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            double productPrice = productDao.getProductPriceById(cartItem.getProductId());
            totalPrice += productPrice * cartItem.getQuantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(productPrice);
            orderItems.add(orderItem);
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setStatus("Pending");
        order.setPaymentMethod(paymentService.getPaymentMethod(userId));
        order.setShippingAddress(paymentService.getShippingAddress(userId));
        order.setOrderItems(orderItems);

        return orderDao.createOrder(order);
    }
    // Method to view the order history for a user
    public List<Order> viewOrderHistory(int userId) {
        // Fetch the list of orders placed by the user
        return orderDao.getOrdersByUserId(userId);
    }

    // Method to update the status of an order
    public void updateOrderStatus(int orderId, String status) {
        // Update the status of the order in the database
        orderDao.updateOrderStatus(orderId, status);
    }

    // Getters and Setters
    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public CartService getCartService() {
        return cartService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
