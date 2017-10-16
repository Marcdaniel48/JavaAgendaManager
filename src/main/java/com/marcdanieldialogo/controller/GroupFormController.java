/**
 * Sample Skeleton for 'GroupForm.fxml' Controller Class
 */

package com.marcdanieldialogo.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GroupFormController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="groupNameTextField"
    private TextField groupNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="colourTextField"
    private TextField colourTextField; // Value injected by FXMLLoader

    @FXML
    void handleCreate(ActionEvent event) {

    }

    @FXML
    void handleExit(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}
