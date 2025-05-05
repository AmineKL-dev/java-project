package com.example.models.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.models.Sale;

import javafx.collections.ObservableList;

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

    // number of sold products by category
    public static Map<String,Integer> getUnitsSoldByCategory(){
        Map<String,Integer> unitSold = new HashMap<>();
        for(Sale sale : Sales){
            String category = sale.getProductCategory();
            int quantity = sale.getQuantity();
            if(unitSold.containsKey(category)){
                unitSold.put(category, unitSold.get(category)+quantity);
            }else{
                unitSold.put(category,quantity);
            }
        }
        return unitSold;
    }

    // Sales trend over time
    public static Map<String,Double> getSalesTrendByTheTime(){
        Map<String,Double> salesTrend = new HashMap<>();
        for(Sale sale : Sales){
            String date = sale.getDate();
            Double total = sale.getTotalPrice();
            if(salesTrend.containsKey(date)){
                salesTrend.put(date, salesTrend.get(date)+total);
            }else{
                salesTrend.put(date, total);
            }
        }
        return salesTrend.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())  // trier par date
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));
    }

    // Names of products most sold
    public static Map<String,Integer> getMostSoldProducts(){
        Map<String,Integer> mostSoldProducts = new HashMap<>();
        for(Sale sale : Sales){
            String productName = sale.getProductName();
            int quantity = sale.getQuantity();
            if(mostSoldProducts.containsKey(productName)){
                mostSoldProducts.put(productName, mostSoldProducts.get(productName)+quantity);
            }else{
                mostSoldProducts.put(productName, quantity);
            }
        }
        return mostSoldProducts.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue())
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));
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

    //Sales Distribution by product name
    public Map<String,Double> getSalesDistribution(ObservableList<Sale> sales){
        Map<String,Double> salesDist = new HashMap<>();
        for(Sale sale : sales){
            String productName = sale.getProductName();
            double totalPrice = sale.getTotalPrice();
            if(salesDist.containsKey(productName)){
                salesDist.put(productName, salesDist.get(productName)+totalPrice);
            }else{
                salesDist.put(productName,totalPrice);
            }
        }
        return salesDist;
    }

    // price unit of products by order
    public static Map<String,Double> getPriceProduct(){
        Map<String,Double> priceproduct = new HashMap<>();
        for(Sale sale : Sales){
            String productname = sale.getProductName();
            Double price = sale.getPrice();
            
            priceproduct.put(productname ,price);
            
        }
        return priceproduct.entrySet().stream()
        .sorted(Map.Entry.comparingByValue())  // trier par date
        .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
        ));
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
