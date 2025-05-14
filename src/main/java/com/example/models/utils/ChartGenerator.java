package com.example.models.utils;

import com.example.models.Sale;
import com.example.models.services.SaleService;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Tooltip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartGenerator {

    public static BarChart<String, Number> createTotalSalesByCategoryChart(ObservableList<Sale> sales) {
        // Create axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        // Create the bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Total Sales by Category");
        barChart.setLegendVisible(false);

        // Prepare data
        Map<String, Double> categorySales = new HashMap<>();
        for (Sale sale : sales) {
            categorySales.merge(sale.getProductCategory(), sale.getTotalPrice(), Double::sum);
        }

        // Create a series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales");

        // Add data to series
        for (Map.Entry<String, Double> entry : categorySales.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Add series to chart
        barChart.getData().add(series);

        return barChart;
    }



    // create barchart for sold units by category
    public static BarChart<String,Number> createUnitSoldByCategoryChart(ObservableList<Sale> sales){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        Map<String,Integer> unitSold = new HashMap<>();
        unitSold = SaleService.getUnitsSoldByCategory();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("total sold units");
        barChart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Units Sold");

        for (Map.Entry<String, Integer> entry : unitSold.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        barChart.getData().add(series);
        return barChart;
    }

    // create lineChart for sales trend over time
    public static LineChart<String,Number> createSalesTrendByTimeLine(ObservableList<Sale> sales){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        Map<String,Double> salesTrend = new HashMap<>();
        salesTrend = SaleService.getSalesTrendByTheTime();

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Sales Trend Over Time");
        lineChart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales Trend");

        for (Map.Entry<String, Double> entry : salesTrend.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        lineChart.getData().add(series);
        return lineChart;
    }

    // create horizontal barchart for most sold products
    public static BarChart<Number,String> createMostSoldProductChart(ObservableList<Sale> sales){
        final CategoryAxis yAxis = new CategoryAxis();
        final NumberAxis xAxis = new NumberAxis();

        Map<String,Integer> MostSold = new HashMap<>();
        MostSold = SaleService.getMostSoldProducts();
        BarChart<Number,String> barChart = new BarChart<>(xAxis,yAxis);
        barChart.setTitle("Most Sold Product");
        barChart.setLegendVisible(false);

        Series<Number, String> series = new XYChart.Series<>();
        series.setName("Most Sold Products");

        for(Map.Entry<String,Integer> entry : MostSold.entrySet()){
            series.getData().add(new XYChart.Data<>(entry.getValue(),entry.getKey()));
        }
        barChart.getData().add(series);
        return barChart;
    }




    // DASHBOARD

    // create piechart for most sold products
    public static PieChart createPieChart(ObservableList<Sale> sales){
        Map<String,Integer> MostSold = new HashMap<>();
        MostSold = SaleService.getMostSoldProducts();

        int totalSold = MostSold.values().stream().mapToInt(Integer::intValue).sum();

        PieChart pieChart = new PieChart();
        pieChart.setTitle("Distrubition of sales");
        pieChart.setLegendVisible(false);

        for (Map.Entry<String, Integer> entry : MostSold.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalSold;
            PieChart.Data data = new PieChart.Data(
                String.format("%s (%.1f%%)", entry.getKey(), percentage),
                entry.getValue()
            );
            pieChart.getData().add(data);
        }
        // Add tooltips with detailed information
        for (PieChart.Data data : pieChart.getData()) {
            String name = data.getName();
            int value = (int) data.getPieValue();
            double percentage = (value * 100.0) / totalSold;
            
            Tooltip tooltip = new Tooltip(
                String.format("%s\nSold: %d units\n%.1f%% of total", 
                    name, value, percentage)
            );
            Tooltip.install(data.getNode(), tooltip);
            
            
            data.getNode().setOnMouseEntered(e -> {
                data.getNode().setScaleX(1.05);
                data.getNode().setScaleY(1.05);
            });
            data.getNode().setOnMouseExited(e -> {
                data.getNode().setScaleX(1.0);
                data.getNode().setScaleY(1.0);
            });
        }
    
        
        pieChart.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-pie-label-visible: true;" +
            "-fx-label-line-length: 20;"
        );
        
        return pieChart;
    }


    // Price Product in DASHBOARD 
    public static BarChart<Number,String> priceProductChart(ObservableList<Sale> sales){
        final CategoryAxis yAxis = new CategoryAxis();
        final NumberAxis xAxis = new NumberAxis();

        Map<String,Double> PriceProduct = new HashMap<>();
        PriceProduct = SaleService.getPriceProduct();
        BarChart<Number,String> barchart = new BarChart<>(xAxis,yAxis);
        // Hide axis tick labels
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        yAxis.setTickLabelsVisible(false);
        yAxis.setTickMarkVisible(false);

        XYChart.Series<Number,String> series = new XYChart.Series<>();

        for(Map.Entry<String,Double> entry : PriceProduct.entrySet()){
            XYChart.Data<Number, String> data = new XYChart.Data<>(entry.getValue(), entry.getKey());
            series.getData().add(data);
            // Add tooltip showing the value
           
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
            if (newNode != null) {
                Tooltip tooltip = new Tooltip("Product: " + entry.getKey() + "\nPrice: " + entry.getValue());
                Tooltip.install(newNode, tooltip);
            
            data.getNode().setOnMouseEntered(e -> {
                data.getNode().setScaleX(1.05);
                data.getNode().setScaleY(1.05);
            });
            data.getNode().setOnMouseExited(e -> {
                data.getNode().setScaleX(1.0);
                data.getNode().setScaleY(1.0);
            });
            }});

            
        }
        
    
       
        barchart.getData().add(series);
        return barchart;
    }
   
}

