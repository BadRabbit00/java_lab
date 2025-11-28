package com.shop.product;

// Импортируем класс Category, так как он нам снова нужен
import com.shop.category.Category;
// Новые импорты для Лаб. 6
import com.shop.product.pricing.PricePolicy;
import java.util.List;

/**
 * Базовый класс (суперкласс) для всех товаров.
 * ДОБАВЛЕНА ПЕРЕГРУЗКА (OVERLOADING) методов finalPrice.
 */
public class Product {
    // --- Статические члены ---
    public static final String DEFAULT_CURRENCY = "KZT";
    private static int createdCount = 0;
    private static int SEQ = 1;

    // --- Атрибуты ---
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private Category category;

    // --- Конструкторы ---
    public Product(String id, String name, String description, double price, int quantity, Category category) {
        if (!trySetId(id)) this.id = "AUTO-" + nextSeq();
        if (!trySetName(name)) this.name = "Unnamed";
        trySetDescription(description);
        if (!trySetPrice(price)) this.price = 0.0;
        if (!trySetQuantity(quantity)) this.quantity = 0;
        trySetCategory(category);
        createdCount++;
    }

    public Product(String id, String name, double price) {
        this(id, name, null, price, 0, null);
    }

    public Product() {
        this("AUTO-" + nextSeq(), "Unnamed", 0.0);
    }

    // --- Статические фабрики и методы ---
    public static Product of(String id, String name, double price) {
        return new Product(id, name, price);
    }
    public static Product freeSample(String name) {
        Product sample = new Product("AUTO-" + nextSeq(), "Unnamed", 0.0);
        sample.trySetQuantity(1);
        return sample;
    }
    public static int getCreatedCount() { return createdCount; }
    private static String nextSeq() { return String.valueOf(SEQ++); }

    // --- Защищенные мутаторы ---
    public boolean trySetId(String id) {
        if (id != null && id.trim().length() >= 2) {
            this.id = id.trim();
            return true;
        }
        return false;
    }
    public boolean trySetName(String name) {
        if (name != null && name.trim().length() >= 2) {
            this.name = name.trim();
            return true;
        }
        return false;
    }
    public boolean trySetDescription(String description) {
        if (description == null || description.trim().length() <= 200) {
            this.description = description;
            return true;
        }
        return false;
    }
    public boolean trySetPrice(double price) {
        if (price >= 0.0 && price <= 1_000_000.0) {
            this.price = price;
            return true;
        }
        return false;
    }
    public boolean trySetQuantity(int quantity) {
        if (quantity >= 0 && quantity <= 1_000_000) {
            this.quantity = quantity;
            return true;
        }
        return false;
    }

    public boolean trySetCategory(Category category) {
        this.category = category;
        return true;
    }
    
    // --- Методы расчета и отображения (из старых лаб) ---
    public double calculateTotalValue() {
        return this.price * this.quantity;
    }

    public boolean applyDiscount(double percentage) {
        if (percentage >= 0 && percentage <= 90) {
            this.price *= (1 - percentage / 100.0);
            return true;
        }
        return false;
    }

    public void displayProductInfo() {
        System.out.println("--- Информация о товаре ---");
        System.out.println("ID: " + id);
        System.out.println("Название: " + name);
        System.out.println("Описание: " + (description == null || description.isEmpty() ? "Отсутствует" : description));
        System.out.printf("Цена: %.2f\n", price);
        System.out.println("Количество на складе: " + quantity);
        System.out.println("Статус наличия: " + getStockStatus());
        if (category != null && category.getName() != null) {
            System.out.println("Категория: " + category.getName());
        }
        System.out.println("---------------------------");
    }

    // --- НОВЫЕ МЕТОДЫ ДЛЯ ЛАБ. 6 (ПЕРЕГРУЗКА) ---

    /**
     * 1) Цена за 1 шт, без правил.
     */
    public double finalPrice() {
        return getPrice();
    }

    /**
     * 2) Цена за 'qty' шт, без правил.
     */
    public double finalPrice(int qty) {
        if (qty <= 0) return 0.0;
        return getPrice() * qty;
    }

    /**
     * 3) Цена за 'qty' шт, с одним правилом.
     * (Подклассы могут добавить свою логику, напр. доставку)
     */
    public double finalPrice(int qty, PricePolicy policy) {
        if (qty <= 0) return 0.0;
        // Если правило null или не применимо, используем базовую цену
        if (policy == null || !policy.applicableTo(this)) {
            return finalPrice(qty);
        }
        // Применяем правило
        return policy.apply(this, qty);
    }

    /**
     * 4) Цена за 'qty' шт, с выбором лучшего из списка правил.
     */
    public double finalPrice(int qty, List<PricePolicy> policies) {
        if (qty <= 0) return 0.0;
        if (policies == null || policies.isEmpty()) {
            return finalPrice(qty); // Нет правил, базовая цена
        }
        
        double bestPrice = Double.POSITIVE_INFINITY;
        // Находим минимальную цену среди всех правил
        for (PricePolicy pp : policies) {
            double currentPrice = finalPrice(qty, pp); // Вызываем (3)
            if (currentPrice < bestPrice) {
                bestPrice = currentPrice;
            }
        }
        return bestPrice;
    }

    // --- Геттеры и другие методы ---
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public Category getCategory() { return category; }

    public String getStockStatus() {
        if (quantity == 0) return "OUT_OF_STOCK";
        if (quantity <= 10) return "LOW";
        return "IN_STOCK";
    }

    @Override
    public String toString() {
        return String.format("Product[id=%s, name='%s', price=%.2f, quantity=%d]",
                id, name, price, quantity);
    }
}