package com.marcdanieldialogo.jam_jdbc;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Marc-Daniel
 */
public class MainApp extends Application{
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private Stage primaryStage;
    private Parent rootPane;
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initRootLayout();
        primaryStage.setTitle("Month View");
        primaryStage.show();
    }
    
    public void initRootLayout() {
        try 
        {
            // Instantiate a FXMLLoader object
            FXMLLoader loader = new FXMLLoader();

            loader.setResources(ResourceBundle.getBundle("MonthViewText"));
            // Connect the FXMLLoader to the fxml file that is stored in the jar
            loader.setLocation(MainApp.class.getResource("/fxml/MonthView.fxml"));

            // The load command returns a reference to the root pane of the fxml file
            rootPane = (BorderPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootPane);
            // Put the Scene on the Stage
            primaryStage.setScene(scene);
        } 
        catch (IOException ex) 
        {
            log.error("Error", ex);
            Platform.exit();
        }
    }
}
