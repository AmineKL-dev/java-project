package com.example.models.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.config.DBConfig;

public class AuthService {
    //Login method to authenticate user
    public static boolean login(String username, String password){
    
        try{
            Connection connection = DBConfig.getConnection();
            String query = "Select *from login where username=? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            if(preparedStatement.executeQuery().next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println("Error logging in :"+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //register method

    public static boolean register(String firstName,String lastName,String usernameR,String email,String password){
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
            return true;
        }catch(SQLException e){
            System.out.println("errorr lors de l'insertion"+e.getMessage());
            return false;
        }
    }
}
