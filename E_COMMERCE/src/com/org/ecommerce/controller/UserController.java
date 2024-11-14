package com.org.ecommerce.controller;

import com.org.ecommerce.model.User;
import com.org.ecommerce.service.UserService;

public class UserController {
    private UserService userService;

    // Default constructor
    public UserController() {
        this.userService = new UserService();
    }

    // Constructor with dependency injection for UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register a new user
    public void registerUser(User user) {
        try {
            boolean isRegistered = userService.registerUser(user);
            if (isRegistered) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("User registration failed. Username might already be taken.");
            }
        } catch (Exception e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    // Login a user
    public void login(String username, String password) {
        try {
            User user = userService.login(username, password);
            if (user != null) {
                System.out.println("Welcome, " + user.getName());
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    // View a user's profile
    public void viewProfile(int userId) {
        try {
            User user = userService.viewProfile(userId);
            if (user != null) {
                System.out.println("Profile details: " + user);
            } else {
                System.out.println("User not found.");
            }
        } catch (Exception e) {
            System.out.println("Error fetching profile: " + e.getMessage());
        }
    }
}
