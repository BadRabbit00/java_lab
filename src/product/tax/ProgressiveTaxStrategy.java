package product.tax;

/**
 * Progressive tax strategy that applies different tax rates based on price tiers.
 * - Price < 100: 5% tax
 * - Price 100-500: 10% tax
 * - Price > 500: 15% tax
 * 
 * Implements TaxPolicy interface (OCP compliant).
 */
public class ProgressiveTaxStrategy implements TaxPolicy {
    
    private static final double LOW_THRESHOLD = 100.0;
    private static final double HIGH_THRESHOLD = 500.0;
    
    private static final double LOW_RATE = 0.05;    // 5%
    private static final double MID_RATE = 0.10;    // 10%
    private static final double HIGH_RATE = 0.15;   // 15%

    @Override
    public double taxRate() {
        // Returns the middle rate as a default
        // The actual rate is determined by calculateTax based on price
        return MID_RATE;
    }

    @Override
    public double calculateTax(double price) {
        if (price < LOW_THRESHOLD) {
            return price * LOW_RATE;    // 5%
        } else if (price <= HIGH_THRESHOLD) {
            return price * MID_RATE;    // 10%
        } else {
            return price * HIGH_RATE;   // 15%
        }
    }
}
