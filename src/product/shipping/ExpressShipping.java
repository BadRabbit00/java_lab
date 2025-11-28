package product.shipping;

import static product.shipping.ShippingConstants.*;

/**
 * Express shipping strategy - fast but expensive.
 * Charges double the standard rate plus a fixed express fee.
 */
public class ExpressShipping implements ShippingPolicy {

    @Override
    public double calculate(double weightKg, double lengthCm, double widthCm, double heightCm) {
        double volumetricWeight = (lengthCm * widthCm * heightCm) / VOLUMETRIC_DIVISOR;
        double billableWeight = Math.max(weightKg, volumetricWeight);
        return billableWeight * EXPRESS_RATE_PER_KG + EXPRESS_FEE;
    }
}
