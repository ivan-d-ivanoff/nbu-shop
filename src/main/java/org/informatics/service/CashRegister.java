package org.informatics.service;

import java.time.LocalDate;
import java.util.Map;


public class CashRegister {
    private final String registerId;
    private Cashier cashier;

    public CashRegister(String registerId) {
        this.registerId = registerId;
    }

    public void assignCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public Receipt processSale(Map<Good, Integer> items, Pricing calculator) {
        LocalDate now = LocalDate.now();
        Receipt receipt = new Receipt(cashier, now);
        for (Map.Entry<Good, Integer> entry : items.entrySet()) {
            Good good = entry.getKey();
            int qty = entry.getValue();
            if (good.getQuantity() < qty) {
                throw new OutOfStockException(good.getId(), qty, good.getQuantity());
            }
            double price = calculator.calculateSalePrice(good, now);
            good.setQuantity(good.getQuantity() - qty);
            receipt.addLineItem(good, qty, price);
        }
        receipt.finalizeTotal();
        receipt.saveToFile();
        return receipt;
    }
}