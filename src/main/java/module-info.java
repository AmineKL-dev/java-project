module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.sql;

    // Ensure the correct module name or dependency is added
        requires de.jensd.fx.glyphs.fontawesome;
        requires javafx.graphics;
        requires javafx.base;
        requires itext;
        requires java.desktop;
      
    
    opens com.example to javafx.fxml;
    opens com.example.controllers to javafx.fxml;
    exports com.example;
}