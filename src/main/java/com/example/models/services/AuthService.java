package com.example.models.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.config.DBConfig;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AuthService {
    //Login method to authenticate user
    public static boolean login(String username, String password){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
        try{
            Connection connection = DBConfig.getConnection();
            String query = "Select *from login where username=? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            if(preparedStatement.executeQuery().next()){
                alert.setTitle("Connexion");
                alert.setHeaderText("Vous etes connecté successfully!");
                alert.setContentText("Connexion réussite");
                alert.showAndWait();
                return true;
            }else{
                alert.setAlertType(AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("Connexion Failed veuillez verifier vos information ou cree un nouveau compte");
                alert.showAndWait();
                return false;

            }
        }catch(SQLException e){
            alert.setAlertType(AlertType.ERROR);
            alert.setHeaderText("ERROR dans le serveur");
            alert.setContentText("Connexion Failed veuillez verifier vos information ou cree un nouveau compte");
            alert.showAndWait();
            System.out.println("Error logging in :"+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //register method

    public static boolean register(String firstName,String lastName,String usernameR,String email,String password){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(firstName.isEmpty() || lastName.isEmpty() || usernameR.isEmpty() || email.isEmpty() || password.isEmpty()){
            alert.setAlertType(AlertType.ERROR);
            alert.setHeaderText("ERROR dans l'insertion");
            alert.setContentText("veuillez inserez les données");
            alert.showAndWait();
            System.out.println("veuillez inserez les données");
            return false;
        }
        try{
            Connection connection = DBConfig.getConnection();
            String query = "insert into login (first_name,last_name,username,email,password) values ( ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, usernameR);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);

            preparedStatement.executeUpdate();
            alert.setTitle("Réussite");
            alert.setHeaderText("Votre compte a été engistré avec succès");
            alert.setContentText("Connexion réussite");
            alert.showAndWait();
            return true;
        }catch(SQLException e){
            alert.setAlertType(AlertType.ERROR);
            alert.setHeaderText("ERROR dans le serveur");
            alert.setContentText("Connexion Failed veuillez verifier vos information ou cree un nouveau compte");
            alert.showAndWait();
            System.out.println("errorr lors de l'insertion"+e.getMessage());
            return false;
        }
    }
}
