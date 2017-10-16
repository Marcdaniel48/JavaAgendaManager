/**
 * Sample Skeleton for 'GroupForm.fxml' Controller Class
 */

package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.GroupRecord;
import com.marcdanieldialogo.persistence.GroupRecordDAO;
import com.marcdanieldialogo.persistence.GroupRecordDAOImpl;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    @FXML // fx:id="groupNumberTextField"
    private TextField groupNumberTextField; // Value injected by FXMLLoader
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private GroupRecordDAO groupRecordDAO;
    private GroupRecord groupRecord;

    @FXML
    void handleCreate(ActionEvent event) {
        try{
            
        groupRecordDAO = new GroupRecordDAOImpl();
        groupRecord = new GroupRecord();
        
        groupRecord.setGroupName(groupNameTextField.getText());
        groupRecord.setColour(colourTextField.getText());
        
        groupRecordDAO.create(groupRecord);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }

    @FXML
    void handleExit(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
    
    public void setGroupRecordDAO(GroupRecord groupRecord, GroupRecordDAO groupRecordDAO)
    {
        this.groupRecord = groupRecord;
        this.groupRecordDAO = groupRecordDAO;
    }
    
    @FXML
    void handleUpdate(ActionEvent event) {

    }
    
    @FXML
    void handleDelete(ActionEvent event) {

    }
    
    @FXML
    void handleClear(ActionEvent event) {
        groupNameTextField.setText("");
        colourTextField.setText("");
    }
}
