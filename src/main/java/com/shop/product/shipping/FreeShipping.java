package com.shop.product.shipping;

import static com.shop.product.shipping.ShippingConstants.*;

/**
 * Free shipping strategy - free if weight is small enough.
 * Items under the weight threshold ship for free.
 */
public class FreeShipping implements ShippingPolicy {

    @Override
    public double calculate(double weightKg, double lengthCm, double widthCm, double heightCm) {
        double volumetricWeight = (lengthCm * widthCm * heightCm) / VOLUMETRIC_DIVISOR;
        double billableWeight = Math.max(weightKg, volumetricWeight);
        
        if (billableWeight <= FREE_WEIGHT_THRESHOLD) {
            return 0.0; // Free shipping
        }
        // If over threshold, charge standard rate
        return billableWeight * STANDARD_RATE_PER_KG;
    }
}
