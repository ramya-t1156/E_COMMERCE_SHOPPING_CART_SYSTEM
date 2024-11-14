package com.org.ecommerce.model;

import java.sql.Timestamp;

public class User {
    private int userId;
    private String username;
    private String password;
    private String name;
    private String email;
    private String address;
    private String role;
    private Timestamp createdAt;
    private Timestamp lastLogin;

    // Constructors
    public User() {}

    public User(int userId, String username, String password, String name, String email, 
                String address, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.role = role;
        this.createdAt = new Timestamp(System.currentTimeMillis());  // Set created_at to current time
        this.lastLogin = null;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getLastLogin() { return lastLogin; }
    public void setLastLogin(Timestamp lastLogin) { this.lastLogin = lastLogin; }
}
