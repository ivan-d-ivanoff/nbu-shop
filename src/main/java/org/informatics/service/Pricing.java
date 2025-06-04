package org.informatics.service;
import java.time.LocalDate;

public class Pricing {
    private final double foodMarkupPercent;
    private final double nonFoodMarkupPercent;
    private final int daysThreshold;
    private final double discountPercent;

    public Pricing(double foodMarkupPercent, double nonFoodMarkupPercent,
                           int daysThreshold, double discountPercent) {
        this.foodMarkupPercent = foodMarkupPercent;
        this.nonFoodMarkupPercent = nonFoodMarkupPercent;
        this.daysThreshold = daysThreshold;
        this.discountPercent = discountPercent;
    }

    public double calculateSalePrice(Good good, LocalDate saleDate) {

        if (good.getExpirationDate().isBefore(saleDate) ||
                good.getExpirationDate().isEqual(saleDate.minusDays(1))) {

            throw new ExpiredProductException(good);
        }

        double markup = good.getCategory() == Category.FOOD
                ? foodMarkupPercent
                : nonFoodMarkupPercent;
        double basePrice = good.getCostPrice() * (1 + markup / 100);

        long daysToExpiry = saleDate.until(good.getExpirationDate()).getDays();
        if (daysToExpiry < daysThreshold) {
            basePrice *= (1 - discountPercent / 100);
        }
        return basePrice;
    }
}
