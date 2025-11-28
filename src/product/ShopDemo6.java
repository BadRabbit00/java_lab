package product;

import product.pricing.*;
import product.tax.*;
import product.shipping.Shippable;
import java.util.List;

/**
 * Демонстрация Абстрактных классов и Интерфейсов (Лаб. 7).
 * Показывает Template Method паттерн через Promotion,
 * а также использование TaxPolicy и Shippable интерфейсов.
 */
public class ShopDemo6 {
    public static void main(String[] args) {
        // 1. Создаем объекты разных подтипов
        PhysicalProduct laptop = new PhysicalProduct("P-LAP-1", "Laptop", 450_000.0, 1.8);
        laptop.trySetDimensions(35, 24, 2);

        DigitalProduct ebook = new DigitalProduct("P-EBK-1", "E-Book", 1_500.0, 12.5);

        // 2. Создаем полиморфный список товаров
        List<Product> items = List.of(laptop, ebook);

        // 3. Создаем полиморфный список промоакций (новые классы, наследующие Promotion)
        List<Promotion> promotions = List.of(
            new PercentagePromotion(10),  // Скидка 10%
            new FixedPromotion(50),       // Скидка 50 KZT за единицу
            new BogoHalfPromotion()       // 2-й за полцены
        );

        // 4. Создаем разные налоговые политики
        TaxPolicy noTax = new NoTax();
        TaxPolicy flatVat = new FlatVat(12); // 12% VAT
        TaxPolicy digitalVat = new ReducedDigitalVat(5); // 5% for digital products

        System.out.println("====== ДЕМОНСТРАЦИЯ ПРОМОАКЦИЙ (Template Method) ======\n");
        
        // 5. Демонстрация промоакций с Template Method
        for (Product p : items) {
            for (int qty : new int[]{1, 2}) {
                System.out.println("== " + p.getName() + " | qty=" + qty);
                System.out.println("Base price: " + p.finalPrice(qty));
                
                for (Promotion promo : promotions) {
                    double price = promo.apply(p, qty);
                    System.out.println(promo.name() + ": " + price);
                }
                System.out.println();
            }
        }

        System.out.println("\n====== ДЕМОНСТРАЦИЯ НАЛОГОВЫХ ПОЛИТИК ======\n");
        
        // 6. Демонстрация TaxPolicy с разными продуктами
        for (Product p : items) {
            double basePrice = p.getPrice();
            System.out.println("Product: " + p.getName() + " | Base price: " + basePrice);
            
            // NoTax
            double tax1 = noTax.calculateTax(basePrice);
            System.out.println("  NoTax: " + basePrice + " + " + tax1 + " = " + (basePrice + tax1));
            
            // FlatVat 12%
            double tax2 = flatVat.calculateTax(basePrice);
            System.out.println("  FlatVat(12%): " + basePrice + " + " + tax2 + " = " + (basePrice + tax2));
            
            // ReducedDigitalVat 5% (only for digital)
            if (digitalVat.applicableTo(p)) {
                double tax3 = digitalVat.calculateTax(basePrice);
                System.out.println("  ReducedDigitalVat(5%): " + basePrice + " + " + tax3 + " = " + (basePrice + tax3));
            } else {
                System.out.println("  ReducedDigitalVat: Not applicable to physical products");
            }
            System.out.println();
        }

        System.out.println("\n====== ДЕМОНСТРАЦИЯ SHIPPABLE ИНТЕРФЕЙСА ======\n");
        
        // 7. Демонстрация Shippable интерфейса
        for (Product p : items) {
            System.out.print("Product: " + p.getName() + " - ");
            if (p instanceof Shippable) {
                Shippable shippable = (Shippable) p;
                System.out.println("Shippable!");
                System.out.println("  Shipping weight: " + shippable.getShippingWeight() + " kg");
                System.out.println("  Shipping cost: " + shippable.calculateShippingCost() + " KZT");
            } else {
                System.out.println("Not shippable (digital product)");
            }
            System.out.println();
        }

        System.out.println("\n====== КОМБИНИРОВАННЫЙ РАСЧЕТ: ПРОМО + НАЛОГ + ДОСТАВКА ======\n");
        
        // 8. Комбинированный пример: промо + налог + доставка
        Promotion bestPromo = new PercentagePromotion(15); // 15% discount
        
        for (Product p : items) {
            int qty = 2;
            double baseTotal = p.getPrice() * qty;
            double afterPromo = bestPromo.apply(p, qty);
            
            // Choose appropriate tax policy
            TaxPolicy tax = (p instanceof DigitalProduct) ? digitalVat : flatVat;
            double taxAmount = tax.calculateTax(afterPromo);
            double withTax = afterPromo + taxAmount;
            
            // Add shipping for physical products
            double shipping = 0;
            if (p instanceof Shippable) {
                shipping = ((Shippable) p).calculateShippingCost() * qty;
            }
            double total = withTax + shipping;
            
            System.out.println("Product: " + p.getName() + " (qty=" + qty + ")");
            System.out.println("  Base total: " + baseTotal);
            System.out.println("  After " + bestPromo.name() + ": " + afterPromo);
            System.out.println("  Tax (" + (tax.taxRate() * 100) + "%): +" + taxAmount);
            System.out.println("  Shipping: +" + shipping);
            System.out.println("  TOTAL: " + total);
            System.out.println();
        }
    }
}
