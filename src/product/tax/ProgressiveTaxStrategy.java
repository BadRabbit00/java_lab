package product.tax;

/**
 * Progressive tax strategy that applies different tax rates based on price tiers.
 * - Price < 100: 5% tax
 * - Price 100-500: 10% tax
 * - Price > 500: 15% tax
 * 
 * Implements TaxPolicy interface (OCP compliant).
 * 
 * Note: The taxRate() method returns a nominal average rate (10%) since 
 * the actual rate depends on the price. For accurate tax calculation,
 * use calculateTax(price) which applies the correct progressive rate.
 */
public class ProgressiveTaxStrategy implements TaxPolicy {
    
    private static final double LOW_THRESHOLD = 100.0;
    private static final double HIGH_THRESHOLD = 500.0;
    
    private static final double LOW_RATE = 0.05;    // 5%
    private static final double MID_RATE = 0.10;    // 10%
    private static final double HIGH_RATE = 0.15;   // 15%

    /**
     * Returns a nominal rate (middle tier) since actual rate is price-dependent.
     * For accurate tax calculation, use calculateTax(price) instead.
     */
    @Override
    public double taxRate() {
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
