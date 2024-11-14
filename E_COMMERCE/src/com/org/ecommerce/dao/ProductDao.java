package com.org.ecommerce.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.org.ecommerce.model.Product;
import com.org.ecommerce.util.DataBaseConnection;

public class ProductDao {

    private Connection connection;

    // Constructor that requires a Connection instance to be passed in
    public ProductDao(Connection connection) {
        this.connection = connection;
    }
    public ProductDao() {
    	try {
            this.connection = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    // Add a product to the database
    public void addProduct(Product product) {
        String sql = "INSERT INTO Products (name, category, price, stock_quantity, description, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setString(5, product.getDescription());
            stmt.setTimestamp(6, product.getCreatedAt());
            stmt.setTimestamp(7, product.getUpdatedAt());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update product information
    public boolean updateProduct(Product product) {
        String sql = "UPDATE Products SET name = ?, category = ?, price = ?, stock_quantity = ?, description = ?, updated_at = ? WHERE product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setString(5, product.getDescription());
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis())); // Updating timestamp
            stmt.setInt(7, product.getProductId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a product by ID
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM Products WHERE product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get a product by its ID
    public Product getProductById(int productId) {
        String sql = "SELECT * FROM Products WHERE product_id = ?";
        Product product = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setDescription(rs.getString("description"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    // Get all products from the database
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM Products";
        List<Product> productList = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setDescription(rs.getString("description"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    // Get product price by ID
    public double getProductPriceById(int productId) {
        String sql = "SELECT price FROM Products WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;  // Return 0.0 if product price is not found
    }

    // Getter for connection (optional if not needed outside the class)
    public Connection getConnection() {
        return connection;
    }

    // Setter for connection if needed to set after object instantiation
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void updateInventory(int productId, int quantity) {
        String sql = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
