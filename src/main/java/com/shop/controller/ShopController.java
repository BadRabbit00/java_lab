package com.shop.controller;

import com.shop.category.Category;
import com.shop.product.DigitalProduct;
import com.shop.product.PhysicalProduct;
import com.shop.product.Product;
import com.shop.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for the main shop pages.
 */
@Controller
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false) String category) {
        List<Product> products;
        if (category != null && !category.isEmpty()) {
            products = shopService.getProductsByCategory(category);
            model.addAttribute("selectedCategory", category);
        } else {
            products = shopService.getAllProducts();
        }
        
        model.addAttribute("products", products);
        model.addAttribute("categories", shopService.getAllCategories());
        
        // Add product type info
        Map<String, String> productTypes = products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        p -> p instanceof DigitalProduct ? "digital" : "physical"
                ));
        model.addAttribute("productTypes", productTypes);
        
        // Add numeric IDs for products
        Map<String, Long> productNumericIds = products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        p -> shopService.getNumericId(p)
                ));
        model.addAttribute("productNumericIds", productNumericIds);
        
        return "shop";
    }

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        return shopService.getProductById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("productId", id);
                    model.addAttribute("isDigital", product instanceof DigitalProduct);
                    model.addAttribute("isPhysical", product instanceof PhysicalProduct);
                    
                    if (product instanceof PhysicalProduct pp) {
                        model.addAttribute("shippingCost", pp.estimateShippingCost());
                    }
                    
                    if (product instanceof DigitalProduct dp) {
                        model.addAttribute("downloadSize", dp.getDownloadSizeMb());
                    }
                    
                    // Get available price policies for price calculation demo
                    model.addAttribute("pricePolicies", shopService.getAllPricePolicies());
                    model.addAttribute("taxPolicies", shopService.getAllTaxPolicies());
                    
                    return "product-detail";
                })
                .orElse("redirect:/");
    }
}
