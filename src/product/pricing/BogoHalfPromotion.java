package product.pricing;

import product.Product;

/**
 * Promotion: "Buy-One-Get-One-Half" (second item at half price).
 * Price per pair = 1.5 * unit price.
 * Extends the abstract Promotion class.
 */
public class BogoHalfPromotion extends Promotion {

    @Override
    public String name() {
        return "BOGO-HALF";
    }

    @Override
    protected double calculateDiscount(Product p, int qty) {
        double price = p.getPrice();
        int pairs = qty / 2; // Number of pairs
        // Discount is 50% of the second item price for each pair
        return pairs * (price * 0.5);
    }
}
