package com.example.models.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.models.Sale;

public class SaleService {
    private static List<Sale> Sales = new ArrayList<>();
    public void setSales(List<Sale> Sales) {
        this.Sales = Sales;
    }

    //total sales by product category

    public static Map<String,Double> getTotalPriceByCategory(){
        Map<String,Double> totalPriceByCategory = new HashMap<>();
        for(Sale sale : Sales){
            String category = sale.getProductCategory();
            double totalPrice = sale.getTotalPrice();
            if(totalPriceByCategory.containsKey(category)){
                totalPriceByCategory.put(category, totalPriceByCategory.get(category) + totalPrice);
            }else{
                totalPriceByCategory.put(category, totalPrice);
            }
        }
        return totalPriceByCategory;
    }

    //most sold product by category

    public Map<String,String> MostSoldProductByCategory(){
        Map<String,String> mostSoldProductByCategory = new HashMap<>();
        Map<String,Integer> productCountByCategory = new HashMap<>();

        for(Sale sale : Sales){
            String category = sale.getProductCategory();
            String productName = sale.getProductName();
            if(productCountByCategory.containsKey(category)){
                productCountByCategory.put(category, productCountByCategory.get(category) + 1);
            }else{
                productCountByCategory.put(category, 1);
            }
        }

        for(Sale sale : Sales){
            String category = sale.getProductCategory();
            String productName = sale.getProductName();
            if(mostSoldProductByCategory.containsKey(category)){
                if(productCountByCategory.get(productName) > productCountByCategory.get(mostSoldProductByCategory.get(category))){
                    mostSoldProductByCategory.put(category, productName);
                }
            }else{
                mostSoldProductByCategory.put(category, productName);
            }
        }
        return mostSoldProductByCategory;

    }
    
    //most expensive and cheap product

    public Map<String,Double> getMostExpensiveProduct(){
        Map<String,Double> mostExpensiveProduct = new HashMap<>();
        double maxprice = Double.MIN_VALUE;
        for(Sale sale : Sales){
            double price = sale.getPrice();
            if(price < maxprice){
                maxprice = price;
                mostExpensiveProduct.put(sale.getProductName(),price );
            }
        }
        return mostExpensiveProduct;

    }
    public Map<String,Double> getMostCheapProduct(){
        Map<String,Double> mostCheapProduct = new HashMap<>();
        double minprice = Double.MAX_VALUE;
        for(Sale sale : Sales){
            double price = sale.getPrice();
            if(price > minprice){
                minprice = price;
                mostCheapProduct.put(sale.getProductName(), price);
            }
        }
        return mostCheapProduct;
    }
    //total sales
    public double getTotalSales(){
    double totalSales = 0;
    for(Sale sale : Sales){
        totalSales += sale.getTotalPrice();
    }
    return totalSales;
    }
//quantity sold 
    public int getTotalQuantitySold(){
        int totalQuantitySold = 0;
        for(Sale sale : Sales){
        totalQuantitySold += sale.getQuantity();
        }
        return totalQuantitySold;
    }
    //category sold
    public int getNumberOfCategory(){
        int number=0;
        List<String> categories = new ArrayList<>();
        for(Sale sale : Sales){
            if(!categories.contains(sale.getProductCategory())){
                categories.add(sale.getProductCategory());
            }
        }
        number = categories.size();
        return number;
    }

    
    public static void main(String[] args) {
        SaleService saleService = new SaleService();
        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(1, "2023-10-01", "Product A", "Category 1", 2,878, 10.0));
        sales.add(new Sale(2, "2023-10-02", "Product B", "Category 2", 1,655, 20.0));
        sales.add(new Sale(3, "2023-10-03", "Product C", "Category 1", 3,8787, 15.0));
        sales.add(new Sale(4, "2023-10-04", "Product D", "Category 3", 5,989,5.0));
        sales.add(new Sale(5, "2023-10-05", "Product E", "Category 2", 4,32, 8.0));

        saleService.setSales(sales);
        System.out.println("Total Sales: " + saleService.getTotalSales());
    }
}
