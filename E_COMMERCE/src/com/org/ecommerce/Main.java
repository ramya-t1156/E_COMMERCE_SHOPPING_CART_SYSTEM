package com.org.ecommerce;

import java.sql.SQLException;
import com.org.ecommerce.util.DataBaseConnection;
import com.org.ecommerce.dao.OrderDao;
import com.org.ecommerce.dao.ProductDao;
import com.org.ecommerce.model.*;
import com.org.ecommerce.service.*;
import java.sql.*;
import java.util.Scanner;

public class Main {

    // Global admin password (you can change this to any desired password)
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        // Creating scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Establishing connection to the database
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (connection == null) {
            System.out.println("Failed to connect to the database.");
            return;  // Exit if the connection fails
        }

        // Initializing services
        UserService userService = new UserService();
        ProductService productService = new ProductService();
        CartService cartService = new CartService();
        ProductDao productDao = new ProductDao();
        OrderDao orderDao = new OrderDao();
        PaymentService paymentService = new PaymentService();

        OrderService orderService = new OrderService(cartService, productDao, orderDao, paymentService);

        User loggedInUser = null;

        while (true) {
            // Display menu options
            System.out.println("\nWelcome to the E-Commerce System!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Add Product (Admin only)");
            System.out.println("4. View Products");
            System.out.println("5. Add Product to Cart");
            System.out.println("6. View Cart");
            System.out.println("7. Place Order");
            System.out.println("8. Make Payment");
            System.out.println("9. View Order History");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // Register a new user
                    System.out.print("Enter userId : ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter address: ");
                    String address = scanner.nextLine();

                    User newUser = new User(userId, username, password, name, email, address, "user");
                    userService.registerUser(newUser);
                    System.out.println("Registration successful!");
                    break;

                case 2:
                    // Login user
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    loggedInUser = userService.login(loginUsername, loginPassword);
                    if (loggedInUser != null) {
                        System.out.println("Login successful! Welcome, " + loggedInUser.getName());
                        System.out.println("Last login: " + loggedInUser.getLastLogin());
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case 3:
                    // Add Product (Admin only)
                    if (loggedInUser != null) {
                        System.out.print("Enter your role (user/admin): ");
                        String userRole = scanner.nextLine().trim();

                        if (userRole.equalsIgnoreCase("admin")) {
                            // Ask for the admin password
                            System.out.print("Enter admin password: ");
                            String enteredAdminPassword = scanner.nextLine();

                            if (enteredAdminPassword.equals(ADMIN_PASSWORD)) {
                                System.out.print("Enter productId: ");
                                int productId = scanner.nextInt();
                                scanner.nextLine();  // Consume newline
                                System.out.print("Enter product name: ");
                                String productName = scanner.nextLine();
                                System.out.print("Enter product category: ");
                                String category = scanner.nextLine();
                                System.out.print("Enter product price: ");
                                double price = scanner.nextDouble();
                                System.out.print("Enter product stock quantity: ");
                                int stock = scanner.nextInt();
                                scanner.nextLine();  // Consume newline
                                System.out.print("Enter product description: ");
                                String description = scanner.nextLine();

                                Product newProduct = new Product(productId, productName, category, price, stock, description);
                                productService.addProduct(newProduct);
                                System.out.println("Product added successfully.");
                            } else {
                                System.out.println("Incorrect admin password. Access denied.");
                            }
                        } else {
                            System.out.println("Only admins are allowed to add products. Access denied.");
                        }
                    } else {
                        System.out.println("You need to be logged in to add products.");
                    }
                    break;

                case 4:
                    // View all products
                    System.out.println("Products available: ");
                    for (Product product : productService.viewAllProducts()) {
                        System.out.println(product);
                    }
                    break;

                case 5:
                    // Add product to cart
                    if (loggedInUser != null) {
                        System.out.print("Enter product ID to add to cart: ");
                        int productIdToAdd = scanner.nextInt();
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();  // Consume newline
                        System.out.println("Enter cart Item Id : ");
                        int cartItemId = scanner.nextInt();

                        CartItem cartItem = new CartItem(cartItemId, loggedInUser.getUserId(), productIdToAdd, quantity);
                        try {
                            cartService.addProductToCart(loggedInUser.getUserId(), productIdToAdd, quantity);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Product added to cart.");
                    } else {
                        System.out.println("You need to login first.");
                    }
                    break;

                case 6:
                    // View cart
                    if (loggedInUser != null) {
                        System.out.println("Your Cart:");
                        try {
							for (CartItem item : cartService.viewCart(loggedInUser.getUserId())) {
							    System.out.println(item);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    } else {
                        System.out.println("You need to login first.");
                    }
                    break;

                case 7:
                    // Place an order
                    if (loggedInUser != null) {
                        int orderId = orderService.placeOrder(loggedInUser.getUserId());
                        System.out.println("Order placed successfully with Order ID: " + orderId);
                    } else {
                        System.out.println("You need to login first.");
                    }
                    break;

                case 8:
                    // Make a payment
                    if (loggedInUser != null) {
                        System.out.print("Enter order ID to make payment: ");
                        int orderId = scanner.nextInt();
                        System.out.print("Enter payment amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();  // Consume newline
                        System.out.print("Enter payment method (e.g., Credit Card, PayPal): ");
                        String paymentMethod = scanner.nextLine();
                        System.out.println("Enter payment Id : ");
                        int paymentId = scanner.nextInt();

                        // Create a Payment object
                        Payment payment = new Payment(paymentId, orderId, paymentMethod, amount,"completed");

                        // Call the PaymentService to process the payment
                        paymentService.processPayment(payment);

                        System.out.println("Payment processed successfully.");
                    } else {
                        System.out.println("You need to login first.");
                    }
                    break;

                case 9:
                    // View order history
                    if (loggedInUser != null) {
                        System.out.println("Order History:");
                        for (Order order : orderService.viewOrderHistory(loggedInUser.getUserId())) {
                            System.out.println(order);
                        }
                    } else {
                        System.out.println("You need to login first.");
                    }
                    break;

                case 0:
                    // Exit the application
                    System.out.println("Exiting the application...");
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing connection.");
                    }
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
