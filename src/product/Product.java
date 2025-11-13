package product;

// Импортируем класс Category, так как он нам снова нужен
import category.Category;

/**
 * Базовый класс (суперкласс) для всех товаров.
 * Содержит общие свойства и методы, которые будут унаследованы подклассами.
 * В ЭТОЙ ВЕРСИИ ВОССТАНОВЛЕНЫ МЕТОДЫ ДЛЯ СОВМЕСТИМОСТИ С CATEGORY.
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
    // ВОЗВРАЩАЕМ ТИП Category вместо Object для корректной работы
    private Category category; 

    // --- Конструкторы ---
    // Обновляем конструктор, чтобы он принимал Category
    public Product(String id, String name, String description, double price, int quantity, Category category) {
        if (!trySetId(id)) this.id = "AUTO-" + nextSeq();
        if (!trySetName(name)) this.name = "Unnamed";
        trySetDescription(description);
        if (!trySetPrice(price)) this.price = 0.0;
        if (!trySetQuantity(quantity)) this.quantity = 0;
        trySetCategory(category); // Используем наш восстановленный метод
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
        Product sample = new Product("AUTO-" + nextSeq(), name, 0.0);
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

    // --- ВОССТАНОВЛЕННЫЕ МЕТОДЫ ---
    /**
     * (ВОССТАНОВЛЕНО) Устанавливает категорию.
     */
    public boolean trySetCategory(Category category) {
        this.category = category;
        return true;
    }
    
    /**
     * (ВОССТАНОВЛЕНО) Рассчитывает общую стоимость запасов этого товара.
     */
    public double calculateTotalValue() {
        return this.price * this.quantity;
    }

    /**
     * (ВОССТАНОВЛЕНО) Применяет скидку (для совместимости со старыми ShopDemo).
     */
    public boolean applyDiscount(double percentage) {
        if (percentage >= 0 && percentage <= 90) { // Правило из лаб. 2
            this.price *= (1 - percentage / 100.0);
            return true;
        }
        return false;
    }

    /**
     * (ВОССТАНОВЛЕНО) Отображает информацию (для совместимости со старыми ShopDemo).
     */
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

    // --- Геттеры и другие методы ---
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public Category getCategory() { return category; } // Добавим геттер

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

