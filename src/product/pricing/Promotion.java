package product.pricing;

import product.Product;

/**
 * Abstract class representing a promotion that implements PricePolicy.
 * Uses Template Method pattern to factor shared logic while allowing
 * subclasses to define specific discount calculations.
 */
public abstract class Promotion implements PricePolicy {

    /**
     * Template method: calculates the total price after applying the promotion.
     * Subclasses implement calculateDiscount() to define their specific discount logic.
     */
    @Override
    public final double apply(Product p, int qty) {
        if (qty <= 0) return 0.0;
        double basePrice = p.getPrice() * qty;
        double discount = calculateDiscount(p, qty);
        return Math.max(0.0, basePrice - discount);
    }

    /**
     * Hook method: subclasses define how the discount is calculated.
     * @param p The product
     * @param qty The quantity
     * @return The discount amount to subtract from the base price
     */
    protected abstract double calculateDiscount(Product p, int qty);
}
