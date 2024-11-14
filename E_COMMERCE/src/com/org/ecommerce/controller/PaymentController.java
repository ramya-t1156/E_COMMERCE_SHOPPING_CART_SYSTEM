package com.org.ecommerce.controller;

import com.org.ecommerce.model.Payment;
import com.org.ecommerce.service.PaymentService;

public class PaymentController {
    private PaymentService paymentService = new PaymentService();
    public PaymentController() {}
 // Default constructor

    // Constructor with dependency injection for PaymentService
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    public void processPayment(Payment payment) {
        try {
            boolean isSuccess = paymentService.processPayment(payment);
            if (isSuccess) {
                System.out.println("Payment processed successfully for Order ID: " + payment.getOrderId());
            } else {
                System.out.println("Payment failed. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while processing the payment: " + e.getMessage());
        }
    }

    public void getPaymentDetails(int orderId) {
        try {
            Payment payment = paymentService.getPaymentDetails(orderId);
            if (payment != null) {
                System.out.println("Payment details for Order ID " + orderId + ": " + payment);
            } else {
                System.out.println("No payment found for Order ID: " + orderId);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving payment details: " + e.getMessage());
        }
    }
}

