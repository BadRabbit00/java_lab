package product.shipping;

/**
 * Standard shipping strategy - calculates shipping based on 
 * the greater of actual weight or volumetric weight.
 * Uses the same formula as the original PhysicalProduct.estimateShippingCost().
 */
public class StandardShipping implements ShippingPolicy {
    
    private static final double VOLUMETRIC_DIVISOR = 5000.0;
    private static final double SHIPPING_RATE_PER_KG = 100.0; // 100 KZT per kg

    @Override
    public double calculate(double weightKg, double lengthCm, double widthCm, double heightCm) {
        double volumetricWeight = (lengthCm * widthCm * heightCm) / VOLUMETRIC_DIVISOR;
        double billableWeight = Math.max(weightKg, volumetricWeight);
        return billableWeight * SHIPPING_RATE_PER_KG;
    }
}
