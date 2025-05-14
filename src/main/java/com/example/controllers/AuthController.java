package com.example.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.models.services.AuthService;
import com.example.models.utils.SceneManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class AuthController implements Initializable{
    
    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtEmailR;

    @FXML
    private TextField txtFirstNameR;

    @FXML
    private TextField txtLastNameR;

    @FXML
    private TextField txtPasswordR;

    @FXML
    private TextField txtUsernameR;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }

    @FXML
    private void loginCont(){
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if(username.isEmpty() || password.isEmpty()){
            System.out.println("Veuillez entrer les valeurs !!!");
        }
        Boolean valid = AuthService.login(username,password);
        if(valid==true){
            System.out.println("login successfully");
            SceneManager.switchToScene("/com/example/views/main.fxml", "Sales Dashboard");
        }else{
            System.out.println("le mot passe ou l'username est incorecte!!!");
        }
    }

    @FXML
    private void registerCont(){
        String firstName = txtFirstNameR.getText();
        String lastName = txtLastNameR.getText();
        String usernameR = txtUsernameR.getText();
        String email = txtEmailR.getText();
        String password = txtPasswordR.getText();

        if(firstName.isEmpty()||lastName.isEmpty()||usernameR.isEmpty()||email.isEmpty()||password.isEmpty()){
            System.out.println("veuiller entrez les valeurs");
        }
        Boolean valid = AuthService.register(firstName, lastName, usernameR, email, password);
        if(valid==true){
            System.out.println("register successfully");
            SceneManager.switchToScene("/com/example/views/login.fxml", "login Page");
        }else{
            System.out.println("probleme dans register!!!!!");
        }
    }


    @FXML
    private void registerButton(){
        SceneManager.switchToScene("/com/example/views/register.fxml", "Register Page");
    }

    @FXML
    private void returnButton(){
        SceneManager.switchToScene("/com/example/views/login.fxml", "login Page");
    }

    

}
