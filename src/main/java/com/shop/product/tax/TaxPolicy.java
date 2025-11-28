package com.shop.product.tax;

import com.shop.product.Product;

/**
 * Interface for tax calculation policies.
 * Different implementations can apply different tax rates.
 */
public interface TaxPolicy {
    
    /**
     * Returns the tax rate as a decimal (e.g., 0.12 for 12%).
     */
    double taxRate();

    /**
     * Calculates the tax amount for the given base price.
     * @param basePrice The price before tax
     * @return The tax amount to add
     */
    default double calculateTax(double basePrice) {
        return basePrice * taxRate();
    }

    /**
     * Checks if this tax policy is applicable to the given product.
     * @param p The product to check
     * @return true if applicable, false otherwise
     */
    default boolean applicableTo(Product p) {
        return true;
    }
}
