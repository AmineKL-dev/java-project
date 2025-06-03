package com.example.controllers;

import com.example.models.Sale;
import com.example.models.utils.ChartGenerator;
import com.example.models.utils.SceneManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


import java.net.URL;
import java.util.ResourceBundle;

public class ChartController implements Initializable, SceneManager.DataReceiver {

    @FXML
    private AnchorPane Container1;

    @FXML
    private AnchorPane Container2;

    @FXML
    private AnchorPane Container3;

    @FXML
    private AnchorPane Container4;

    @FXML
    void handleBackToDashboard(ActionEvent event) {

    }


    private ObservableList<Sale> salesData;

    @Override
    public void setData(ObservableList<Sale> data) {
        this.salesData = data;
        System.out.println("from data transfered :"+salesData.get(0));
        loadCharts();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    private void loadCharts() {
        // Clear previous content
    Container1.getChildren().clear();
    Container2.getChildren().clear();
    Container3.getChildren().clear();
    Container4.getChildren().clear();

    // Generate charts
    BarChart<String, Number> salesChart = ChartGenerator.createTotalSalesByCategoryChart(salesData);
    BarChart<String, Number> MostSoldUnit = ChartGenerator.createUnitSoldByCategoryChart(salesData);
    LineChart<String,Number> salesTrend = ChartGenerator.createSalesTrendByTimeLine(salesData);
    BarChart<Number,String> mostSoldProduct = ChartGenerator.createMostSoldProductChart(salesData);

    // Set to fill the container
    AnchorPane.setTopAnchor(salesChart, 0.0);
    AnchorPane.setBottomAnchor(salesChart, 0.0);
    AnchorPane.setLeftAnchor(salesChart, 0.0);
    AnchorPane.setRightAnchor(salesChart, 0.0);

    AnchorPane.setTopAnchor (MostSoldUnit, 0.0);
    AnchorPane.setBottomAnchor (MostSoldUnit, 0.0);
    AnchorPane.setLeftAnchor(MostSoldUnit, 0.0);
    AnchorPane.setRightAnchor (MostSoldUnit, 0.0);

    AnchorPane.setTopAnchor(salesTrend, 0.0);
    AnchorPane.setBottomAnchor(salesTrend, 0.0);
    AnchorPane.setLeftAnchor(salesTrend, 0.0);
    AnchorPane.setRightAnchor(salesTrend, 0.0);

    AnchorPane.setTopAnchor(mostSoldProduct, 0.0);
    AnchorPane.setBottomAnchor(mostSoldProduct, 0.0);
    AnchorPane.setLeftAnchor(mostSoldProduct, 0.0);
    AnchorPane.setRightAnchor(mostSoldProduct, 0.0);
    // Add each chart to its respective container
    Container1.getChildren().add(salesChart);
    Container2.getChildren().add(MostSoldUnit);
    Container3.getChildren().add(salesTrend);
    Container4.getChildren().add(mostSoldProduct);
    }

 
    //handleButtonDataManipulation
    @FXML
    private void handleDataManipulationButton() {
        SceneManager.switchToSceneWithData(
            "/com/example/views/dataManipulation.fxml", 
            "Data Manipulation",
            salesData
        );
    }

    //handleButtonDataManipulation
    @FXML
    private void handleDashboard() {
        SceneManager.switchToSceneWithData(
            "/com/example/views/main.fxml", 
            "Dashboard",
            salesData
        );
    }

    @FXML
    private void logout(){
        SceneManager.switchToScene("/com/example/views/login.fxml", "Login");
    }


}
