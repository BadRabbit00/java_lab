package product.pricing;

import product.Product;

/**
 * Реализация PricePolicy: Скидка в процентах.
 */
public class PercentageOff implements PricePolicy {
    private final double percent; // 0..90

    public PercentageOff(double percent) {
        // Гарантируем, что процент находится в допустимом диапазоне
        this.percent = Math.max(0, Math.min(90, percent));
    }

    @Override
    public String name() {
        return "Percent-" + percent + "%";
    }

    @Override
    public double apply(Product p, int qty) {
        double unitPrice = p.getPrice() * (1 - percent / 100.0);
        return unitPrice * Math.max(0, qty);
    }
}