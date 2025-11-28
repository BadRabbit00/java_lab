package com.shop.product.pricing;

import com.shop.product.Product;

/**
 * Promotion: "Buy 3, Pay for 2" - Every 3rd item is free.
 * Extends the abstract Promotion class (OCP compliant).
 */
public class Buy3Pay2Promotion extends Promotion {

    @Override
    public String name() {
        return "Buy 3 Pay 2";
    }

    @Override
    protected double calculateDiscount(Product p, int qty) {
        // Every 3rd item is free
        // If buying 3, pay for 2. If 6, pay for 4.
        int freeItems = qty / 3;
        // Discount is the price of the free items
        return p.getPrice() * freeItems;
    }
}
