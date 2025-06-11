package org.informatics.service;


import java.io.Serializable;

public class Cashier implements Serializable {
    private final String id;
    private final String name;
    private final double monthlySalary;

    public Cashier(String id, String name, double monthlySalary) {
        this.id = id;
        this.name = name;
        this.monthlySalary = monthlySalary;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public double getMonthlySalary() { return monthlySalary; }
}
