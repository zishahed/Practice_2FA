package com.practise2fa.controllers;

import com.practise2fa.AuthenticationService;
import com.practise2fa.EmailService;
import com.practise2fa.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passField;

    @FXML
    private Hyperlink registerLink;

    @FXML
    void handleLogin(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showError("Validation Error", "Email and password cannot be empty.");
            return;
        }

        try {
            if (AuthenticationService.isUserExit(email)) {
                String uid = AuthenticationService.authenticate(email, password);

                String otp = AuthenticationService.generateOtp();
                AuthenticationService.storeOtp(uid, otp);
                EmailService.sendOtp(email, otp);

                TextInputDialog otpDialog = new TextInputDialog();
                otpDialog.setTitle("OTP Verification");
                otpDialog.setHeaderText("Enter the OTP sent to your email:");
                otpDialog.setContentText("OTP:");

                otpDialog.showAndWait().ifPresent(userEnteredOtp -> {
                    try {
                        if (AuthenticationService.verifyOtp(uid, userEnteredOtp)) {
                            loadScene("MainFrame.fxml");
                        } else {
                            showError("Invalid OTP", "The OTP you entered is incorrect.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showError("Verification Failed", "An error occurred while verifying OTP.");
                    }
                });

            } else {
                showError("Login Error", "User does not exist.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Login Error", "Something went wrong during login.");
        }
    }

    @FXML
    void handleRegistration(ActionEvent event) {
        try {
            loadScene("register.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Navigation Error", "Could not open registration screen.");
        }
    }

    private void loadScene(String fxmlFile) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlFile)));
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
