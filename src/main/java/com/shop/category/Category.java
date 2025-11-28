package com.shop.category;

import com.shop.product.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Category управляет списком продуктов.
 * Добавлены проверки для предотвращения добавления null или дубликатов.
 */
public class Category {
    private int id;
    private String name;
    private String description;
    private List<Product> products;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.products = new ArrayList<>();
    }

    /**
     * Добавляет товар в категорию с проверкой.
     * @param product Товар для добавления.
     * @return true, если товар успешно добавлен, иначе false (если товар null или уже существует).
     */
    public boolean addProduct(Product product) {
        if (product == null) {
            System.out.println("Ошибка: Нельзя добавить null в категорию '" + this.name + "'.");
            return false;
        }
        // Проверка на дубликат по ссылке на объект
        if (this.products.contains(product)) {
            System.out.println("Ошибка: Товар '" + product.getName() + "' уже существует в категории '" + this.name + "'.");
            return false;
        }
        
        this.products.add(product);
        product.trySetCategory(this); // Используем защищенный мутатор
        System.out.println("Товар '" + product.getName() + "' успешно добавлен в категорию '" + this.name + "'.");
        return true;
    }
    
    public void removeProduct(Product product) {
        if (this.products.remove(product)) {
            product.trySetCategory(null);
            System.out.println("Товар '" + product.getName() + "' удален из категории '" + this.name + "'.");
        } else {
            System.out.println("Ошибка: Товар '" + product.getName() + "' не найден в категории '" + this.name + "'.");
        }
    }
    
    public double getTotalValue() {
        double totalValue = 0.0;
        for (Product product : this.products) {
            totalValue += product.calculateTotalValue();
        }
        return totalValue;
    }
    
    public void displayCategoryInfo() {
        System.out.println("\n===== Информация о категории =====");
        System.out.println("ID: " + id);
        System.out.println("Название: " + name);
        if (products.isEmpty()) {
            System.out.println("В этой категории пока нет товаров.");
        } else {
            System.out.println("--- Товары в категории ---");
            for (Product product : products) {
                System.out.printf("- %s (Статус: %s, Кол-во: %d)\n",
                    product.getName(), product.getStockStatus(), product.getQuantity());
            }
        }
        System.out.println("==================================");
    }
    
    public String getName() { return name; }
    public void setDescription(String description) { this.description = description; }
}
