package com.org.ecommerce.controller;

import java.util.List;

import com.org.ecommerce.model.CartItem;
import com.org.ecommerce.service.CartService;

public class CartController {
    private CartService cartService;

    // Default constructor
    public CartController() {
        this.cartService = new CartService(); // Initializing the cart service
    }

    // Constructor with CartService dependency injection
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Method to add product to the cart
    public void addProductToCart(int userId, int productId, int quantity) {
        try {
            cartService.addProductToCart(userId, productId, quantity);
            System.out.println("Product added to cart.");
        } catch (Exception e) {
            System.out.println("Error adding product to cart: " + e.getMessage());
        }
    }

    // Method to remove product from the cart
    public void removeProductFromCart(int cartItemId) {
        try {
            cartService.removeProductFromCart(cartItemId);
            System.out.println("Product removed from cart.");
        } catch (Exception e) {
            System.out.println("Error removing product from cart: " + e.getMessage());
        }
    }

    // Method to view the cart for a given user
    public void viewCart(int userId) {
        try {
            List<CartItem> cartItems = cartService.viewCart(userId);
            if (cartItems.isEmpty()) {
                System.out.println("Your cart is empty.");
            } else {
                cartItems.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error viewing cart: " + e.getMessage());
        }
    }

    // Method to calculate the total value of items in the cart
    public void calculateCartTotal(int userId) {
        try {
            double total = cartService.calculateCartTotal(userId);
            System.out.println("Total cart value: " + total);
        } catch (Exception e) {
            System.out.println("Error calculating cart total: " + e.getMessage());
        }
    }
}
