package com.shop.product;

// Импортируем весь пакет pricing
import com.shop.product.pricing.*;
// Импортируем List для полиморфных списков
import java.util.List;

/**
 * Демонстрация Полиморфизма (Лаб. 6).
 * Показывает, как List<Product> и List<PricePolicy>
 * приводят к разному поведению объектов.
 */
public class ShopDemo5 {
    public static void main(String[] args) {
        // 1. Создаем объекты разных подтипов
        PhysicalProduct laptop = new PhysicalProduct("P-LAP-1", "Laptop", 450_000.0, 1.8);
        laptop.trySetDimensions(35, 24, 2); // Устанавливаем размеры для расчета доставки

        DigitalProduct ebook = new DigitalProduct("P-EBK-1", "E-Book", 1_500.0, 12.5);

        // 2. Создаем полиморфный список List<Product>
        // Мы можем класть в него и PhysicalProduct, и DigitalProduct
        List<Product> items = List.of(laptop, ebook);

        // 3. Создаем полиморфный список List<PricePolicy>
        // Содержит разные реализации интерфейса PricePolicy
        List<PricePolicy> rules = List.of(
            new PercentageOff(10), // Скидка 10%
            new FixedOff(50),      // Скидка 50 KZT
            new BogoHalf()         // 2-й за полцены
        );

        // 4. Демонстрируем полиморфизм в действии
        for (Product p : items) { // Проходим по списку товаров
            for (int qty : new int[]{1, 2}) { // Тестируем для 1 и 2 шт.
                System.out.println("\n== " + p.getName() + " | qty=" + qty);
                
                // Вызов метода (2) из Product
                System.out.println("Base: " + p.finalPrice(qty));
                
                for (PricePolicy r : rules) { // Проходим по списку правил
                    // Вызов (3) из Product, который переопределен в подклассах
                    // JVM (Java) сама решает, какой метод вызвать:
                    // - p.finalPrice(...) для PhysicalProduct (который добавит доставку)
                    // - p.finalPrice(...) для DigitalProduct (которыj проигнорит BOGO)
                    System.out.println(r.name() + ": " + p.finalPrice(qty, r));
                }
                
                // Вызов (4) из Product, который тоже переопределен в PhysicalProduct
                System.out.println("Best(of all): " + p.finalPrice(qty, rules));
            }
        }
    }
}