package com.shop.product.shipping;

/**
 * Interface for products that can be shipped.
 * Typically implemented by physical products that require shipping.
 */
public interface Shippable {
    
    /**
     * Calculates the shipping cost for this product.
     * @return The shipping cost
     */
    double calculateShippingCost();

    /**
     * Returns the weight in kilograms for shipping calculations.
     * @return Weight in kg
     */
    double getShippingWeight();
}
