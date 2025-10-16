package product;

/**
 * Базовый класс (суперкласс) для всех товаров.
 * Содержит общие свойства и методы, которые будут унаследованы подклассами.
 * Код из предыдущей лабораторной работы.
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
    private Object category; // Используем Object для упрощения в данном примере

    // --- Конструкторы ---
    public Product(String id, String name, String description, double price, int quantity, Object category) {
        if (!trySetId(id)) this.id = "AUTO-" + nextSeq();
        if (!trySetName(name)) this.name = "Unnamed";
        trySetDescription(description);
        if (!trySetPrice(price)) this.price = 0.0;
        if (!trySetQuantity(quantity)) this.quantity = 0;
        this.category = category;
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

    // --- Геттеры и другие методы ---
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
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