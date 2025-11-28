package com.shop.product.shipping;

/**
 * Strategy interface for shipping cost calculation.
 * Different implementations can apply different shipping formulas.
 * This follows the SRP principle - shipping logic is separated from product.
 */
public interface ShippingPolicy {
    
    /**
     * Calculates the shipping cost based on weight and dimensions.
     * 
     * @param weightKg Weight of the product in kilograms
     * @param lengthCm Length of the product in centimeters
     * @param widthCm Width of the product in centimeters
     * @param heightCm Height of the product in centimeters
     * @return The calculated shipping cost
     */
    double calculate(double weightKg, double lengthCm, double widthCm, double heightCm);
}
