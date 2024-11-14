package com.org.ecommerce.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class ExceptionHandler {

    private static final String LOG_FILE = "error_log.txt";

    public static void handle(Exception e) {
        logException(e);
        System.out.println("An error occurred. Please try again later.");
    }

    public static void handle(SQLException e) {
        logException(e);
        System.out.println("A database error occurred. Please contact support.");
    }

    private static void logException(Exception e) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
             
            printWriter.println("Exception occurred: " + e.getClass().getName());
            printWriter.println("Message: " + e.getMessage());
            e.printStackTrace(printWriter);
            printWriter.println("--------------------------------------------------");
        } catch (IOException ioException) {
            System.err.println("Failed to log exception: " + ioException.getMessage());
        }
    }

    public static void showCustomMessage(String message) {
        System.out.println(message);
    }
}

