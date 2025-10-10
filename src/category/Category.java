// src/category/Category.java
package category;

import product.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Category представляет категорию товаров.
 * Он управляет списком продуктов, принадлежащих этой категории,
 * и предоставляет методы для расчета общей стоимости товаров в категории.
 */
public class Category {
    // --- Атрибуты ---
    private int id;
    private String name;
    private String description;
    private List<Product> products; // Список для хранения товаров в этой категории

    // --- Конструктор ---
    /**
     * Создает новую категорию с заданным ID и именем.
     * @param id Уникальный идентификатор категории.
     * @param name Название категории.
     */
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.products = new ArrayList<>(); // Инициализируем пустой список товаров
    }

    // --- Методы управления товарами ---

    /**
     * Добавляет товар в эту категорию.
     * @param product Объект Product для добавления.
     */
    public void addProduct(Product product) {
        this.products.add(product);
        product.setCategory(this); // Устанавливаем товару ссылку на эту категорию
        System.out.println("Товар '" + product.getName() + "' добавлен в категорию '" + this.name + "'.");
    }

    /**
     * Удаляет товар из этой категории.
     * @param product Объект Product для удаления.
     */
    public void removeProduct(Product product) {
        if (this.products.remove(product)) {
            product.setCategory(null); // Убираем у товара ссылку на категорию
            System.out.println("Товар '" + product.getName() + "' удален из категории '" + this.name + "'.");
        } else {
            System.out.println("Ошибка: Товар '" + product.getName() + "' не найден в категории '" + this.name + "'.");
        }
    }

    // --- Расчеты и информация ---

    /**
     * Рассчитывает общую стоимость всех товаров в данной категории.
     * @return Общая стоимость.
     */
    public double getTotalValue() {
        double totalValue = 0.0;
        // Проходим по каждому товару в списке и суммируем их общую стоимость
        for (Product product : this.products) {
            totalValue += product.calculateTotalValue();
        }
        return totalValue;
    }

    /**
     * Выводит информацию о категории и всех товарах в ней.
     */
    public void displayCategoryInfo() {
        System.out.println("\n===== Информация о категории =====");
        System.out.println("ID: " + id);
        System.out.println("Название: " + name);
        System.out.println("Описание: " + (description.isEmpty() ? "Отсутствует" : description));
        System.out.println("--- Товары в категории ---");
        if (products.isEmpty()) {
            System.out.println("В этой категории пока нет товаров.");
        } else {
            // Выводим краткую информацию о каждом товаре
            for (Product product : products) {
                System.out.printf("- %s (Количество: %d, Цена: %.2f)\n", 
                    product.getName(), product.getQuantity(), product.getPrice());
            }
        }
        System.out.println("==================================");
    }
    
    // --- Геттеры и Сеттеры ---
    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}