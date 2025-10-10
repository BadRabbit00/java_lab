// src/product/Product.java
package product;

import category.Category;

/**
 * Класс Product представляет отдельный товар в магазине.
 * Он управляет информацией о товаре, такой как цена, количество на складе,
 * и предоставляет методы для управления запасами и применения скидок.
 */
public class Product {
    // --- Атрибуты ---
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private Category category;
    private boolean inStock;

    // --- Конструктор ---
    /**
     * Создает новый объект Product с заданными параметрами.
     * @param id Уникальный идентификатор товара.
     * @param name Название товара.
     * @param price Цена товара (должна быть неотрицательной).
     * @param quantity Количество товара на складе (должно быть неотрицательным).
     * @throws IllegalArgumentException если цена или количество отрицательные.
     */
    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = ""; // По умолчанию

        // Проверка корректности цены
        if (price < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной.");
        }
        this.price = price;

        // Проверка корректности количества
        if (quantity < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным.");
        }
        this.quantity = quantity;
        this.inStock = quantity > 0;
    }

    // --- Методы управления запасами ---

    /**
     * Добавляет указанное количество товара на склад.
     * @param amount Количество для добавления (должно быть положительным).
     */
    public void addStock(int amount) {
        if (amount > 0) {
            this.quantity += amount;
            this.inStock = true; // Если добавили товар, он точно в наличии
            System.out.println(amount + " единиц товара '" + name + "' добавлено на склад. Новое количество: " + this.quantity);
        } else {
            System.out.println("Ошибка: Нельзя добавить отрицательное или нулевое количество товара.");
        }
    }

    /**
     * Осуществляет продажу указанного количества товара.
     * @param amount Количество для продажи (должно быть положительным).
     */
    public void sellProduct(int amount) {
        if (amount <= 0) {
            System.out.println("Ошибка: Количество для продажи должно быть положительным.");
            return;
        }
        // Проверяем, достаточно ли товара на складе
        if (this.quantity >= amount) {
            this.quantity -= amount;
            System.out.println(amount + " единиц товара '" + name + "' продано. Остаток на складе: " + this.quantity);
            // Обновляем статус наличия
            if (this.quantity == 0) {
                this.inStock = false;
            }
        } else {
            System.out.println("Ошибка: Недостаточно товара '" + name + "' на складе для продажи. В наличии: " + this.quantity);
        }
    }

    // --- Бизнес-операции ---

    /**
     * Применяет процентную скидку к цене товара.
     * @param percentage Процент скидки (от 0 до 100).
     */
    public void applyDiscount(double percentage) {
        if (percentage >= 0 && percentage <= 100) {
            this.price *= (1 - percentage / 100.0);
            System.out.printf("К товару '%s' применена скидка %.1f%%. Новая цена: %.2f\n", name, percentage, price);
        } else {
            System.out.println("Ошибка: Процент скидки должен быть в диапазоне от 0 до 100.");
        }
    }

    /**
     * Рассчитывает общую стоимость всех единиц данного товара на складе.
     * @return Общая стоимость запасов.
     */
    public double calculateTotalValue() {
        return this.price * this.quantity;
    }

    // --- Информационный метод ---

    /**
     * Выводит полную информацию о продукте в консоль.
     */
    public void displayProductInfo() {
        System.out.println("--- Информация о товаре ---");
        System.out.println("ID: " + id);
        System.out.println("Название: " + name);
        System.out.println("Описание: " + (description.isEmpty() ? "Отсутствует" : description));
        System.out.printf("Цена: %.2f\n", price);
        System.out.println("Количество на складе: " + quantity);
        System.out.println("В наличии: " + (inStock ? "Да" : "Нет"));
        if (category != null) {
            System.out.println("Категория: " + category.getName());
        }
        System.out.println("---------------------------");
    }

    // --- Геттеры и Сеттеры (для доступа к приватным полям) ---
    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("Ошибка: Цена не может быть отрицательной.");
        }
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}