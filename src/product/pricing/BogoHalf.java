package product.pricing;

import product.Product;

/**
 * Реализация PricePolicy: "Buy-One-Get-One-Half" (второй за полцены).
 * Цена за пару = 1.5 * price
 */
public class BogoHalf implements PricePolicy {
    @Override
    public String name() {
        return "BOGO-HALF";
    }

    @Override
    public double apply(Product p, int qty) {
        double price = p.getPrice();
        int pairs = Math.max(0, qty) / 2; // Количество пар
        int singles = Math.max(0, qty) % 2; // Количество оставшихся (0 или 1)
        
        // Стоимость пар + стоимость оставшегося
        return pairs * (price * 1.5) + singles * price;
    }
}