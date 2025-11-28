package com.shop.service;

import com.shop.category.Category;
import com.shop.product.DigitalProduct;
import com.shop.product.PhysicalProduct;
import com.shop.product.Product;
import com.shop.product.pricing.*;
import com.shop.product.shipping.*;
import com.shop.product.tax.*;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service for managing shop data (products, categories, policies).
 * Uses in-memory storage with thread-safe collections.
 */
@Service
public class ShopService {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final Map<Long, Category> categories = new ConcurrentHashMap<>();
    private final Map<String, PricePolicy> pricePolicies = new ConcurrentHashMap<>();
    private final Map<String, TaxPolicy> taxPolicies = new ConcurrentHashMap<>();
    private final Map<String, ShippingPolicy> shippingPolicies = new ConcurrentHashMap<>();

    private final AtomicLong productIdGenerator = new AtomicLong(1);
    private final AtomicLong categoryIdGenerator = new AtomicLong(1);

    @PostConstruct
    public void init() {
        // Initialize default policies
        initDefaultPolicies();
        // Initialize sample data
        initSampleData();
    }

    private void initDefaultPolicies() {
        // Tax policies
        taxPolicies.put("flat-vat-12", new FlatVat(12));
        taxPolicies.put("flat-vat-20", new FlatVat(20));
        taxPolicies.put("no-tax", new NoTax());
        taxPolicies.put("progressive", new ProgressiveTaxStrategy());
        taxPolicies.put("digital-vat-5", new ReducedDigitalVat(5));

        // Price policies
        pricePolicies.put("percentage-10", new PercentageOff(10));
        pricePolicies.put("percentage-20", new PercentageOff(20));
        pricePolicies.put("percentage-30", new PercentageOff(30));
        pricePolicies.put("fixed-500", new FixedOff(500));
        pricePolicies.put("fixed-1000", new FixedOff(1000));
        pricePolicies.put("bogo-half", new BogoHalf());
        pricePolicies.put("buy3-pay2", new Buy3Pay2Promotion());

        // Shipping policies
        shippingPolicies.put("standard", new StandardShipping());
        shippingPolicies.put("express", new ExpressShipping());
        shippingPolicies.put("free", new FreeShipping());
    }

    private void initSampleData() {
        // Create categories
        Category electronics = createCategory("Электроника", "Смартфоны, ноутбуки, гаджеты");
        Category software = createCategory("Программы", "Лицензии и цифровые товары");
        Category accessories = createCategory("Аксессуары", "Кабели, чехлы, зарядки");
        Category gaming = createCategory("Игры", "Видеоигры и игровые товары");

        // Create physical products
        addPhysicalProduct("iPhone 15 Pro", "Новейший флагман Apple с чипом A17 Pro", 
            499900, 10, 0.187, electronics.getName(), "standard");
        addPhysicalProduct("MacBook Pro 16\"", "Мощный ноутбук для профессионалов", 
            1299900, 5, 2.14, electronics.getName(), "standard");
        addPhysicalProduct("Samsung Galaxy S24 Ultra", "Флагманский Android смартфон", 
            449900, 15, 0.233, electronics.getName(), "standard");
        addPhysicalProduct("Sony WH-1000XM5", "Премиальные наушники с шумоподавлением", 
            149900, 20, 0.25, accessories.getName(), "standard");
        addPhysicalProduct("Apple Watch Ultra 2", "Профессиональные смарт-часы", 
            349900, 8, 0.061, electronics.getName(), "standard");
        addPhysicalProduct("PlayStation 5 Pro", "Игровая консоль нового поколения", 
            349900, 12, 3.2, gaming.getName(), "standard");
        addPhysicalProduct("Nintendo Switch OLED", "Гибридная игровая консоль", 
            179900, 18, 0.42, gaming.getName(), "standard");
        addPhysicalProduct("Xbox Elite Controller", "Профессиональный геймпад", 
            89900, 25, 0.35, gaming.getName(), "standard");
        addPhysicalProduct("USB-C Hub 7-in-1", "Многофункциональный хаб", 
            14900, 50, 0.12, accessories.getName(), "free");
        addPhysicalProduct("MagSafe Charger", "Беспроводная зарядка для iPhone", 
            19900, 40, 0.05, accessories.getName(), "free");

        // Create digital products
        addDigitalProduct("Microsoft Office 365", "Годовая подписка на пакет Office", 
            39900, 100, 4096, software.getName());
        addDigitalProduct("Adobe Creative Cloud", "Полный набор программ Adobe", 
            59900, 100, 25600, software.getName());
        addDigitalProduct("Windows 11 Pro", "Лицензия на операционную систему", 
            69900, 100, 5120, software.getName());
        addDigitalProduct("Cyberpunk 2077", "Ролевая игра в жанре киберпанк", 
            24900, 100, 70000, gaming.getName());
        addDigitalProduct("Elden Ring", "Эпическая ролевая игра от FromSoftware", 
            29900, 100, 50000, gaming.getName());
    }

