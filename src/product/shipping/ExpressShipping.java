package product.shipping;

/**
 * Express shipping strategy - fast but expensive.
 * Charges double the standard rate plus a fixed express fee.
 */
public class ExpressShipping implements ShippingPolicy {
    
    private static final double VOLUMETRIC_DIVISOR = 5000.0;
    private static final double EXPRESS_RATE_PER_KG = 200.0; // 200 KZT per kg (double standard)
    private static final double EXPRESS_FEE = 500.0;         // Fixed express fee

    @Override
    public double calculate(double weightKg, double lengthCm, double widthCm, double heightCm) {
        double volumetricWeight = (lengthCm * widthCm * heightCm) / VOLUMETRIC_DIVISOR;
        double billableWeight = Math.max(weightKg, volumetricWeight);
        return billableWeight * EXPRESS_RATE_PER_KG + EXPRESS_FEE;
    }
}
