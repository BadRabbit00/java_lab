package product;

import product.shipping.Shippable;

/**
 * Подкласс для физических товаров. Наследует Product и добавляет
 * свойства, связанные с весом, размерами и доставкой.
 * Implements Shippable interface for shipping cost calculations.
 */
public class PhysicalProduct extends Product implements Shippable {
    // --- Constants ---
    private static final double VOLUMETRIC_DIVISOR = 5000.0;
    private static final double SHIPPING_RATE_PER_KG = 100.0; // 100 KZT per kg

    // --- Уникальные атрибуты ---
    private double weightKg;
    private double lengthCm, widthCm, heightCm;

    // --- Конструкторы ---
    public PhysicalProduct(String id, String name, String description, double price, int quantity,
                           double weightKg, double l, double w, double h) {
        super(id, name, description, price, quantity, null); // Вызов конструктора родителя
        trySetWeightKg(weightKg);
        trySetDimensions(l, w, h);
    }

    public PhysicalProduct(String id, String name, double price, double weightKg) {
        super(id, name, price); // Вызов конструктора родителя
        trySetWeightKg(weightKg);
    }

    public PhysicalProduct() {
        super(); // Вызов конструктора родителя без аргументов
    }

    // --- Уникальные защищенные мутаторы ---
    public boolean trySetWeightKg(double weightKg) {
        if (weightKg >= 0 && weightKg <= 1000) {
            this.weightKg = weightKg;
            return true;
        }
        return false;
    }

    public boolean trySetDimensions(double lengthCm, double widthCm, double heightCm) {
        if (lengthCm >= 0 && lengthCm <= 1000 &&
            widthCm >= 0 && widthCm <= 1000 &&
            heightCm >= 0 && heightCm <= 1000) {
            this.lengthCm = lengthCm;
            this.widthCm = widthCm;
            this.heightCm = heightCm;
            return true;
        }
        return false;
    }

    // --- Уникальный метод бизнес-логики ---
    public double estimateShippingCost() {
        return getBillableWeight() * SHIPPING_RATE_PER_KG;
    }

    // --- Implementation of Shippable interface ---
    @Override
    public double calculateShippingCost() {
        return estimateShippingCost();
    }

    @Override
    public double getShippingWeight() {
        return getBillableWeight();
    }

    // --- Private helper method to eliminate duplication ---
    private double getBillableWeight() {
        double volumetricWeight = (lengthCm * widthCm * heightCm) / VOLUMETRIC_DIVISOR;
        return Math.max(weightKg, volumetricWeight);
    }
    
    // --- Геттеры для новых полей ---
    public double getWeightKg() {
        return weightKg;
    }

    // --- Переопределение toString ---
    @Override
    public String toString() {
        return super.toString() + String.format(" | Physical[weight=%.2fkg]", weightKg);
    }
}