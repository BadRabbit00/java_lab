package com.shop.product.pricing;

import com.shop.product.DigitalProduct;
import com.shop.product.Product;

/**
 * Реализация PricePolicy: Фиксированная скидка на единицу товара.
 */
public class FixedOff implements PricePolicy {
    private final double amount; // >= 0

    public FixedOff(double amount) {
        this.amount = Math.max(0, amount);
    }

    @Override
    public String name() {
        return "Fixed-" + amount;
    }

    @Override
    public double apply(Product p, int qty) {
        // Цена за единицу не может быть ниже нуля
        double unitPrice = Math.max(0.0, p.getPrice() - amount);
        return unitPrice * Math.max(0, qty);

    }
    
    @Override
    public boolean applicableTo(Product p) {
        // Мы проверяем, является ли переданный продукт p
        // экземпляром класса DigitalProduct
        return p instanceof DigitalProduct;
    }
}