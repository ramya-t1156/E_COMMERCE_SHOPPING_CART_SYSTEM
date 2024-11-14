package com.org.ecommerce.util;

public class InputValidator {
    public static boolean isValidEmail(String email) {
        return email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    public static boolean isPositive(double number) {
        return number > 0;
    }

    public static boolean isNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }
}
