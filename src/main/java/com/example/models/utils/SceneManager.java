package com.example.models.utils;

import java.io.IOException;

import com.example.controllers.DataManController;
import com.example.models.Sale;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage primaryStage;
    private static Scene currentScene;

    public static void initialize(Stage stage) {
        primaryStage = stage;
    }

    public static void switchToScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            
            currentScene = new Scene(root);
            primaryStage.setScene(currentScene);
            primaryStage.setTitle(title);
            primaryStage.setResizable(false);
            
        } catch (IOException e) {
            System.err.println("Failed to load scene: " + fxmlPath);
            e.printStackTrace();
            System.out.println("Scene Error Failed to load view"+ e.getMessage());
        }
    }

    public static void switchToSceneWithData(String fxmlPath, String title, ObservableList<Sale> data) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            
            // Pass data to the controller
            Object controller = loader.getController();
            if (controller instanceof DataReceiver) {
                ((DataReceiver) controller).setData(data);
                
                // DEBUG: Verify data received
                System.out.println("Passing " + data.size() + " items to DataManipulationController");
            }
            
            currentScene = new Scene(root);
            primaryStage.setScene(currentScene);
            primaryStage.setTitle(title);
            primaryStage.setResizable(false);
            
        } catch (IOException e) {
            System.err.println("Failed to load scene with data: " + fxmlPath);
            e.printStackTrace();
            System.out.println("Data Error Failed to transfer data"+ e.getMessage());
        }
    }
    public interface DataReceiver {
        void setData(ObservableList<Sale> data);
    }
}