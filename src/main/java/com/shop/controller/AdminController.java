package com.shop.controller;

import com.shop.category.Category;
import com.shop.product.DigitalProduct;
import com.shop.product.PhysicalProduct;
import com.shop.product.Product;
import com.shop.product.pricing.*;
import com.shop.product.tax.*;
import com.shop.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for the admin panel.
 * Provides management for products, categories, and policies.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ShopService shopService;

    public AdminController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("productCount", shopService.getAllProducts().size());
        model.addAttribute("categoryCount", shopService.getAllCategories().size());
        model.addAttribute("pricePolicyCount", shopService.getAllPricePolicies().size());
        model.addAttribute("taxPolicyCount", shopService.getAllTaxPolicies().size());
        model.addAttribute("shippingPolicyCount", shopService.getAllShippingPolicies().size());
        return "admin/dashboard";
    }

    // ==================== PRODUCTS ====================
    
    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", shopService.getAllProducts());
        model.addAttribute("categories", shopService.getAllCategories());
        return "admin/products";
    }

    @GetMapping("/products/add")
    public String addProductForm(Model model) {
        model.addAttribute("categories", shopService.getAllCategories());
        model.addAttribute("shippingPolicies", shopService.getAllShippingPolicies().keySet());
        return "admin/product-form";
    }

    @PostMapping("/products/add/physical")
    public String addPhysicalProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam int quantity,
            @RequestParam double weight,
            @RequestParam String category,
            @RequestParam String shippingPolicy,
            RedirectAttributes redirectAttributes) {
        
        shopService.addPhysicalProduct(name, description, price, quantity, weight, category, shippingPolicy);
        redirectAttributes.addFlashAttribute("success", "Физический товар успешно добавлен!");
        return "redirect:/admin/products";
    }

    @PostMapping("/products/add/digital")
    public String addDigitalProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam int quantity,
            @RequestParam double downloadSize,
            @RequestParam String category,
            RedirectAttributes redirectAttributes) {
        
        shopService.addDigitalProduct(name, description, price, quantity, downloadSize, category);
        redirectAttributes.addFlashAttribute("success", "Цифровой товар успешно добавлен!");
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        return shopService.getProductById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("productId", id);
                    model.addAttribute("isDigital", product instanceof DigitalProduct);
                    model.addAttribute("isPhysical", product instanceof PhysicalProduct);
                    model.addAttribute("categories", shopService.getAllCategories());
                    return "admin/product-edit";
                })
                .orElse("redirect:/admin/products");
    }

    @PostMapping("/products/edit/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam int quantity,
            RedirectAttributes redirectAttributes) {
        
        shopService.updateProduct(id, name, description, price, quantity);
        redirectAttributes.addFlashAttribute("success", "Товар успешно обновлен!");
        return "redirect:/admin/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        shopService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("success", "Товар удален!");
        return "redirect:/admin/products";
    }

    // ==================== CATEGORIES ====================

    @GetMapping("/categories")
    public String listCategories(Model model) {
        model.addAttribute("categories", shopService.getAllCategories());
        return "admin/categories";
    }

    @PostMapping("/categories/add")
    public String addCategory(
            @RequestParam String name,
            @RequestParam String description,
            RedirectAttributes redirectAttributes) {
        
        shopService.createCategory(name, description);
        redirectAttributes.addFlashAttribute("success", "Категория успешно добавлена!");
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        shopService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("success", "Категория удалена!");
        return "redirect:/admin/categories";
    }

    // ==================== PRICE POLICIES ====================

    @GetMapping("/prices")
    public String listPricePolicies(Model model) {
        model.addAttribute("pricePolicies", shopService.getAllPricePolicies());
        return "admin/prices";
    }

    @PostMapping("/prices/add/percentage")
    public String addPercentageDiscount(
            @RequestParam String key,
            @RequestParam double percentage,
            RedirectAttributes redirectAttributes) {
        
        shopService.addPricePolicy(key, new PercentageOff(percentage));
        redirectAttributes.addFlashAttribute("success", "Процентная скидка добавлена!");
        return "redirect:/admin/prices";
    }

    @PostMapping("/prices/add/fixed")
    public String addFixedDiscount(
            @RequestParam String key,
            @RequestParam double amount,
            RedirectAttributes redirectAttributes) {
        
        shopService.addPricePolicy(key, new FixedOff(amount));
        redirectAttributes.addFlashAttribute("success", "Фиксированная скидка добавлена!");
        return "redirect:/admin/prices";
    }

    // ==================== TAX POLICIES ====================

    @GetMapping("/taxes")
    public String listTaxPolicies(Model model) {
        model.addAttribute("taxPolicies", shopService.getAllTaxPolicies());
        return "admin/taxes";
    }

    @PostMapping("/taxes/add/flat")
    public String addFlatVat(
            @RequestParam String key,
            @RequestParam double rate,
            RedirectAttributes redirectAttributes) {
        
        shopService.addTaxPolicy(key, new FlatVat(rate));
        redirectAttributes.addFlashAttribute("success", "Плоский НДС добавлен!");
        return "redirect:/admin/taxes";
    }

    @PostMapping("/taxes/add/notax")
    public String addNoTax(@RequestParam String key, RedirectAttributes redirectAttributes) {
        shopService.addTaxPolicy(key, new NoTax());
        redirectAttributes.addFlashAttribute("success", "Политика без налога добавлена!");
        return "redirect:/admin/taxes";
    }

    // ==================== SHIPPING POLICIES ====================

    @GetMapping("/shipping")
    public String listShippingPolicies(Model model) {
        model.addAttribute("shippingPolicies", shopService.getAllShippingPolicies());
        return "admin/shipping";
    }
}
