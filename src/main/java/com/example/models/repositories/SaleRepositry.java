package com.example.models.repositories;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.models.Sale;

import javafx.scene.control.Alert;

import com.example.config.DBConfig;


public class SaleRepositry {

    // Method to get a sale from database
    public static List<Sale> getSalesDB(){
        List<Sale> sales = new ArrayList<>();
        try{
            String query = "Select *from sale";
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            var resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Sale sale = new Sale();
                sale.setIdSale(resultSet.getInt("id_sale"));
                sale.setDate(resultSet.getString("date"));
                sale.setProductName(resultSet.getString("product"));
                sale.setProductCategory(resultSet.getString("category"));
                sale.setQuantity(resultSet.getInt("quantity"));
                sale.setPrice(resultSet.getDouble("price"));
                sale.setTotalPrice(resultSet.getDouble("total_price"));

                sales.add(sale);
            }
            connection.close();

        }catch(SQLException e){
            System.out.println("Error getting sales from database: " + e.getMessage());
            e.printStackTrace();
        }
        return sales;
    
    }

    // Method to add a sale to the database

    public static void addSaleDB(Sale sale) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        int id = sale.getIdSale();
            try {
                String query = "INSERT INTO sale (id_sale,date, product, category, quantity, price, total_price) VALUES (?,?, ?, ?, ?, ?, ?)";
                Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, sale.getIdSale());
                preparedStatement.setString(2, sale.getDate());
                preparedStatement.setString(3, sale.getProductName());
                preparedStatement.setString(4, sale.getProductCategory());
                preparedStatement.setInt(5, sale.getQuantity());
                preparedStatement.setDouble(6, sale.getPrice());
                preparedStatement.setDouble(7, sale.getTotalPrice());

                preparedStatement.executeUpdate();
                //alert
            
                alert.setTitle("Success");
                alert.setHeaderText("Sale added successfully!");
                alert.setContentText("Sale with ID " + id + " has been added to the database.");
                alert.showAndWait();

                connection.close();

            } catch (SQLException e) {
                System.out.println("Error adding sale to database: " + e.getMessage());
                e.printStackTrace();
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error adding sale to database!");
                alert.setContentText("Error adding sale with ID " + id + " to the database.\n make sure the ID is unique and all fields are filled correctly.");
                alert.showAndWait();
            }
    }

    // Method to delete a sale from the database

    public static void deleteSaleDB(int id) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            String query = "DELETE FROM sale WHERE id_sale = ?";
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            alert.setTitle("Success");
            alert.setHeaderText("Sale deleted successfully!");
            alert.setContentText("Sale with ID " + id + " has been deleted from the database.");
            alert.showAndWait();

            connection.close();

        } catch (SQLException e) {
            System.out.println("Error deleting sale from database: " + e.getMessage());
            e.printStackTrace();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting sale from database!");
            alert.setContentText("Error deleting sale with ID " + id + " from the database.");
            alert.showAndWait();
        }
    }

    // Method to update a sale in the database

    public static void updateSaleDB(Sale sale) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            String query = "UPDATE sale SET date = ?, product = ?, category = ?, quantity = ?, price = ?, total_price = ? WHERE id_sale = ?";
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, sale.getDate());
            preparedStatement.setString(2, sale.getProductName());
            preparedStatement.setString(3, sale.getProductCategory());
            preparedStatement.setInt(4, sale.getQuantity());
            preparedStatement.setDouble(5, sale.getPrice());
            preparedStatement.setDouble(6, sale.getTotalPrice());
            preparedStatement.setInt(7, sale.getIdSale());

            preparedStatement.executeUpdate();
            alert.setTitle("Success");
            alert.setHeaderText("Sale updated successfully!");
            alert.setContentText("Sale with ID " + sale.getIdSale() + " has been updated in the database.");
            alert.showAndWait();

            connection.close();

        } catch (SQLException e) {
            System.out.println("Error updating sale in database: " + e.getMessage());
            e.printStackTrace();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating sale in database!");
            alert.setContentText("Error updating sale with ID " + sale.getIdSale() + " in the database.");
            alert.showAndWait();
        }
    }



}
