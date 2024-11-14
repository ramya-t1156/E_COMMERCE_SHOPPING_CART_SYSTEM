package com.org.ecommerce.service;

import java.sql.Connection;

import com.org.ecommerce.dao.PaymentDao;
import com.org.ecommerce.model.Payment;

public class PaymentService {

    private PaymentDao paymentDao;
	private Connection connection;

    // Default constructor
    public PaymentService() {
        this.paymentDao = new PaymentDao();  // Initialize PaymentDao by default
    }

    // Constructor with PaymentDao dependency for dependency injection
    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public PaymentService(Connection connection) {
		// TODO Auto-generated constructor stub
    	this.connection = connection;
	}

	// Method to process payment
    public boolean processPayment(Payment payment) {
        // Logic for payment processing (dummy logic for now)
        try {
            return paymentDao.savePayment(payment);  // Assuming savePayment() is defined in PaymentDao
        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
            return false;  // Return false if payment processing fails
        }
    }

    // Method to get payment details by order ID
    public Payment getPaymentDetails(int orderId) {
        try {
            return paymentDao.getPaymentByOrderId(orderId);  // Assuming getPaymentByOrderId() is defined in PaymentDao
        } catch (Exception e) {
            System.err.println("Error retrieving payment details for order ID " + orderId + ": " + e.getMessage());
            return null;  // Return null if there's an error fetching payment details
        }
    }

    // Method to get payment method for a user
    public String getPaymentMethod(int userId) {
        // Fetch payment method for the user from PaymentDao
        try {
            return paymentDao.getPaymentMethodByUserId(userId);  // Assuming getPaymentMethodByUserId() is defined in PaymentDao
        } catch (Exception e) {
            System.err.println("Error retrieving payment method for user ID " + userId + ": " + e.getMessage());
            return null;  // Return null if there's an error fetching the payment method
        }
    }

    // Method to get shipping address for a user
    public String getShippingAddress(int userId) {
        // Fetch shipping address for the user from PaymentDao
        try {
            return paymentDao.getShippingAddressByUserId(userId);  // Assuming getShippingAddressByUserId() is defined in PaymentDao
        } catch (Exception e) {
            System.err.println("Error retrieving shipping address for user ID " + userId + ": " + e.getMessage());
            return null;  // Return null if there's an error fetching the shipping address
        }
    }
    
    

    // Getter and Setter for PaymentDao
    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }
}
