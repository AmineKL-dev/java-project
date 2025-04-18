package com.example.controllers;

import com.example.models.Sale;
import com.example.models.repositories.SaleRepositry;
import com.example.models.services.SaleService;
import com.example.models.utils.CSVReader;
import com.example.models.utils.SceneManager;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController implements SceneManager.DataReceiver {
    @FXML private TableView<Sale> salestable;
    @FXML private TableColumn<Sale, Integer> colId;
    @FXML private TableColumn<Sale, String> colDate;
    @FXML private TableColumn<Sale, String> colProduct;
    @FXML private TableColumn<Sale, String> colCategory;
    @FXML private TableColumn<Sale, Integer> colQuantity;
    @FXML private TableColumn<Sale, Double> colPrice;
    @FXML private TableColumn<Sale, Double> colTotal;
    @FXML private TableColumn<Sale, Double> totalsales;
    private SaleService saleService = new SaleService();
    @FXML
    private Label totalSalesLabel;  // Matches fx:id in FXML
    @FXML
    private Label quantitySoldlabel;  // Matches fx:id in FXML
    @FXML
    private Label categorySoldLabel;  // Matches fx:id in FXML
    @FXML 
    private void initialize() {
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdSale()).asObject());
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        colProduct.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
        colCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductCategory()));
        colQuantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        colPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        colTotal.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalPrice()).asObject());

        salestable.getItems().addListener((ListChangeListener<Sale>) change -> {
        saleService.setSales(new ArrayList<>(salestable.getItems()));
        updateTotalSales();
    });
    }
    

    @FXML
    private void handleLoadCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (salestable == null) {  // Check before using it
                System.out.println("salesTable is null!");
                return;
            }

            try {
                System.out.println("Selected file: " + file.getAbsolutePath());
                List<Sale> sales = CSVReader.readCSV(file.getAbsolutePath());
                if (sales == null || sales.isEmpty()) {
                    System.out.println("No data found in CSV.");
                } else {
                    System.out.println("Loaded " + sales.size() + " sales.");
                    // load total sales
                    loadSalesData(sales);
                    updateTotalSales();

                    ObservableList<Sale> observableSales = FXCollections.observableArrayList(sales);
                    salestable.setItems(observableSales);
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading data from CSV file.", ButtonType.OK);
                alert.showAndWait();
            }
        }
      
    }

    //load data from database
    @FXML
    private void handleLoadFromDB() {
        try {
            List<Sale> sales = new SaleRepositry().getSalesDB();
            ObservableList<Sale> data = FXCollections.observableArrayList(sales);
            loadSalesData(sales);
            updateTotalSales();
            salestable.setItems(data);
        } catch (Exception e) {
            System.out.println("Error loading data from database on MainController: " + e.getMessage());
        }
    }

    public void updateTotalSales() {
        double total = saleService.getTotalSales();
        double totalQuantity = saleService.getTotalQuantitySold();
        int numberOfCategory = saleService.getNumberOfCategory();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        totalSalesLabel.setText("$" + df.format(total));
        quantitySoldlabel.setText(String.valueOf(totalQuantity));
        categorySoldLabel.setText(String.valueOf(numberOfCategory));
    }

    // we Call this when loading data
    public void loadSalesData(List<Sale> sales) {
        saleService.setSales(sales);
        salestable.getItems().setAll(sales);
        updateTotalSales();
    }

    //handleButtonDataManipulation
    @FXML
    private void handleDataManipulationButton() {
        SceneManager.switchToSceneWithData(
            "/com/example/views/dataManipulation.fxml", 
            "Data Manipulation",
            salestable.getItems()
        );
    }
    public void setSalesData(ObservableList<Sale> data) {
    Platform.runLater(() -> {
        System.out.println("Setting data with " + data.size() + " items");
        
        // Verify data content
        if (!data.isEmpty()) {
            Sale firstItem = data.get(0);
            System.out.printf("First item - ID: %d, Product: %s, Date: %s%n",
                firstItem.getIdSale(),
                firstItem.getProductName(),
                firstItem.getDate());
        }

        salestable.setItems(data);
        salestable.refresh();
        
        // Verify table state after update
        System.out.println("Table now has " + salestable.getItems().size() + " items");
        System.out.println("Table columns: " + salestable.getColumns().size());
        System.out.println("Table visible: " + salestable.isVisible());
        });
    }


    @Override
    public void setData(ObservableList<Sale> data) {
        setSalesData(data);
        loadSalesData(data);
    }
    

}
