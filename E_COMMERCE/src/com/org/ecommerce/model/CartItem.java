package com.org.ecommerce.model;

import java.sql.Timestamp;

public class CartItem {
    private int cartItemId;
    private int userId;
    private int productId;
    private int quantity;
	private Timestamp createdAt;

    // Constructors
    public CartItem() {}

    public CartItem(int cartItemId, int userId, int productId, int quantity) {
        this.cartItemId = cartItemId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.createdAt = new Timestamp(System.currentTimeMillis());  // Set created_at to current time
    }

    // Getters and Setters
    public int getCartItemId() { return cartItemId; }
    public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    // Override toString method to print meaningful CartItem information
    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                '}';
    }
}
