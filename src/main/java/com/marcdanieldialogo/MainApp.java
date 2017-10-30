package com.marcdanieldialogo;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.marcdanieldialogo.tasks.EmailTask;

/**
 *
 * @author Marc-Daniel
 */
public class MainApp extends Application{
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private Stage primaryStage;
    private Parent rootPane;
    
    private final EmailTask emailTask;
    private final ScheduledExecutorService executor;
    
    /**
     * Default constructor instantiates the task to be carried out and the
     * executor that manages the interval
     */
    public MainApp()
    {
        emailTask = new EmailTask();
        
        // Multiple tasks can be scheduled, here we are scheduling just one
        executor = Executors.newScheduledThreadPool(1);
    }
    
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
        
        // The interval is between the start time of the task.
        // The task must complete its task before the next interval completes.
        // Runnable task, delay to start, interval between tasks, time units
        executor.scheduleWithFixedDelay(emailTask, 1, 60, TimeUnit.SECONDS);
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
    
    // When the JAM window closes, then the schedule executor will stop.
    @Override
    public void stop()
    {
        executor.shutdown();
        log.debug("Executor has shut down");
    }
}