package com.example.models.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Sale;

public class CSVReader {
    public static List<Sale> readCSV(String filePath){
        List<Sale> sales = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split("[,;]");

                if (values.length < 7) {
                    System.err.println("Skipping invalid line: " + line);
                    continue;
                }

                try {
                    Sale sale = new Sale(
                        Integer.parseInt(values[0]),
                        values[1], // Change here!
                        values[2],
                        values[3],
                        Integer.parseInt(values[4]),
                        Double.parseDouble(values[5]),
                        Double.parseDouble(values[6])
                    );
                    sales.add(sale);
                } catch (Exception e) {
                    System.err.println("Error parsing row: " + line);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }

        return sales;
    }
    public static void main(String[] args) {
        String filePath = "C:\\Users\\RPC\\java_anaytics\\test.csv"; // Replace with your file path
        List<Sale> sales = readCSV(filePath);
        for (Sale sale : sales) {
            System.out.println(sale.getIdSale() + ", " + sale.getDate() + ", " + sale.getProductName() + ", " +
                    sale.getProductCategory() + ", " + sale.getQuantity() + ", " + sale.getPrice() + ", " +
                    sale.getTotalPrice());
        }
    }
}

