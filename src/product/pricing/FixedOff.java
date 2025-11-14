package product.pricing;

import product.Product;
// 1. ИМПОРТИРУЕМ DigitalProduct, чтобы проверить тип
import product.DigitalProduct;

/**
 * ...
 * ТЕПЕРЬ ДЕЙСТВУЕТ ТОЛЬКО НА DigitalProduct.
 */
public class FixedOff implements PricePolicy {
    // ... (конструктор и другие методы)

    /**
     * 2. (ПЕРЕОПРЕДЕЛЕНО)
     * Это правило применимо, только если продукт является DigitalProduct.
     */
    @Override
    public boolean applicableTo(Product p) {
        // Мы проверяем, является ли переданный продукт p
        // экземпляром класса DigitalProduct
        return p instanceof DigitalProduct;
    }
}