package product.shipping;

/**
 * Shared constants for shipping calculations.
 * Centralized to follow DRY principle and facilitate maintenance.
 */
public final class ShippingConstants {
    
    private ShippingConstants() {
        // Prevent instantiation
    }
    
    /** Divisor for volumetric weight calculation (L * W * H / DIVISOR) */
    public static final double VOLUMETRIC_DIVISOR = 5000.0;
    
    /** Standard shipping rate in KZT per kilogram */
    public static final double STANDARD_RATE_PER_KG = 100.0;
    
    /** Express shipping rate in KZT per kilogram (2x standard) */
    public static final double EXPRESS_RATE_PER_KG = 200.0;
    
    /** Fixed express shipping fee in KZT */
    public static final double EXPRESS_FEE = 500.0;
    
    /** Weight threshold for free shipping in kilograms */
    public static final double FREE_WEIGHT_THRESHOLD = 2.0;
}
