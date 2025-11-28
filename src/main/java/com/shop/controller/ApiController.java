package com.shop.controller;

import com.shop.service.ShopService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST API controller for price calculations and other API endpoints.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final ShopService shopService;

    public ApiController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/calculate")
    public Map<String, Object> calculatePrice(
            @RequestParam Long productId,
            @RequestParam int quantity,
            @RequestParam(required = false) String pricePolicy,
            @RequestParam(required = false) String taxPolicy) {
        
        double total = shopService.calculateFinalPrice(productId, quantity, pricePolicy, taxPolicy);
        
        return Map.of(
            "productId", productId,
            "quantity", quantity,
            "pricePolicy", pricePolicy != null ? pricePolicy : "none",
            "taxPolicy", taxPolicy != null ? taxPolicy : "none",
            "total", total
        );
    }
}
