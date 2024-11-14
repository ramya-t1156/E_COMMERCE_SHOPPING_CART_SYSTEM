package com.org.ecommerce.controller;

import java.util.List;

import com.org.ecommerce.model.Product;
import com.org.ecommerce.service.ProductService;

public class ProductController {
    private ProductService productService;

    // Default constructor
    public ProductController() {
        this.productService = new ProductService();
    }

    // Constructor with dependency injection for ProductService
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Add product
    public void addProduct(Product product) {
        try {
            productService.addProduct(product);
            System.out.println("Product added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    // Update product
    public void updateProduct(Product product) {
        try {
            boolean isUpdated = productService.updateProduct(product);
            if (isUpdated) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Product not found or update failed.");
            }
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    // Delete product
    public void deleteProduct(int productId) {
        try {
            boolean isDeleted = productService.deleteProduct(productId);
            if (isDeleted) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found or deletion failed.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }

    // View all products
    public void viewAllProducts() {
        try {
            List<Product> products = productService.viewAllProducts();
            if (products.isEmpty()) {
                System.out.println("No products available.");
            } else {
                products.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
    }
}
