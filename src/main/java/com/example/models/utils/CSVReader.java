package com.example.models.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Sale;

class CSVReader {
    public static List<Sale> readCSV(String filePath){
        List<Sale> Sales = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            boolean isFirstLine = true;
            
            while((line = br.readLine()) != null){
                if(isFirstLine) {
                 // Skip the header line
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                Sale sale = new Sale(
                        Integer.parseInt(values[0]), // idSale
                        values[1], // date
                        values[2], // productName
                        values[3], // productCategory
                        Integer.parseInt(values[4]), // quantity
                        Double.parseDouble(values[5]), // price
                        Double.parseDouble(values[6]) // totalPrice
                );
                Sales.add(sale);
            }
            br.close();
            
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Sales;
    }
    public static void main(String[] args) {
        String filePath = "C:\\Users\\RPC\\java_anaytics\\test.csv"; // Update with your CSV file path
        List<Sale> sales = readCSV(filePath);
        
        for(Sale sale : sales) {
            System.out.println(sale.getIdSale() + " | " + sale.getDate() + " | " + sale.getProductName() + " | " +
                    sale.getProductCategory() + " | " + sale.getQuantity() + " | " + sale.getPrice() + " | " +
                    sale.getTotalPrice());
        }
    }
}