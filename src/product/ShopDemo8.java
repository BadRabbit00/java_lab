package product;

import product.pricing.*;
import product.shipping.*;
import product.tax.*;

/**
 * Демонстрация Лабораторной работы №8: SOLID (SRP & OCP).
 * 
 * Показывает:
 * - OCP: Новые классы Buy3Pay2Promotion и ProgressiveTaxStrategy
 * - SRP: ShippingPolicy стратегии для расчета доставки
 */
public class ShopDemo8 {
    public static void main(String[] args) {
        System.out.println("====== ЛАБОРАТОРНАЯ РАБОТА №8: SOLID (SRP & OCP) ======\n");
        
        // ============================================================
        // ЧАСТЬ 1: Демонстрация OCP - Новые возможности
        // ============================================================
        
        System.out.println("=== ЧАСТЬ 1: Принцип OCP (Открытости/Закрытости) ===\n");
        
        // 1.1 Создаем стратегии доставки
        ShippingPolicy standard = new StandardShipping();
        ShippingPolicy express = new ExpressShipping();
        ShippingPolicy free = new FreeShipping();
        
        // 1.2 Создаем физический товар со стандартной доставкой
        PhysicalProduct monitor = new PhysicalProduct("M-1", "Monitor 27\"", 50000, 5.0, standard);
        monitor.trySetDimensions(50, 40, 10);
        
        PhysicalProduct keyboard = new PhysicalProduct("K-1", "Mechanical Keyboard", 15000, 0.8, standard);
        keyboard.trySetDimensions(45, 15, 4);
        
        // 1.3 Тестируем новую акцию "3 по цене 2" (OCP)
        System.out.println("--- Акция \"Buy 3 Pay 2\" (OCP) ---");
        Promotion b3p2 = new Buy3Pay2Promotion();
        Promotion percent10 = new PercentagePromotion(10);
        
        System.out.println("Товар: " + monitor.getName() + " | Цена: " + monitor.getPrice() + " KZT");
        System.out.println("  Базовая цена за 3 шт: " + monitor.finalPrice(3));
        System.out.println("  Цена с акцией " + b3p2.name() + ": " + monitor.finalPrice(3, b3p2));
        System.out.println("  (Ожидание: " + (monitor.getPrice() * 2) + " KZT - платим за 2 из 3)");
        System.out.println();
        
        System.out.println("  Цена за 6 шт (базовая): " + monitor.finalPrice(6));
        System.out.println("  Цена за 6 шт (" + b3p2.name() + "): " + monitor.finalPrice(6, b3p2));
        System.out.println("  (Ожидание: " + (monitor.getPrice() * 4) + " KZT - платим за 4 из 6)");
        System.out.println();
        
        // 1.4 Тестируем прогрессивный налог (OCP)
        System.out.println("--- Прогрессивный налог (OCP) ---");
        TaxPolicy progTax = new ProgressiveTaxStrategy();
        TaxPolicy flatVat = new FlatVat(12);
        
        double[] testPrices = {50, 250, 1000};
        for (double price : testPrices) {
            double progressiveTax = progTax.calculateTax(price);
            double flatTaxAmount = flatVat.calculateTax(price);
            System.out.printf("  Цена: %.0f KZT | Прогрессивный налог: %.2f KZT | FlatVat(12%%): %.2f KZT%n",
                    price, progressiveTax, flatTaxAmount);
        }
        System.out.println("  (Прогрессивный: <100 = 5%, 100-500 = 10%, >500 = 15%)");
        System.out.println();
        
        // ============================================================
        // ЧАСТЬ 2: Демонстрация SRP - Стратегии доставки
        // ============================================================
        
        System.out.println("=== ЧАСТЬ 2: Принцип SRP (Единственной Ответственности) ===\n");
        
        System.out.println("--- Разные стратегии доставки для одного товара ---");
        System.out.println("Товар: " + monitor.getName());
        System.out.printf("  Вес: %.1f кг, Размеры: %.0fx%.0fx%.0f см%n", 
                monitor.getWeightKg(), 50.0, 40.0, 10.0);
        System.out.println();
        
        // Стандартная доставка
        monitor.setShippingPolicy(standard);
        System.out.println("  StandardShipping: " + monitor.estimateShippingCost() + " KZT");
        
        // Экспресс доставка
        monitor.setShippingPolicy(express);
        System.out.println("  ExpressShipping: " + monitor.estimateShippingCost() + " KZT");
        
        // Бесплатная доставка (для тяжелого товара будет платная)
        monitor.setShippingPolicy(free);
        System.out.println("  FreeShipping: " + monitor.estimateShippingCost() + " KZT");
        System.out.println("  (Бесплатно только если вес <= 2 кг)");
        System.out.println();
        
        // Тест бесплатной доставки для легкого товара
        System.out.println("--- Легкий товар (клавиатура 0.8 кг) ---");
        System.out.println("Товар: " + keyboard.getName());
        
        keyboard.setShippingPolicy(standard);
        System.out.println("  StandardShipping: " + keyboard.estimateShippingCost() + " KZT");
        
        keyboard.setShippingPolicy(free);
        System.out.println("  FreeShipping: " + keyboard.estimateShippingCost() + " KZT");
        System.out.println("  (Бесплатно, т.к. вес <= 2 кг)");
        System.out.println();
        
        // ============================================================
        // ЧАСТЬ 3: Комбинированный пример
        // ============================================================
        
        System.out.println("=== ЧАСТЬ 3: Комбинированный расчет ===\n");
        
        monitor.setShippingPolicy(standard);
        int qty = 3;
        
        double basePrice = monitor.getPrice() * qty;
        double afterPromo = monitor.finalPrice(qty, b3p2);
        double tax = progTax.calculateTax(afterPromo);
        double shipping = monitor.estimateShippingCost();
        double total = afterPromo + tax + shipping;
        
        System.out.println("Товар: " + monitor.getName() + " x " + qty);
        System.out.println("  Базовая цена: " + basePrice + " KZT");
        System.out.println("  После акции " + b3p2.name() + ": " + afterPromo + " KZT");
        System.out.println("  Налог (прогрессивный 15%): +" + tax + " KZT");
        System.out.println("  Доставка (стандартная): +" + shipping + " KZT");
        System.out.println("  ----------------------------------------");
        System.out.println("  ИТОГО: " + total + " KZT");
        System.out.println();
        
        System.out.println("====== КОНЕЦ ДЕМОНСТРАЦИИ ======");
    }
}
