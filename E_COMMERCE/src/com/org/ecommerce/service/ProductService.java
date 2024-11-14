package com.org.ecommerce.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.org.ecommerce.dao.ProductDao;
import com.org.ecommerce.model.Product;
import com.org.ecommerce.util.DataBaseConnection;

public class ProductService {

    private ProductDao productDao;
    private Connection connection;

    // Default constructor
    public ProductService() {
        try {
            this.connection = DataBaseConnection.getConnection();  // Initialize connection
            this.productDao = new ProductDao(connection);  // Pass connection to ProductDao
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Constructor with ProductDao dependency for dependency injection
    public ProductService(ProductDao productDao) {
        this.setProductDao(productDao);
    }

    public ProductService(Connection connection) {
		// TODO Auto-generated constructor stub
    	this.connection = connection;
	}

	// Method to view all products
    public List<Product> viewAllProducts() {
        return productDao.getAllProducts();
    }

    // Method to add a new product
    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    // Method to update an existing product
    public boolean updateProduct(Product product) {
        return productDao.updateProduct(product);
    }

    // Method to delete a product by its ID
    public boolean deleteProduct(int productId) {
        return productDao.deleteProduct(productId);
    }

    // Getter for ProductDao
    public ProductDao getProductDao() {
        return productDao;
    }

    // Setter for ProductDao
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
