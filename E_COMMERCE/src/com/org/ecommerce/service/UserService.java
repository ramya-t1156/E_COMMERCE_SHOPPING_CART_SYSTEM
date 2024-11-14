package com.org.ecommerce.service;

import java.sql.Connection;

import com.org.ecommerce.dao.UserDao;
import com.org.ecommerce.model.User;

public class UserService {
    
    private UserDao userDao;
	private Connection connection;

    // Constructor with UserDao dependency for dependency injection
    public UserService(UserDao userDao) {
        this.setUserDao(userDao);
    }

    // Default constructor
    public UserService() {
        this.userDao = new UserDao();  // Initialize UserDao by default
    }

    public UserService(Connection connection) {
		// TODO Auto-generated constructor stub
    	this.connection = connection;
	}

	// Method to register a new user
    public boolean registerUser(User user) {
        // Check if the username is already taken (optional check)
        if (userDao.isUsernameTaken(user.getUsername())) {
            return false;  // Return false if the username is already taken
        }
        
        // Call UserDao to save the user into the database
        return userDao.addUser(user);
    }

    // Method for user login (authenticate user)
    public User login(String username, String password) {
        // Retrieve the user by username
        User user = userDao.getUserByUsername(username);
        
        // Check if user exists and if the password matches
        if (user != null && user.getPassword().equals(password)) {
            return user;  // Return user if authentication is successful
        }
        
        return null;  // Return null if authentication fails
    }

    // Method to view user profile by user ID
    public User viewProfile(int userId) {
        // Fetch the user profile by user ID
        return userDao.getUserById(userId);
    }

    // Getter for UserDao
    public UserDao getUserDao() {
        return userDao;
    }

    // Setter for UserDao
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
