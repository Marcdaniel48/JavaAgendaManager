package com.marcdanieldialogo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import java.nio.file.Path;
import static java.nio.file.Paths.get;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ConfigurationFormController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="statusLabel"
    private Label statusLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="urlTextField"
    private TextField urlTextField; // Value injected by FXMLLoader

    @FXML // fx:id="usernameTextField"
    private TextField usernameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordTextField"
    private TextField passwordTextField; // Value injected by FXMLLoader

    @FXML
    void onExit(ActionEvent event) 
    {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onSave(ActionEvent event) 
    {
        /*Properties dbmsSettings = new Properties();
        Path propertiesPath = get("src/main/resources", "DBMSSettings.properties");

        if(Files.exists(propertiesPath))
        {
            try(InputStream propFileStream = newInputStream(propertiesPath);)
            {
                dbmsSettings.load(propFileStream);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ConfigurationFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        dbmsSettings.setProperty("URL", urlTextField.getText());
        dbmsSettings.setProperty("USER", usernameTextField.getText());
        dbmsSettings.setProperty("PASSWORD", passwordTextField.getText());
        statusLabel.setText("User:"+usernameTextField.getText()+",Pass:"+passwordTextField.getText());*/
        
        Properties prop = new Properties();
        Path txtFile = get("src/main/resources", "DBMSSettings.properties");
        
        prop.setProperty("URL", urlTextField.getText());
        prop.setProperty("USER", usernameTextField.getText());
        prop.setProperty("PASSWORD", passwordTextField.getText());
        statusLabel.setText("User="+usernameTextField.getText()+";Pass="+passwordTextField.getText());
        
        // Add properties to the prop object
        try (OutputStream propFileStream = newOutputStream(txtFile, StandardOpenOption.CREATE);)
        {
            prop.store(propFileStream, "SMTP Properties");
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ConfigurationFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @FXML
    void onDefault(ActionEvent event) 
    {
        urlTextField.setText("jdbc:mysql://localhost:3306/jamdb?autoReconnect=true&useSSL=false");
        usernameTextField.setText("MarcDaniel");
        passwordTextField.setText("dbsurfing");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
        urlTextField.setEditable(false);
        usernameTextField.setEditable(false);
        passwordTextField.setEditable(false);
        
        Properties dbmsSettings = new Properties();
        Path propertiesPath = get("src/main/resources", "DBMSSettings.properties");

        if(Files.exists(propertiesPath))
        {
            try(InputStream propFileStream = newInputStream(propertiesPath);)
            {
                dbmsSettings.load(propFileStream);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ConfigurationFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        String url = dbmsSettings.getProperty("URL");
        String user = dbmsSettings.getProperty("USER");
        String password = dbmsSettings.getProperty("PASSWORD");
        urlTextField.setText(url);
        usernameTextField.setText(user);
        passwordTextField.setText(password);
        statusLabel.setText("User:"+user+",Pass:"+password);
    }
}