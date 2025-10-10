package product;

import category.Category;

/**
 * Класс Product с перегруженными конструкторами, статическими членами и фабричными методами.
 * Демонстрирует гибкие способы создания объектов с сохранением валидации.
 */
public class Product {
    // --- Статические члены ---
    public static final String DEFAULT_CURRENCY = "KZT";
    private static int createdCount = 0; // Счетчик созданных объектов
    private static int SEQ = 1; // Последовательность для авто-генерации ID

    // --- Атрибуты ---
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private Category category;

    // --- Конструкторы (с использованием цепочек вызовов) ---

    /**
     * 1. Полный конструктор. Основная логика инициализации здесь.
     */
    public Product(String id, String name, String description, double price, int quantity, Category category) {
        // Устанавливаем значения через защищенные методы
        if (!trySetId(id)) {
            this.id = "AUTO-" + nextSeq(); // Безопасное значение по умолчанию
        }
        if (!trySetName(name)) {
            this.name = "Unnamed"; // Безопасное значение по умолчанию
        }
        trySetDescription(description); // Описание может быть null
        if (!trySetPrice(price)) {
            this.price = 0.0; // Безопасное значение по умолчанию
        }
        if (!trySetQuantity(quantity)) {
            this.quantity = 0; // Безопасное значение по умолчанию
        }
        trySetCategory(category); // Категория может быть null

        createdCount++; // Увеличиваем счетчик созданных объектов
    }

    /**
     * 2. Конструктор с обязательными полями. Вызывает полный конструктор.
     */
    public Product(String id, String name, double price) {
        this(id, name, null, price, 0, null);
    }

    /**
     * 3. Конструктор без аргументов. Вызывает конструктор с обязательными полями.
     */
    public Product() {
        this("AUTO-" + nextSeq(), "Unnamed", 0.0);
    }

    /**
     * 4. Конструктор для совместимости с кодом из предыдущих заданий.
     */
    public Product(String id, String name, double price, int quantity) {
        this(id, name, null, price, quantity, null);
    }

    // --- Статические фабричные методы ---
    
    /**
     * Создает продукт через статический метод. Альтернатива конструктору.
     */
    public static Product of(String id, String name, double price) {
        return new Product(id, name, price);
    }

    /**
     * Создает предопределенный продукт "бесплатный образец".
     */
    public static Product freeSample(String name) {
        Product sample = new Product("AUTO-" + nextSeq(), name, 0.0);
        sample.trySetQuantity(1);
        return sample;
    }

    // --- Статические вспомогательные методы ---
    
    public static int getCreatedCount() {
        return createdCount;
    }
    
    private static String nextSeq() {
        return String.valueOf(SEQ++);
    }

    // --- Защищенные мутаторы (Guarded Mutators) ---
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
        if (category != null) {
            this.category = category;
            return true;
        }
        return false;
    }

    // --- Методы управления запасами ---
    public boolean addStock(int amount) {
        if (amount > 0 && (this.quantity + amount) <= 1_000_000) {
            this.quantity += amount;
            return true;
        }
        return false;
    }

    public boolean sellProduct(int amount) {
        if (amount > 0 && amount <= this.quantity) {
            this.quantity -= amount;
            return true;
        }
        return false;
    }
    
    // --- Бизнес-операции и информационные методы ---
    public boolean applyDiscount(double percentage) {
        if (percentage >= 0 && percentage <= 90) {
            this.price *= (1 - percentage / 100.0);
            return true;
        }
        return false;
    }

    public double calculateTotalValue() {
        return this.price * this.quantity;
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

    // --- Геттеры ---
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public String getStockStatus() {
        if (quantity == 0) return "OUT_OF_STOCK";
        if (quantity <= 10) return "LOW";
        return "IN_STOCK";
    }

    /**
     * Переопределенный метод toString для удобного вывода информации об объекте.
     */
    @Override
    public String toString() {
        return String.format("Product[id=%s, name='%s', price=%.2f, quantity=%d, status=%s]",
                id, name, price, quantity, getStockStatus());
    }
}

