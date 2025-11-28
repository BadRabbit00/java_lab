package com.shop.product.pricing;

import com.shop.product.Product;

/**
 * Интерфейс (контракт) для любого правила ценообразования.
 */
public interface PricePolicy {
    /**
     * Человекочитаемое имя правила (для печати).
     */
    String name();

    /**
     * Рассчитывает ИТОГОВУЮ стоимость для 'qty' единиц товара 'p' (БЕЗ доставки).
     * Не должен изменять состояние объекта Product.
     */
    double apply(Product p, int qty);

    /**
     * По умолчанию, правило применимо ко всем товарам.
     */
    default boolean applicableTo(Product p) {
        return true;
    }
}