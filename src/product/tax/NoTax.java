package product.tax;

/**
 * Tax policy with no tax (0%).
 */
public class NoTax implements TaxPolicy {
    
    @Override
    public double taxRate() {
        return 0.0;
    }
}
