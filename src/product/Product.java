package product;

import category.Category;

/**
 * Класс Product представляет товар в магазине с усиленной инкапсуляцией и валидацией.
 * Все изменения состояния объекта происходят через "защищенные" методы,
 * которые проверяют корректность данных и возвращают boolean в зависимости от успеха операции.
 */
public class Product {
    // --- Атрибуты (все private для инкапсуляции) ---
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private Category category;

    // --- Конструктор ---
    /**
     * Создает новый объект Product с обязательной начальной валидацией.
     * Если начальные данные некорректны, создание объекта завершится ошибкой.
     * @param id Уникальный ID товара (длина >= 2).
     * @param name Название товара (длина >= 2).
     * @param price Начальная цена (0 <= цена <= 1,000,000).
     * @param quantity Начальное количество (0 <= количество <= 1,000,000).
     */
    public Product(String id, String name, double price, int quantity) {
        // Начальная валидация критически важных полей
        if (id == null || id.trim().length() < 2) {
            throw new IllegalArgumentException("ID товара не может быть null и должен содержать минимум 2 символа.");
        }
        this.id = id.trim();

        if (name == null || name.trim().length() < 2) {
            throw new IllegalArgumentException("Название товара не может быть null и должно содержать минимум 2 символа.");
        }
        this.name = name.trim();
        
        // Для остальных полей используем защищенные мутаторы
        if (!trySetPrice(price)) {
             throw new IllegalArgumentException("Некорректная начальная цена.");
        }
        if (!trySetQuantity(quantity)) {
            throw new IllegalArgumentException("Некорректное начальное количество.");
        }
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
            // В реальном приложении можно добавить логику
            // по удалению продукта из старой категории
            this.category = category;
            return true;
        }
        return false;
    }

    // --- Методы управления запасами (с валидацией) ---

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

    // --- Бизнес-операции (с валидацией) ---
    
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
    
    // --- Геттеры (только для чтения) ---
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public Category getCategory() { return category; }
    
    /**
     * Вычисляемый статус наличия товара на складе.
     * @return "OUT_OF_STOCK", "LOW" или "IN_STOCK".
     */
    public String getStockStatus() {
        if (quantity == 0) {
            return "OUT_OF_STOCK";
        } else if (quantity <= 10) {
            return "LOW";
        } else {
            return "IN_STOCK";
        }
    }

    public void displayProductInfo() {
        System.out.println("--- Информация о товаре ---");
        System.out.println("ID: " + id);
        System.out.println("Название: " + name);
        System.out.println("Описание: " + (description == null || description.isEmpty() ? "Отсутствует" : description));
        System.out.printf("Цена: %.2f\n", price);
        System.out.println("Количество на складе: " + quantity);
        System.out.println("Статус наличия: " + getStockStatus()); // Используем новый метод
        if (category != null) {
            System.out.println("Категория: " + category.getName());
        }
        System.out.println("---------------------------");
    }
}
