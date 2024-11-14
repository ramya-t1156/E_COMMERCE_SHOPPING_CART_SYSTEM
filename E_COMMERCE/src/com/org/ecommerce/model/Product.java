package com.org.ecommerce.model;

import java.sql.Timestamp;

public class Product {
    private int productId;
    private String name;
    private String category;
    private double price;
    private int stockQuantity;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Product() {}

    public Product(int productId, String name, String category, double price, int stockQuantity, 
                   String description) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.createdAt = new Timestamp(System.currentTimeMillis());  // Set created_at to current time
        this.updatedAt = this.createdAt;
    }

    // Getters and Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    
    @Override
    public String toString() {
        return "Product ID: " + this.productId + "\n" +
               "Product Name: " + this.name + "\n" +
               "Category: " + this.category + "\n" +
               "Price: " + this.price + "\n" +
               "Stock: " + this.stockQuantity + "\n" +
               "Description: " + this.description + "\n---------------------------------------------------\n";
    }
}
