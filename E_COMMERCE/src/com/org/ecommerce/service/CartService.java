package com.org.ecommerce.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.org.ecommerce.dao.CartItemDao;
import com.org.ecommerce.dao.ProductDao;
import com.org.ecommerce.model.CartItem;
import com.org.ecommerce.util.DataBaseConnection;

public class CartService {

    private CartItemDao cartItemDao;
    private ProductDao productDao;
    
    // Constructor Injection for CartItemDao and ProductDao
    public CartService() {
        try {
            // Create the connection internally
            Connection connection = DataBaseConnection.getConnection();
            this.cartItemDao = new CartItemDao(connection);  // Initialize with connection
            this.productDao = new ProductDao(connection);    // Initialize productDao if needed
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database");
        }
    }

    public CartService(Connection connection) {
        this.cartItemDao = new CartItemDao(connection);  // Pass the connection
        this.productDao = new ProductDao(connection);  // If you need the connection for ProductDao as well
    }

    public CartService(CartItemDao cartItemDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }
    
    // Add product to the cart, or update quantity if already exists
    public void addProductToCart(int userId, int productId, int quantity) throws SQLException {
        // Check if the user exists (optional but recommended)
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        // Get all the items in the cart for the logged-in user
        List<CartItem> existingItems = cartItemDao.getCartItemsByUserId(userId);
        boolean productExists = false;

        // Check if the product already exists in the cart
        for (CartItem cartItem : existingItems) {
            if (cartItem.getProductId() == productId) {
                // Product exists, update the quantity
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemDao.updateCartItem(cartItem);  // Update in DB
                productExists = true;
                break;
            }
        }

        // If product doesn't exist in cart, add a new item
        if (!productExists) {
            // Create a new CartItem with a temporary ID (assuming DB will auto-generate it)
            CartItem newCartItem = new CartItem(0, userId, productId, quantity); // Ensure constructor is correct
            cartItemDao.addCartItem(newCartItem);
        }
    }


    // Remove product from the cart
    public void removeProductFromCart(int cartItemId) throws SQLException {
        cartItemDao.deleteCartItem(cartItemId);
    }

    // View all items in the cart for a user
    public List<CartItem> viewCart(int userId) throws SQLException {
        // Ensure cartItemDao is not null
        if (cartItemDao != null) {
            return cartItemDao.getCartItemsByUserId(userId);
        } else {
            System.out.println("CartItemDao is not initialized properly.");
            return new ArrayList<>();
        }
    }

    // Calculate total price of all products in the cart
    public double calculateCartTotal(int userId) throws SQLException {
        double total = 0;
        List<CartItem> cartItems = cartItemDao.getCartItemsByUserId(userId);

        for (CartItem cartItem : cartItems) {
            // Fetch the price of the product from ProductDao
            double productPrice = productDao.getProductPriceById(cartItem.getProductId());
            total += productPrice * cartItem.getQuantity();  // Total = price * quantity
        }
        return total;
    }

    // Getters and Setters
    public CartItemDao getCartItemDao() {
        return cartItemDao;
    }

    public void setCartItemDao(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
