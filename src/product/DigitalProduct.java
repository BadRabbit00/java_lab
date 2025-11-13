package product;

/**
 * Подкласс для цифровых товаров. Наследует Product и добавляет
 * свойства, связанные с размером файла и лицензированием.
 */
public class DigitalProduct extends Product {
    // --- Уникальные атрибуты ---
    private double downloadSizeMb;
    private String licenseKey;

    // --- Конструкторы ---
    public DigitalProduct(String id, String name, String description, double price, int quantity,
                          double downloadSizeMb, String licenseKey) {
        super(id, name, description, price, quantity, null); // Вызов конструктора родителя
        trySetDownloadSizeMb(downloadSizeMb);
        trySetLicenseKey(licenseKey);
    }

    public DigitalProduct(String id, String name, double price, double downloadSizeMb) {
        super(id, name, price); // Вызов конструктора родителя
        trySetDownloadSizeMb(downloadSizeMb);
    }

    public DigitalProduct() {
        super(); // Вызов конструктора родителя без аргументов
    }

    // --- Уникальные защищенные мутаторы ---
    public boolean trySetDownloadSizeMb(double sizeMb) {
        if (sizeMb >= 0 && sizeMb <= 1_000_000) {
            this.downloadSizeMb = sizeMb;
            if (sizeMb < 500000){
                applyDiscount(10);
                return true;
            }
            return true;
        }
        return false;
    }

    public boolean trySetLicenseKey(String key) {
        if (key == null || key.length() <= 64) {
            this.licenseKey = key;
            return true;
        }
        return false;
    }

    // --- Уникальный метод бизнес-логики ---
    public boolean isLicenseRequired() {
        return licenseKey != null && !licenseKey.isBlank();
    }

    // --- Геттеры для новых полей ---
    public double getDownloadSizeMb() {
        return downloadSizeMb;
    }

    // --- Переопределение toString ---
    @Override
    public String toString() {
        return super.toString() + String.format(" | Digital[size=%.2fMB, license=%b]",
                downloadSizeMb, isLicenseRequired());
    }
}