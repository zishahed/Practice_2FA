package com.practise2fa.controllers;

import com.practise2fa.AuthenticationService;
import com.practise2fa.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Objects;

public class RegisterController {

    @FXML
    private PasswordField AgainPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private Hyperlink gotoLogin;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    void initialize() {
        registerButton.setOnAction(this::handleRegister);
        gotoLogin.setOnAction(this::handleGotoLogin);
    }

    private void handleRegister(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String rePassword = AgainPasswordField.getText();

        if (email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (!password.equals(rePassword)) {
            showError("Passwords do not match.");
            return;
        }

        try {
            AuthenticationService.register(email, password);
            showInfo("Registration successful!");
            loadScene("login.fxml");  // adjust to your actual login file
        } catch (Exception e) {
            e.printStackTrace();
            showError("Registration failed. " + e.getMessage());
        }
    }

    private void handleGotoLogin(ActionEvent event) {
        try {
            loadScene("login.fxml"); // adjust to your actual login file
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not open login screen.");
        }
    }

    private void loadScene(String fxmlFile) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlFile)));
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
