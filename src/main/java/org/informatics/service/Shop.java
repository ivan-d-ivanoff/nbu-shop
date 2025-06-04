package org.informatics.service;
import java.util.*;


public class Shop {
    private final List<Good> inventory = new ArrayList<>();
    private final List<Cashier> cashiers = new ArrayList<>();
    private final List<Receipt> receipts = new ArrayList<>();

    private final Pricing priceCalculator;
    private double totalRevenue = 0;
    private double totalCost = 0;

    public Shop(Pricing calculator) {
        this.priceCalculator = calculator;
    }

    public void addGood(Good good) {
        inventory.add(good);
        totalCost += good.getCostPrice() * good.getQuantity();
    }


    public void addCashier(Cashier cashier) {
        cashiers.add(cashier);
        totalCost += cashier.getMonthlySalary();
    }


    public Receipt sell(CashRegister register, Map<Good,Integer> items) {
        Receipt receipt = register.processSale(items, priceCalculator);
        receipts.add(receipt);
        totalRevenue += receipt.total;
        return receipt;
    }

    public int getReceiptCount() { return receipts.size(); }
    public double getTotalRevenue() { return totalRevenue; }
    public double getTotalCost() { return totalCost; }
    public double getProfit() { return totalRevenue - totalCost; }
}
