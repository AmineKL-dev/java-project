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

}
