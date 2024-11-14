package com.org.ecommerce.model;

import java.sql.Timestamp;

public class DiscountCode {
    private String discountCode;
    private String description;
    private double discountPercent;
    private Timestamp validFrom;
    private Timestamp validUntil;

    // Constructor
    public DiscountCode() {}
    public DiscountCode(String discountCode, String description, double discountPercent, Timestamp validFrom, Timestamp validUntil) {
        this.discountCode = discountCode;
        this.description = description;
        this.discountPercent = discountPercent;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    // Getter and Setter methods
    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Timestamp getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Timestamp validFrom) {
        this.validFrom = validFrom;
    }

    public Timestamp getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Timestamp validUntil) {
        this.validUntil = validUntil;
    }
}
