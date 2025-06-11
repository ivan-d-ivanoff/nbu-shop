package org.informatics.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class Receipt implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int counter = 0;

    private final int number;
    private final Cashier cashier;
    private final LocalDate date;
    private final List<LineItem> items = new ArrayList<>();
    protected double total;

    public Receipt(Cashier cashier, LocalDate date) {
        this.number = ++counter;
        this.cashier = cashier;
        this.date = date;
    }

    public void addLineItem(Good good, int qty, double unitPrice) {
        items.add(new LineItem(good, qty, unitPrice));
    }

    public void finalizeTotal() {
        total = items.stream().mapToDouble(li -> li.getQuantity() * li.getUnitPrice()).sum();
    }

    public void saveToFile() {
        String dir = "receipts";
        Path dirPath = Paths.get(dir);

        try {
            Files.createDirectories(dirPath);

            String baseName = "receipt_" + number;
            Path txtPath = dirPath.resolve(baseName + ".txt");
            Path serPath = dirPath.resolve(baseName + ".ser");

            try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(txtPath))) {

                writer.println("Receipt No: " + number);
                writer.println("Date: " + date);
                writer.println("Cashier: " + cashier.getName() + " (ID=" + cashier.getId() + ")");
                writer.println("-----------------------------------");

                for (LineItem item : items) {
                    writer.printf("%s (x%d) @ %.2f = %.2f%n",
                            item.getGood().getName(),
                            item.getQuantity(),
                            item.getUnitPrice(),
                            item.getQuantity() * item.getUnitPrice());
                }

                writer.println("-----------------------------------");
                writer.printf("Total: %.2f%n", total);
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serPath.toFile()))) {
                oos.writeObject(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving receipt to files", e);
        }
    }


    private static class LineItem implements Serializable {
        private static final long serialVersionUID = 1L;

        private final Good good;
        private final int quantity;
        private final double unitPrice;

        public LineItem(Good good, int quantity, double unitPrice) {
            this.good = good;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
        public Good getGood() { return good; }
        public int getQuantity() { return quantity; }
        public double getUnitPrice() { return unitPrice; }
    }
}
