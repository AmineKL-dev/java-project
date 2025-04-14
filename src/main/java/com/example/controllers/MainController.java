package com.example.controllers;

import com.example.models.Sale;
import com.example.models.utils.CSVReader;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class MainController {
    @FXML private TableView<Sale> salestable;
    @FXML private TableColumn<Sale, Integer> colId;
    @FXML private TableColumn<Sale, String> colDate;
    @FXML private TableColumn<Sale, String> colProduct;
    @FXML private TableColumn<Sale, String> colCategory;
    @FXML private TableColumn<Sale, Integer> colQuantity;
    @FXML private TableColumn<Sale, Double> colPrice;
    @FXML private TableColumn<Sale, Double> colTotal;

    @FXML 
    private void initialize() {
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdSale()).asObject());
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        colProduct.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
        colCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductCategory()));
        colQuantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        colPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        colTotal.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalPrice()).asObject());
    }

    @FXML
    private void handleLoadData() {
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
}
