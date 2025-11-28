package product.pricing;

import product.Product;

/**
 * Promotion that applies a fixed discount amount per unit.
 * Extends the abstract Promotion class.
 */
public class FixedPromotion extends Promotion {
    private final double amount; // >= 0

    public FixedPromotion(double amount) {
        this.amount = Math.max(0, amount);
    }

    @Override
    public String name() {
        return "Fixed-" + amount;
    }

    @Override
    protected double calculateDiscount(Product p, int qty) {
        // Fixed discount per unit, but total discount cannot exceed base price
        double maxDiscount = p.getPrice() * qty;
        double totalDiscount = amount * qty;
        return Math.min(totalDiscount, maxDiscount);
    }
}
