package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.Week;
import com.marcdanieldialogo.MainApp;
import com.marcdanieldialogo.entities.Appointment;
import com.marcdanieldialogo.entities.GroupRecord;
import com.marcdanieldialogo.entities.SMTPSettings;
import com.marcdanieldialogo.entities.WeekBean;
import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import com.marcdanieldialogo.persistence.GroupRecordDAO;
import com.marcdanieldialogo.persistence.GroupRecordDAOImpl;
import com.marcdanieldialogo.persistence.SMTPSettingsDAO;
import com.marcdanieldialogo.persistence.SMTPSettingsDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
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
    private TableView<WeekBean> monthTable; // Value injected by FXMLLoader

    @FXML // fx:id="sundayCells"
    private TableColumn<WeekBean, String> sundayCells; // Value injected by FXMLLoader

    @FXML // fx:id="mondayCells"
    private TableColumn<WeekBean, String> mondayCells; // Value injected by FXMLLoader

    @FXML // fx:id="tuesdayCells"
    private TableColumn<WeekBean, String> tuesdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="wednesdayCells"
    private TableColumn<WeekBean, String> wednesdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="thursdayCells"
    private TableColumn<WeekBean, String> thursdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="fridayCells"
    private TableColumn<WeekBean, String> fridayCells; // Value injected by FXMLLoader

    @FXML // fx:id="saturdayCells"
    private TableColumn<WeekBean, String> saturdayCells; // Value injected by FXMLLoader
    
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
    }
    
    /**
     * Will fill the table with days of the current month, making the table look more like a calendar
     */
    public void displayTable()
    {
        ObservableList<WeekBean> weekList = FXCollections.observableArrayList();
        
        LocalDate currentDate = LocalDate.of(currentYear, currentMonth, 1);
        currentMonthLabel.setText(currentDate.getMonth().toString() + " " + currentDate.getYear());
        
        for(int i = 0; i < 6; i++)
        {
            WeekBean weekBean = new WeekBean();
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
    }
    
    
    /**
    *    Turns the cells into squares, to make the table look more like a calendar
    */
    private void adjustColumnWidths() {
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
        
            if(!((String)data).equals(""))
            {
                LocalDate selectedDay = LocalDate.of(currentYear, currentMonth, Integer.parseInt(data));
                
                handleOpen(selectedDay);
            }
        }
    }
    
    /**
     * When the user clicks on a square of the calendar, this method handles opening up the day view for that particular square
     * @param selectedDay 
     */
    public void handleOpen(LocalDate selectedDay)
    {
        try
        {   
            Stage dayStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("DayViewText"));
            loader.setLocation(MainApp.class.getResource("/fxml/DayView.fxml"));
            
            Parent dayPane = (BorderPane) loader.load();

            Scene dayScene = new Scene(dayPane);
            
            dayStage.setScene(dayScene);
            DayViewController controller = loader.getController();
            controller.setDay(selectedDay);
            dayStage.setTitle("Day View");
            dayStage.show();
        }
        catch(IOException ioe)
        {
            Platform.exit();
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
            Platform.exit();
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
    
    /**
     * Closes the window
     */
    @FXML
    private void handleExit()
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
