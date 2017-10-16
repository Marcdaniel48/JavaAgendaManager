/**
 * Sample Skeleton for 'SMTPSettingsForm.fxml' Controller Class
 */

package com.marcdanieldialogo.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SMTPSettingsFormController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="defaultSmtpComboBox"
    private ComboBox<?> defaultSmtpComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="idTextField"
    private TextField idTextField; // Value injected by FXMLLoader

    @FXML // fx:id="usernameTextField"
    private TextField usernameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="emailTextField"
    private TextField emailTextField; // Value injected by FXMLLoader

    @FXML // fx:id="emailPasswordTextField"
    private TextField emailPasswordTextField; // Value injected by FXMLLoader

    @FXML // fx:id="smtpUrlTextField"
    private TextField smtpUrlTextField; // Value injected by FXMLLoader

    @FXML // fx:id="smtpPortTextField"
    private TextField smtpPortTextField; // Value injected by FXMLLoader

    @FXML // fx:id="reminderIntervalTextField"
    private TextField reminderIntervalTextField; // Value injected by FXMLLoader

    @FXML
    void handleClear(ActionEvent event) {

    }

    @FXML
    void handleCreate(ActionEvent event) {

    }

    @FXML
    void handleDelete(ActionEvent event) {

    }

    @FXML
    void handleExit(ActionEvent event) {

    }

    @FXML
    void handleUpdate(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        

    }
}
