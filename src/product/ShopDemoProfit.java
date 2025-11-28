package product;

import product.pricing.PercentagePromotion;
import product.service.FinancialReport;
import product.service.ProfitCalculator;
import product.tax.FlatVat;

public class ShopDemoProfit {
    public static void main(String[] args) {
        System.out.println("=== РАСЧЕТ ЧИСТОЙ ПРИБЫЛИ (Лабораторная) ===\n");

        // 1. Создаем товар: Игровой ноутбук
        // Продаем за 450 000, но купили у поставщика за 350 000
        PhysicalProduct laptop = new PhysicalProduct("L-1", "Gaming Laptop", 450_000, 2.5);
        laptop.setCostPrice(350_000); 

        // 2. Условия продажи
        int qty = 1;
        PercentagePromotion sale10 = new PercentagePromotion(10); // Скидка 10%
        FlatVat vat12 = new FlatVat(12); // НДС 12%
        
        // 3. Запуск калькулятора
        ProfitCalculator calculator = new ProfitCalculator();
        FinancialReport report = calculator.calculate(laptop, qty, sale10, vat12);
        
        // 4. Вывод результата
        report.printReport(laptop.getName());
        
        // Пример убыточной продажи (слишком большая скидка)
        System.out.println("--- Тест убыточной продажи (Скидка 30%) ---");
        PercentagePromotion hugeSale = new PercentagePromotion(30);
        FinancialReport badReport = calculator.calculate(laptop, qty, hugeSale, vat12);
        badReport.printReport(laptop.getName());
    }
}