    // Category operations
    public Category createCategory(String name, String description) {
        long id = categoryIdGenerator.getAndIncrement();
        Category category = new Category((int) id, name);
        category.setDescription(description);
        categories.put(id, category);
        return category;
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories.values());
    }

    public Optional<Category> getCategoryById(Long id) {
        return Optional.ofNullable(categories.get(id));
    }

    public Optional<Category> getCategoryByName(String name) {
        return categories.values().stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public void deleteCategory(Long id) {
        categories.remove(id);
    }

    // Product operations
    public Product addPhysicalProduct(String name, String description, double price, 
            int quantity, double weightKg, String categoryName, String shippingPolicyKey) {
        long id = productIdGenerator.getAndIncrement();
        ShippingPolicy policy = shippingPolicies.getOrDefault(shippingPolicyKey, new StandardShipping());
        
        PhysicalProduct product = new PhysicalProduct(
            "P-" + id, name, description, price, quantity, weightKg, 30, 20, 10, policy);
        
        Optional<Category> category = getCategoryByName(categoryName);
        category.ifPresent(c -> c.addProduct(product));
        
        products.put(id, product);
        return product;
    }

    public Product addDigitalProduct(String name, String description, double price, 
            int quantity, double downloadSizeMb, String categoryName) {
        long id = productIdGenerator.getAndIncrement();
        
        DigitalProduct product = new DigitalProduct(
            "D-" + id, name, description, price, quantity, downloadSizeMb, "LICENSE-" + id);
        
        Optional<Category> category = getCategoryByName(categoryName);
        category.ifPresent(c -> c.addProduct(product));
        
        products.put(id, product);
        return product;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> getProductsByCategory(String categoryName) {
        return products.values().stream()
                .filter(p -> p.getCategory() != null && 
                        p.getCategory().getName().equalsIgnoreCase(categoryName))
                .toList();
    }

    public void deleteProduct(Long id) {
        Product product = products.remove(id);
        if (product != null && product.getCategory() != null) {
            product.getCategory().removeProduct(product);
        }
    }

    public void updateProduct(Long id, String name, String description, double price, int quantity) {
        Product product = products.get(id);
        if (product != null) {
            product.trySetName(name);
            product.trySetDescription(description);
            product.trySetPrice(price);
            product.trySetQuantity(quantity);
        }
    }

    // Policy operations
    public Map<String, PricePolicy> getAllPricePolicies() {
        return new HashMap<>(pricePolicies);
    }

    public Map<String, TaxPolicy> getAllTaxPolicies() {
        return new HashMap<>(taxPolicies);
    }

    public Map<String, ShippingPolicy> getAllShippingPolicies() {
        return new HashMap<>(shippingPolicies);
    }

    public void addPricePolicy(String key, PricePolicy policy) {
        pricePolicies.put(key, policy);
    }

    public void addTaxPolicy(String key, TaxPolicy policy) {
        taxPolicies.put(key, policy);
    }

    public void addShippingPolicy(String key, ShippingPolicy policy) {
        shippingPolicies.put(key, policy);
    }

    public Optional<PricePolicy> getPricePolicy(String key) {
        return Optional.ofNullable(pricePolicies.get(key));
    }

    public Optional<TaxPolicy> getTaxPolicy(String key) {
        return Optional.ofNullable(taxPolicies.get(key));
    }

    public Optional<ShippingPolicy> getShippingPolicy(String key) {
        return Optional.ofNullable(shippingPolicies.get(key));
    }

    // Calculate price with policies
    public double calculateFinalPrice(Long productId, int quantity, String pricePolicyKey, String taxPolicyKey) {
        Product product = products.get(productId);
        if (product == null) return 0;

        double price;
        if (pricePolicyKey != null && pricePolicies.containsKey(pricePolicyKey)) {
            price = product.finalPrice(quantity, pricePolicies.get(pricePolicyKey));
        } else {
            price = product.finalPrice(quantity);
        }

        if (taxPolicyKey != null && taxPolicies.containsKey(taxPolicyKey)) {
            price += taxPolicies.get(taxPolicyKey).calculateTax(price);
        }

        return price;
    }

    // Get product by string ID
    public Optional<Product> getProductByStringId(String id) {
        return products.values().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Long getNumericId(Product product) {
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            if (entry.getValue().equals(product)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
