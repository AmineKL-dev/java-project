package com.example;

import java.io.IOException;

import com.example.models.utils.SceneManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize SceneManager with the primary stage
        SceneManager.initialize(primaryStage);
        
        // Load and show the main scene
        SceneManager.switchToScene("/com/example/views/login.fxml", "Login ");
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}