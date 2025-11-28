package com.shop.product.shipping;

import static com.shop.product.shipping.ShippingConstants.*;

/**
 * Standard shipping strategy - calculates shipping based on 
 * the greater of actual weight or volumetric weight.
 * Uses the same formula as the original PhysicalProduct.estimateShippingCost().
 */
public class StandardShipping implements ShippingPolicy {

    @Override
    public double calculate(double weightKg, double lengthCm, double widthCm, double heightCm) {
        double volumetricWeight = (lengthCm * widthCm * heightCm) / VOLUMETRIC_DIVISOR;
        double billableWeight = Math.max(weightKg, volumetricWeight);
        return billableWeight * STANDARD_RATE_PER_KG;
    }
}
