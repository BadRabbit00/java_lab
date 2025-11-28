package product.tax;

import product.DigitalProduct;
import product.Product;

/**
 * Reduced VAT rate for digital products (e.g., 5%).
 * Only applicable to DigitalProduct instances.
 */
public class ReducedDigitalVat implements TaxPolicy {
    private final double rate;

    public ReducedDigitalVat(double ratePercent) {
        this.rate = ratePercent / 100.0;
    }

    @Override
    public double taxRate() {
        return rate;
    }

    @Override
    public boolean applicableTo(Product p) {
        return p instanceof DigitalProduct;
    }
}
