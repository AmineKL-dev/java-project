package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class DataManController implements Initializable,SceneManager.DataReceiver  {
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
    private TextField txtIdsale;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtProduct;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtTotal_Price;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Use method references for cleaner code
        colId.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getIdSale()).asObject());
        colDate.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDate()));
        colProduct.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getProductName()));
        colCategory.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getProductCategory()));
        colQuantity.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        colPrice.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        colTotal.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getTotalPrice()).asObject());

        // Debug column initialization
        System.out.println("Columns initialized:");
        salestable.getColumns().forEach(col -> {
            System.out.printf("%s (fx:id=%s) width=%.1f visible=%s%n",
                col.getText(),
                col.getId(),
                col.getWidth(),
                col.isVisible());
        });

    }

    //table row click event

    @FXML
    private void handleRowClick() {
        Sale selectedSale = salestable.getSelectionModel().getSelectedItem();
        if (selectedSale != null) {
            txtIdsale.setText(String.valueOf(selectedSale.getIdSale()));
            txtDate.setText(selectedSale.getDate());
            txtProduct.setText(selectedSale.getProductName());
            txtCategory.setText(selectedSale.getProductCategory());
            txtQuantity.setText(String.valueOf(selectedSale.getQuantity()));
            txtPrice.setText(String.valueOf(selectedSale.getPrice()));
            txtTotal_Price.setText(String.valueOf(selectedSale.getTotalPrice()));
        }
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

    //add to database
    @FXML
    public void addSaleToDB(){
        int id = Integer.parseInt(txtIdsale.getText());
        String date = txtDate.getText();
        String product = txtProduct.getText();
        String category = txtCategory.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());
        double total_price = Double.parseDouble(txtTotal_Price.getText());

        try{
            //Ajouter une vente a Base de données
           SaleRepositry.addSaleDB(new Sale(id,date, product, category, quantity, price, total_price));
            // Clear the text fields after adding the sale
            txtIdsale.clear();
            txtDate.clear();
            txtProduct.clear();
            txtCategory.clear();
            txtQuantity.clear();
            txtPrice.clear();
            txtTotal_Price.clear();
            
            List<Sale> sales = SaleRepositry.getSalesDB();
            ObservableList<Sale> observableSales = FXCollections.observableArrayList(sales);
            salestable.setItems(observableSales);
            salestable.refresh();
           
        }catch(Exception e){
            System.out.println("Error adding sale to database: " + e.getMessage());
            e.printStackTrace();
            
        }
    }

    //delete from database

    @FXML
    public void deleteSaleFromDB(){
        int id = Integer.parseInt(txtIdsale.getText());
        try{
            SaleRepositry.deleteSaleDB(id);
            // Clear the text fields after deleting the sale
            txtIdsale.clear();
            txtDate.clear();
            txtProduct.clear();
            txtCategory.clear();
            txtQuantity.clear();
            txtPrice.clear();
            txtTotal_Price.clear();
            //refresh the table view
            List<Sale> sales = SaleRepositry.getSalesDB();
            ObservableList<Sale> observableSales = FXCollections.observableArrayList(sales);
            salestable.setItems(observableSales);
            salestable.refresh();
        }catch(Exception e){
            System.out.println("Error deleting sale from database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //update from database

    @FXML
    private void updateSaleInDB() {
        int id = Integer.parseInt(txtIdsale.getText());
        String date = txtDate.getText();
        String product = txtProduct.getText();
        String category = txtCategory.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());
        double total_price = Double.parseDouble(txtTotal_Price.getText());

        try {
            SaleRepositry.updateSaleDB(new Sale(id, date, product, category, quantity, price, total_price));
            // Clear the text fields after updating the sale
            txtIdsale.clear();
            txtDate.clear();
            txtProduct.clear();
            txtCategory.clear();
            txtQuantity.clear();
            txtPrice.clear();
            txtTotal_Price.clear();
            //refresh the table view
            List<Sale> sales = SaleRepositry.getSalesDB();
            ObservableList<Sale> observableSales = FXCollections.observableArrayList(sales);
            salestable.setItems(observableSales);
            salestable.refresh();

        } catch (Exception e) {
            System.out.println("Error updating sale in database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //from csv to database

    @FXML
    public void fromCSVToDB(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);
        try{
            if (file != null) {
                System.out.println("Selected file: " + file.getAbsolutePath());
                List<Sale> sales = CSVReader.readCSV(file.getAbsolutePath());
                if (sales == null || sales.isEmpty()) {
                    System.out.println("No data found in CSV.");
                } else {
                    System.out.println("Loaded " + sales.size() + " sales.");
                    for (Sale sale : sales) {
                        SaleRepositry.addSaleDB(sale);
                    }
                    List<Sale> salesFromDB = SaleRepositry.getSalesDB();
                    ObservableList<Sale> observableSales = FXCollections.observableArrayList(salesFromDB);
                    salestable.setItems(observableSales);
                    salestable.refresh();
                }
            }
        }catch(Exception e){
            System.out.println("Error loading data from CSV to database: " + e.getMessage());
            e.printStackTrace();
        }
    }





    @FXML
    private void handleBackToDashboard() {
        SceneManager.switchToSceneWithData(
            "/com/example/views/main.fxml", "Sales Dashboard",salestable.getItems());
    }

    //handleButtonDataChart
    @FXML
    private void handleButtonDataChart(){
        SceneManager.switchToSceneWithData(
            "/com/example/views/Test.fxml", "Data Chart",salestable.getItems()
        );
    }

    @Override
    public void setData(ObservableList<Sale> data) {
        setSalesData(data);
    }
    
    @FXML
    private void logout(){
        SceneManager.switchToScene("/com/example/views/login.fxml", "Login");
    }
    
}
