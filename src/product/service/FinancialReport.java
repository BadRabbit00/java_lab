package product.service;

/**
 * DTO (Data Transfer Object) для хранения результатов финансового расчета.
 */
public class FinancialReport {
    private double revenue;       // Выручка (грязная)
    private double taxAmount;     // Сумма налога
    private double netRevenue;    // Выручка без налога
    private double costOfGoods;   // Себестоимость
    private double netProfit;     // Чистая прибыль

    public FinancialReport(double revenue, double taxAmount, double costOfGoods) {
        this.revenue = revenue;
        this.taxAmount = taxAmount;
        this.costOfGoods = costOfGoods;
        
        // Считаем производные показатели
        // Предполагаем, что налог включен в цену (или начисляется сверху - зависит от вашей TaxPolicy)
        // В текущей реализации TaxPolicy начисляет налог СВЕРХУ цены.
        // Значит: Клиент платит (Revenue + Tax).
        // Магазин получает Revenue.
        // Государство получает Tax.
        
        this.netRevenue = revenue; 
        this.netProfit = netRevenue - costOfGoods;
    }

    public void printReport(String productName) {
        System.out.println("\n===== ФИНАНСОВЫЙ ОТЧЕТ: " + productName + " =====");
        System.out.printf("1. Выручка магазина:          %10.2f KZT\n", revenue);
        System.out.printf("   (Налог к уплате):          %10.2f KZT\n", taxAmount);
        System.out.println("   --------------------------------------");
        System.out.printf("2. Себестоимость (Закупка):  -%10.2f KZT\n", costOfGoods);
        System.out.println("   --------------------------------------");
        System.out.printf("3. ЧИСТАЯ ПРИБЫЛЬ:            %10.2f KZT\n", netProfit);
        
        if (netProfit < 0) {
            System.out.println("   [!!!] ВНИМАНИЕ: Сделка убыточна!");
        } else {
            double margin = (netProfit / revenue) * 100;
            System.out.printf("   Маржинальность:            %10.1f %%\n", margin);
        }
        System.out.println("=========================================\n");
    }
}
