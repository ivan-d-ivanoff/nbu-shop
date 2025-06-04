package org.informatics.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Configure price calculator:");
            System.out.print("Food markup percent: ");
            double foodMarkup = Double.parseDouble(scanner.nextLine());
            System.out.print("Non-food markup percent: ");
            double nonFoodMarkup = Double.parseDouble(scanner.nextLine());
            System.out.print("Days threshold for discount: ");
            int daysThreshold = Integer.parseInt(scanner.nextLine());
            System.out.print("Discount percent: ");
            double discountPercent = Double.parseDouble(scanner.nextLine());

            Pricing calculator = new Pricing(foodMarkup, nonFoodMarkup, daysThreshold, discountPercent);
            Shop shop = new Shop(calculator);

            System.out.println("Enter products (leave ID empty to finish):");

            while (true) {
                System.out.print("Product ID: ");
                String id = scanner.nextLine().trim();
                if (id.isEmpty()) break;
                System.out.print("Name: ");
                String name = scanner.nextLine().trim();
                System.out.print("Cost price: ");
                double cost = Double.parseDouble(scanner.nextLine());
                Category category;
                while (true) {
                    System.out.print("Category (FOOD/NON_FOOD): ");
                    try {
                        category = Category.valueOf(scanner.nextLine().trim().toUpperCase());
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid category, try again.");
                    }
                }
                LocalDate expDate;

                while (true) {
                    System.out.print("Expiration date (YYYY-MM-DD): ");
                    try {
                        expDate = LocalDate.parse(scanner.nextLine().trim());
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format, try again.");
                    }
                }
                System.out.print("Quantity: ");
                int qty = Integer.parseInt(scanner.nextLine());
                Good good = new Good(id, name, cost, category, expDate, qty);
                shop.addGood(good);
                System.out.println("Added product: " + name);
            }

            System.out.println("Enter cashiers (leave ID empty to finish):");

            while (true) {
                System.out.print("Cashier ID: ");
                String cid = scanner.nextLine().trim();
                if (cid.isEmpty()) break;
                System.out.print("Name: ");
                String cname = scanner.nextLine().trim();
                System.out.print("Monthly salary: ");
                double salary = Double.parseDouble(scanner.nextLine());
                Cashier cashier = new Cashier(cid, cname, salary);
                shop.addCashier(cashier);
                System.out.println("Added cashier: " + cname);
            }

            System.out.println("Setup complete.");
            System.out.println("Total cost (deliveries & salaries): " + shop.getTotalCost());
            System.out.println("Total revenue so far: " + shop.getTotalRevenue());
            System.out.println("Current profit: " + shop.getProfit());
        }
    }
}