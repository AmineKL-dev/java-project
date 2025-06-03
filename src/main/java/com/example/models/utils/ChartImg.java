package com.example.models.utils;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import javafx.util.Duration;
import java.util.concurrent.CountDownLatch;

public class ChartImg {
    public static BufferedImage captureNodeAsImage(Node chartNode) {
        CountDownLatch latch = new CountDownLatch(1);
        final BufferedImage[] imageRef = new BufferedImage[1];

        Platform.runLater(() -> {
            try {
                // Forcer une taille minimale pour un rendu complet
                if (chartNode instanceof Region region) {
                    region.setPrefSize(800, 600);
                }

                StackPane root = new StackPane(chartNode);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setOpacity(0.01); // Invisible mais affiché
                stage.show();

                // Attendre que JavaFX rende les axes
                PauseTransition delay = new PauseTransition(Duration.millis(500));
                delay.setOnFinished(e -> {
                    try {
                        WritableImage fxImage = chartNode.snapshot(new SnapshotParameters(), null);
                        imageRef[0] = SwingFXUtils.fromFXImage(fxImage, null);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        stage.close();
                        latch.countDown();
                    }
                });
                delay.play();

            } catch (Exception e) {
                e.printStackTrace();
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return imageRef[0];
    }
}

