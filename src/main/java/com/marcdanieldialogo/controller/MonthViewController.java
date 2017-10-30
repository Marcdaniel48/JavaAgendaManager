package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.MainApp;
import com.marcdanieldialogo.entities.Appointment;
import com.marcdanieldialogo.entities.GroupRecord;
import com.marcdanieldialogo.entities.SMTPSettings;
import com.marcdanieldialogo.entities.MonthViewBean;
import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import com.marcdanieldialogo.persistence.GroupRecordDAO;
import com.marcdanieldialogo.persistence.GroupRecordDAOImpl;
import com.marcdanieldialogo.persistence.SMTPSettingsDAO;
import com.marcdanieldialogo.persistence.SMTPSettingsDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonthViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="monthTable"
    private TableView<MonthViewBean> monthTable; // Value injected by FXMLLoader

    @FXML // fx:id="sundayCells"
    private TableColumn<MonthViewBean, String> sundayCells; // Value injected by FXMLLoader

    @FXML // fx:id="mondayCells"
    private TableColumn<MonthViewBean, String> mondayCells; // Value injected by FXMLLoader

    @FXML // fx:id="tuesdayCells"
    private TableColumn<MonthViewBean, String> tuesdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="wednesdayCells"
    private TableColumn<MonthViewBean, String> wednesdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="thursdayCells"
    private TableColumn<MonthViewBean, String> thursdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="fridayCells"
    private TableColumn<MonthViewBean, String> fridayCells; // Value injected by FXMLLoader

    @FXML // fx:id="saturdayCells"
    private TableColumn<MonthViewBean, String> saturdayCells; // Value injected by FXMLLoader
    
    @FXML // fx:id="currentMonthLabel"
    private Label currentMonthLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="exitButton"
    private Button exitButton; // Value injected by FXMLLoader
    
    private final Logger log = LoggerFactory.getLogger(getClass().getName());
    
    // The individual cells of the month table
    private ObservableList<TablePosition> theCells;
    
    // Keeps track of the current month and year
    private int currentMonth;
    private int currentYear;
    
    private SMTPSettingsDAO smtpDAO;
    private SMTPSettings smtp;
    private GroupRecordDAO groupRecordDAO;
    private GroupRecord groupRecord;
    private AppointmentDAO appointmentDAO;
    private Appointment appointment;
    
    DayViewController dayController;
    WeekViewController weekController;
    
    @FXML//delete
    private StackPane stackPane;//delete
    /**
     * Constructor
     */
    public MonthViewController() {
        super();
        smtpDAO = new SMTPSettingsDAOImpl();
        smtp = new SMTPSettings();
        groupRecordDAO = new GroupRecordDAOImpl();
        groupRecord = new GroupRecord();
        appointmentDAO = new AppointmentDAOImpl();
        appointment = new Appointment();
        
        currentMonth = LocalDate.now().getMonthValue();
        currentYear = LocalDate.now().getYear();
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        sundayCells.setCellValueFactory(cellData -> cellData.getValue().sundayColProperty());
        mondayCells.setCellValueFactory(cellData -> cellData.getValue().mondayColProperty());
        tuesdayCells.setCellValueFactory(cellData -> cellData.getValue().tuesdayColProperty());
        wednesdayCells.setCellValueFactory(cellData -> cellData.getValue().wednesdayColProperty());
        thursdayCells.setCellValueFactory(cellData -> cellData.getValue().thursdayColProperty());
        fridayCells.setCellValueFactory(cellData -> cellData.getValue().fridayColProperty());
        saturdayCells.setCellValueFactory(cellData -> cellData.getValue().saturdayColProperty());
        
        adjustColumnWidths();
        
        monthTable.getSelectionModel().cellSelectionEnabledProperty().set(true);
        theCells = monthTable.getSelectionModel().getSelectedCells();
        theCells.addListener(this::showSingleCellDetails);
        
        displayTable();
        
        addOtherViewsToStackPane();
    }
    
    /**
     * Will fill the table with days of the current month, making the table look more like a calendar
     */
    public void displayTable()
    {
        ObservableList<MonthViewBean> weekList = FXCollections.observableArrayList();
        
        LocalDate currentDate = LocalDate.of(currentYear, currentMonth, 1);
        currentMonthLabel.setText(currentDate.getMonth().toString() + " " + currentDate.getYear());
        
        for(int i = 0; i < 6; i++)
        {
            MonthViewBean weekBean = new MonthViewBean();
            weekBean.setDate(currentDate);
            weekBean.setWeek();
            
            weekList.add(weekBean);
            
            currentDate = currentDate.plusWeeks(1);
            if(currentDate.getMonthValue() != currentMonth)
            {
                while(currentDate.getDayOfWeek() != DayOfWeek.SUNDAY)
                {
                    currentDate = currentDate.minusDays(1);
                }
                
                if(currentDate.getMonthValue() != currentMonth)
                    break;
            }
        }
        
        monthTable.setItems(weekList);
        
        for(int i = 0; i < monthTable.getColumns().size(); i++)
        {
            renderCell(monthTable.getColumns().get(i));
        }
    }    
    
    /**
    *    Turns the cells into squares, to make the table look more like a calendar
    */
    private void adjustColumnWidths() 
    {
        double width = monthTable.getPrefWidth();
        mondayCells.setPrefWidth(width / 7.0);
        tuesdayCells.setPrefWidth(width / 7.0);
        wednesdayCells.setPrefWidth(width / 7.0);
        thursdayCells.setPrefWidth(width / 7.0);
        fridayCells.setPrefWidth(width / 7.0);
        saturdayCells.setPrefWidth(width / 7.0);
        sundayCells.setPrefWidth(width / 7.0);
        monthTable.setFixedCellSize(width / 7.0);
    }
    
    /**
     * This method is what allows the user to pass in the number of a square in the calendar to a daily view. With the passed in number of the day,
     * the month view can open the correct day view.
     * @param change 
     */
    private void showSingleCellDetails(ListChangeListener.Change<? extends TablePosition> change)
    {
        if(theCells.size() > 0)
        {
            TablePosition selectedCell = theCells.get(0);
            TableColumn column = selectedCell.getTableColumn();
            int rowIndex = selectedCell.getRow();
            String data = (String)column.getCellObservableValue(rowIndex).getValue();
            data = data.split("\\n")[0];
            
            //renderCell(selectedCell.getTableColumn()); //DELETE
            if(!((String)data).equals(""))
            {
                LocalDate selectedDay = LocalDate.of(currentYear, currentMonth, Integer.parseInt(data));
                
                dayController.setDay(selectedDay);
                weekController.setDay(selectedDay);
                openDayView(null);
            }
        }
    }
    
    /**
     * Will fill the table with the days of the next month
     */
    @FXML
    private void handleNext()
    {
        if(currentMonth == 12)
        {
            currentMonth = 1;
            currentYear += 1;
            displayTable();
        }
        else
        {
            currentMonth += 1;
            displayTable();
        }
    }
    
    /**
     * Will fill the table with the days of the previous month
     */
    @FXML
    private void handlePrevious()
    {
        if(currentMonth == 1)
        {
            currentMonth = 12;
            currentYear -= 1;
            displayTable();
        }
        else
        {
            currentMonth -= 1;
            displayTable();
        }
    }
    
    /**
     * Opens the appointment form
     */
    public void handleNewAppointment()
    {
        try
        {   
            Stage appointmentFormStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("AppointmentFormText"));
            loader.setLocation(MainApp.class.getResource("/fxml/AppointmentForm.fxml"));
            
            Parent appointmentFormPane = (BorderPane) loader.load();

            Scene appointmentFormScene = new Scene(appointmentFormPane);
            
            appointmentFormStage.setScene(appointmentFormScene);
            appointmentFormStage.initModality(Modality.APPLICATION_MODAL);
            appointmentFormStage.setTitle("Appointment Form");
            
            AppointmentFormController controller = loader.getController();
            controller.setAppointment(appointmentDAO, appointment);
            appointmentFormStage.show();
        }
        catch(IOException ioe)
        {
            log.error("Error trying to open appointment form", ioe);
        }
    }
    
    /**
     * Opens up the GroupRecord Form
     * @param event 
     */
    @FXML
    void handleNewGroupRecord(ActionEvent event) 
    {
        try
        {   
            Stage groupRecordStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("GroupFormText"));
            loader.setLocation(MainApp.class.getResource("/fxml/GroupForm.fxml"));
            
            Parent groupRecordFormPane = (BorderPane) loader.load();

            Scene groupRecordFormScene = new Scene(groupRecordFormPane);
            
            groupRecordStage.setScene(groupRecordFormScene);
            groupRecordStage.initModality(Modality.APPLICATION_MODAL);
            groupRecordStage.setTitle("SMTP Settings Form");
            
            GroupFormController controller = loader.getController();
            controller.setGroupRecord(groupRecordDAO, groupRecord);
            groupRecordStage.show();
        }
        catch(IOException ioe)
        {
            log.error("IOException in handleSettingsForm method", ioe.getMessage());
        }
    }
    
    /**
     * Opens up the SMTP Settings Form
     * @param event 
     */
    @FXML
    void handleSettingsForm(ActionEvent event) 
    {
        try
        {   
            Stage smtpFormStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("SMTPSettingsFormText"));
            loader.setLocation(MainApp.class.getResource("/fxml/SMTPSettingsForm.fxml"));
            
            Parent smtpFormPane = (BorderPane) loader.load();

            Scene smtpFormScene = new Scene(smtpFormPane);
            
            smtpFormStage.setScene(smtpFormScene);
            smtpFormStage.initModality(Modality.APPLICATION_MODAL);
            smtpFormStage.setTitle("SMTP Settings Form");
            
            SMTPSettingsFormController controller = loader.getController();
            controller.setSMTPSettings(smtpDAO, smtp);
            smtpFormStage.show();
        }
        catch(IOException ioe)
        {
            log.error("IOException in handleSettingsForm method", ioe.getMessage());
        }
    }
    
    @FXML
    void openConfiguration(ActionEvent event) 
    {
        try
        {   
            Stage configFormStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("ConfigurationSettingsForm"));
            loader.setLocation(MainApp.class.getResource("/fxml/ConfigurationForm.fxml"));
            
            Parent configFormPane = (BorderPane) loader.load();

            Scene configFormScene = new Scene(configFormPane);
            
            configFormStage.setScene(configFormScene);
            configFormStage.initModality(Modality.APPLICATION_MODAL);
            configFormStage.setTitle("Configuration Settings");
            
            configFormStage.show();
        }
        catch(IOException ioe)
        {
            log.error("IOException in openConfiguration method", ioe.getMessage());
        }  
    }
    
    /**
     * Closes the window
     */
    @FXML
    private void handleExit()
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void openDayView(ActionEvent event) {
       stackPane.getChildren().get(0).setVisible(false);
       stackPane.getChildren().get(1).setVisible(true);
       stackPane.getChildren().get(2).setVisible(false);
    }
    
    @FXML
    void openWeekView(ActionEvent event) {
       stackPane.getChildren().get(0).setVisible(false);
       stackPane.getChildren().get(1).setVisible(false);
       stackPane.getChildren().get(2).setVisible(true);
    }
    
    @FXML
    void openMonthView(ActionEvent event) {
       stackPane.getChildren().get(0).setVisible(true);
       stackPane.getChildren().get(1).setVisible(false);
       stackPane.getChildren().get(2).setVisible(false);
    }
    
    private void addOtherViewsToStackPane()
    {
        FXMLLoader dayLoader = new FXMLLoader();
        dayLoader.setResources(ResourceBundle.getBundle("DayViewText"));
        dayLoader.setLocation(MainApp.class.getResource("/fxml/DayView.fxml"));
        try 
        {
            Pane pane = dayLoader.load();
            pane.setPrefWidth(1100);
            pane.setPrefHeight(610);
            dayController = dayLoader.getController();
            dayController.setDay(LocalDate.now());
            stackPane.getChildren().add(pane);
        } 
        catch (IOException ex) 
        {
            java.util.logging.Logger.getLogger(MonthViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FXMLLoader weekLoader = new FXMLLoader();
        weekLoader.setResources(ResourceBundle.getBundle("WeekViewText"));
        weekLoader.setLocation(MainApp.class.getResource("/fxml/WeekView.fxml"));
        try 
        {
            Pane pane = weekLoader.load();
            pane.setPrefWidth(1100);
            pane.setPrefHeight(610);
            weekController = weekLoader.getController();
            weekController.setDay(LocalDate.now());
            stackPane.getChildren().add(pane);
        } 
        catch (IOException ex) 
        {
            java.util.logging.Logger.getLogger(MonthViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        openMonthView(null);
    }
    
    //DELETE METHOD
    /**
     * This is a method that is responsible for the contents of a cell. With it
     * we can colour the background of a cell.
     *
     * @param tc
     */
    private void renderCell(TableColumn tc) {

        tc.setCellFactory(column -> {
            return new TableCell<MonthViewBean, String>() 
            {
                @Override
                protected void updateItem(String item, boolean empty) 
                {
                    super.updateItem(item, empty);
                    setText(item);
                    if (item == null || empty) 
                    {
                        setText(null);
                        setStyle("");
                    }
                    else
                    {
                        if(item.split("\\n").length > 1)
                        {
                            VBox vbox = new VBox();
                            //DELETE
                            Label dayOfMonthLbl = new Label(item.split("\\n")[0]);
                            vbox.getChildren().add(dayOfMonthLbl);
                            for(int i = 1; i < item.split("\\n").length; i++)
                            {
                                try 
                                {
                                    // A label to hold individual appointment titles
                                    Label lbl = new Label(item.split("\\n")[i]);
                                    Appointment appointment = appointmentDAO.findByTitle(item.split("\\n")[i]).get(0);
                                    GroupRecord group = groupRecordDAO.findID(appointment.getAppointmentGroup());
                                    
                                    Color color = group.colourProperty().getValue();
                                    String hex = String.format("#%02x%02x%02x", (int)(color.getRed()*255), (int)(color.getGreen()*255), (int)(color.getBlue()*255));  
                                    
                                    lbl.setStyle("-fx-background-color: " + hex);
                                    
                                    // If the background color isn't white, set the text color to white
                                    if(!color.equals(Color.WHITE))
                                        lbl.setTextFill(Color.WHITE);
                                    
                                    // Set the label text to the title of the current appointment
                                    lbl.setText(appointment.getTitle());
                                    
                                    // Add the label to the VBox
                                    vbox.getChildren().add(lbl);
                                } 
                                catch (SQLException ex) 
                                {
                                    java.util.logging.Logger.getLogger(MonthViewController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            
                            // The data of the cell, exluding the appointment labels will only consist of the day of the month.
                            setText("");
                            
                            // Add the VBox to the cell
                            setGraphic(vbox);
                        }
                    }
                }
            };
        });

    }


}
