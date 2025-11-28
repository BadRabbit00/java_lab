package product.service;

import product.Product;
import product.pricing.PricePolicy;
import product.tax.TaxPolicy;

public class ProfitCalculator {

    /**
     * Рассчитывает экономику одной продажи.
     */
    public FinancialReport calculate(Product product, int quantity, PricePolicy promotion, TaxPolicy taxPolicy) {
        if (quantity <= 0) return new FinancialReport(0, 0, 0);

        // 1. Считаем цену продажи с учетом скидок (Выручка магазина)
        double revenue = product.finalPrice(quantity, promotion);
        
        // 2. Считаем налог (он идет государству, в прибыль не входит)
        double tax = taxPolicy.calculateTax(revenue);
        
        // 3. Считаем себестоимость партии
        double totalCost = product.getCostPrice() * quantity;
        
        // 4. Формируем отчет
        return new FinancialReport(revenue, tax, totalCost);
    }
}
