package com.org.ecommerce.model;

public class OrderItem {
    private int productId;
    private int quantity;
    private double price;
    private int orderItemId;

    // Constructor
    public OrderItem(int productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItem() {
		// TODO Auto-generated constructor stub
	}

	// Getters and setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    // Getters and setters
    public int getOrderItemId() {  // Getter for orderItemId
		return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {  // Setter for orderItemId
        this.orderItemId = orderItemId;
    }
    @Override
    public String toString() {
        return "OrderItem{productId=" + this.productId + ", quantity=" + this.quantity + ", price=" + this.price + "}";
    }

}
