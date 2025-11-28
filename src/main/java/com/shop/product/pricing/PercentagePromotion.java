package com.shop.product.pricing;

import com.shop.product.Product;

/**
 * Promotion that applies a percentage discount.
 * Extends the abstract Promotion class.
 */
public class PercentagePromotion extends Promotion {
    private static final double MAX_DISCOUNT_PERCENT = 90.0;
    private final double percent; // 0..90

    public PercentagePromotion(double percent) {
        // Guarantee percent is within valid range
        this.percent = Math.max(0, Math.min(MAX_DISCOUNT_PERCENT, percent));
    }

    @Override
    public String name() {
        return "Percent-" + percent + "%";
    }

    @Override
    protected double calculateDiscount(Product p, int qty) {
        double basePrice = p.getPrice() * qty;
        return basePrice * (percent / 100.0);
    }
}
