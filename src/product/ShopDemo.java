package product;

/**
 * Демонстрация работы с иерархией классов Product, PhysicalProduct и DigitalProduct.
 */
public class ShopDemo {
    public static void main(String[] args) {
        System.out.println("📦 Демонстрация наследования и подтипов продуктов 📦\n");

        // --- 1. Создание объектов подклассов ---
        System.out.println("--- 1. Создание разных типов продуктов ---");
        // Физический товар
        PhysicalProduct laptop = new PhysicalProduct("LP-1", "Игровой ноутбук", 750000.0, 2.5);
        laptop.trySetDimensions(35, 25, 2.5);

        // Цифровой товар
        DigitalProduct ebook = new DigitalProduct("EB-1", "Книга по Java", "Полное руководство", 12000.0, 999, 15.5, "JAVA-BOOK-LICENSE-KEY");

        System.out.println("Создан физический товар: " + laptop);
        System.out.println("Создан цифровой товар: " + ebook);

        // --- 2. Демонстрация валидных и невалидных обновлений ---
        System.out.println("\n--- 2. Тестирование защищенных методов подклассов ---");

        System.out.println("--> Попытка установить корректный вес для ноутбука (2.6 кг)...");
        boolean weightSet = laptop.trySetWeightKg(2.6);
        System.out.println("Результат: " + weightSet + ". Новый вес: " + laptop.getWeightKg() + " кг");

        System.out.println("\n--> Попытка установить некорректные размеры (-10, 20, 20)...");
        boolean dimsSet = laptop.trySetDimensions(-10, 20, 20);
        System.out.println("Результат: " + dimsSet + " (размеры не изменились)");

        System.out.println("\n--> Попытка установить корректный размер загрузки для книги (20 МБ)...");
        boolean sizeSet = ebook.trySetDownloadSizeMb(20.0);
        System.out.println("Результат: " + sizeSet + ". Новый размер: " + ebook.getDownloadSizeMb() + " МБ");


        // --- 3. Вызов методов, специфичных для подклассов ---
        System.out.println("\n--- 3. Вызов уникальных методов ---");

        double shippingCost = laptop.estimateShippingCost();
        System.out.printf("Примерная стоимость доставки ноутбука: %.2f %s\n", shippingCost, Product.DEFAULT_CURRENCY);

        boolean licenseRequired = ebook.isLicenseRequired();
        System.out.println("Требуется ли лицензия для электронной книги? " + licenseRequired);

        // --- 4. Финальный вывод ---
        System.out.println("\n--- Финальное состояние объектов ---");
        System.out.println(laptop);
        System.out.println(ebook);
    }
}