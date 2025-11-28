package product.shipping;

/**
 * Free shipping strategy - free if weight is small enough.
 * Items under the weight threshold ship for free.
 */
public class FreeShipping implements ShippingPolicy {
    
    private static final double FREE_WEIGHT_THRESHOLD = 2.0; // Free if <= 2 kg
    private static final double VOLUMETRIC_DIVISOR = 5000.0;
    private static final double FALLBACK_RATE_PER_KG = 100.0; // 100 KZT per kg if over threshold

    @Override
    public double calculate(double weightKg, double lengthCm, double widthCm, double heightCm) {
        double volumetricWeight = (lengthCm * widthCm * heightCm) / VOLUMETRIC_DIVISOR;
        double billableWeight = Math.max(weightKg, volumetricWeight);
        
        if (billableWeight <= FREE_WEIGHT_THRESHOLD) {
            return 0.0; // Free shipping
        }
        // If over threshold, charge standard rate
        return billableWeight * FALLBACK_RATE_PER_KG;
    }
}
