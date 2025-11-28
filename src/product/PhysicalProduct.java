package product;

import product.shipping.Shippable;
import product.shipping.ShippingConstants;
import product.shipping.ShippingPolicy;
import product.shipping.StandardShipping;

/**
 * Подкласс для физических товаров. Наследует Product и добавляет
 * свойства, связанные с весом, размерами и доставкой.
 * Implements Shippable interface for shipping cost calculations.
 * Uses ShippingPolicy strategy for shipping cost calculation (SRP).
 */
public class PhysicalProduct extends Product implements Shippable {
    // --- Constants ---
    private static final double VOLUMETRIC_DIVISOR = 5000.0;

    // --- Уникальные атрибуты ---
    private double weightKg;
    private double lengthCm, widthCm, heightCm;
    
    // --- Стратегия доставки (SRP) ---
    private ShippingPolicy shippingPolicy;

    // --- Конструкторы ---
    public PhysicalProduct(String id, String name, String description, double price, int quantity,
                           double weightKg, double l, double w, double h, ShippingPolicy shippingPolicy) {
        super(id, name, description, price, quantity, null); // Вызов конструктора родителя
        trySetWeightKg(weightKg);
        trySetDimensions(l, w, h);
        this.shippingPolicy = shippingPolicy;
    }

    public PhysicalProduct(String id, String name, String description, double price, int quantity,
                           double weightKg, double l, double w, double h) {
        this(id, name, description, price, quantity, weightKg, l, w, h, new StandardShipping());
    }

    public PhysicalProduct(String id, String name, double price, double weightKg, ShippingPolicy shippingPolicy) {
        super(id, name, price); // Вызов конструктора родителя
        trySetWeightKg(weightKg);
        this.shippingPolicy = shippingPolicy;
    }

    public PhysicalProduct(String id, String name, double price, double weightKg) {
        this(id, name, price, weightKg, new StandardShipping());
    }

    public PhysicalProduct() {
        super(); // Вызов конструктора родителя без аргументов
        this.shippingPolicy = new StandardShipping();
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
        if (shippingPolicy != null) {
            return shippingPolicy.calculate(weightKg, lengthCm, widthCm, heightCm);
        }
        return getBillableWeight() * ShippingConstants.STANDARD_RATE_PER_KG; // Fallback rate
    }
    
    // --- Сеттер для стратегии доставки ---
    public void setShippingPolicy(ShippingPolicy policy) {
        this.shippingPolicy = policy;
    }
    
    // --- Геттер для стратегии доставки ---
    public ShippingPolicy getShippingPolicy() {
        return shippingPolicy;
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