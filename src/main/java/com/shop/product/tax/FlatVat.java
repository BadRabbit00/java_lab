package com.shop.product.tax;

/**
 * Tax policy with a flat VAT rate (e.g., 12%).
 */
public class FlatVat implements TaxPolicy {
    private final double rate;

    public FlatVat(double ratePercent) {
        this.rate = ratePercent / 100.0;
    }

    @Override
    public double taxRate() {
        return rate;
    }
}
