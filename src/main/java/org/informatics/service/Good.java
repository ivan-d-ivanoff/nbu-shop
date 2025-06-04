package org.informatics.service;

import java.time.LocalDate;


public class Good {
    private final String id;
    private final String name;
    private final double costPrice;
    private final Category category;
    private LocalDate expirationDate;
    private int quantity;

    public Good(String id, String name, double costPrice, Category category, LocalDate expirationDate, int quantity) {
        this.id = id;
        this.name = name;
        this.costPrice = costPrice;
        this.category = category;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getCostPrice() { return costPrice; }
    public Category getCategory() { return category; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
}

