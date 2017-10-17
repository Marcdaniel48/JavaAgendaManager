package com.marcdanieldialogo.persistence;

import com.marcdanieldialogo.entities.SMTPSettings;
import com.marcdanieldialogo.Utilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the SMTPSettingsDAO interface
 * 
 * @author Marc-Daniel
 */
public class SMTPSettingsDAOImpl implements SMTPSettingsDAO{

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    // Has a method that can make and return a connection.
    private Utilities util = new Utilities();
    
    /**
     * Takes in an SMTPSettings object and inserts its values into the SMTP_SETTINGS table as a record.
     *
     * @param smtp
     * @return records  number of records created, should be 0 or 1
     * @throws SQLException
     */
    @Override
    public int create(SMTPSettings smtp) throws SQLException {
        int records;

        String sql = "INSERT INTO SMTP_SETTINGS(USERNAME, EMAIL, EMAIL_PASSWORD, SMTP_URL, SMTP_PORT, DEFAULT_SMTP, REMINDER_INTERVAL) values (?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = util.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);)
        {
            stmt.setString(1, smtp.getUsername());
            stmt.setString(2, smtp.getEmail());
            stmt.setString(3, smtp.getEmailPassword());
            stmt.setString(4, smtp.getSMTPURL());
            stmt.setInt(5, smtp.getSMTPPort());
            stmt.setInt(6, smtp.getDefaultSMTP());
            stmt.setInt(7, smtp.getReminderInterval());
            
            if(smtp.getDefaultSMTP() == 1)
            {
                String updateDefaultSQL = "UPDATE SMTP_SETTINGS SET DEFAULT_SMTP = 0 WHERE DEFAULT_SMTP = 1";
                try(PreparedStatement updateDefaultStmt = conn.prepareStatement(updateDefaultSQL))
                {
                    updateDefaultStmt.executeUpdate();
                }
            }
            
            records = stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            
            int recordNum = -1;
            
            if (rs.next()) {
                recordNum = rs.getInt(1);
            }
            
            smtp.setSMTPID(recordNum);
            log.debug("New record ID is " + recordNum);
            
        }
        
        return records;
    }

     /**
     * Selects all the records in the SMTP_SETTINGS table and returns
     * a list of SMTPSettings objects with the records' values.
     *
     * @return rows A list of SMTPSettings objects representing the records of the SMTP_SETTINGS table
     * @throws SQLException
     */
    @Override
    public List<SMTPSettings> findAll() throws SQLException {
         List<SMTPSettings> rows = new ArrayList<>();

        String selectQuery = "SELECT * FROM SMTP_SETTINGS";
        try (Connection connection = util.getConnection();
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);
                ResultSet results = pStatement.executeQuery()) 
        {
            while (results.next()) {
                SMTPSettings smtp = new SMTPSettings();
                smtp.setSMTPID(results.getInt("SMTP_ID"));
                smtp.setUsername(results.getString("USERNAME"));
                smtp.setEmail(results.getString("EMAIL"));
                smtp.setEmailPassword(results.getString("EMAIL_PASSWORD"));
                smtp.setSMTPURL(results.getString("SMTP_URL"));
                smtp.setSMTPPort(results.getInt("SMTP_PORT"));
                smtp.setReminderInterval(results.getInt("REMINDER_INTERVAL"));
                
                rows.add(smtp);
            }
        }
        return rows;
    }

    /**
     * Takes in an int ID and uses it to retrieve a record in the SMTP_SETTINGS table with the same
     * ID. Then uses that record's values to fill in an SMTPSettings object that will be returned.
     * @param id    Represents the ID of an SMTP_SETTINGS record
     * @return  smtp    Returns an SMTPSettings object with values equivalent to the selected record.
     * @throws SQLException 
     */
    @Override
    public SMTPSettings findID(int id) throws SQLException {
        SMTPSettings smtp = new SMTPSettings();
        
        String selectQuery = "SELECT * FROM SMTP_SETTINGS WHERE SMTP_ID = ?";
        
        try (Connection connection = util.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1, id);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    smtp = new SMTPSettings();
                    smtp.setSMTPID(resultSet.getInt("SMTP_ID"));
                    smtp.setUsername(resultSet.getString("USERNAME"));
                    smtp.setEmail(resultSet.getString("EMAIL"));
                    smtp.setEmailPassword(resultSet.getString("EMAIL_PASSWORD"));
                    smtp.setSMTPURL(resultSet.getString("SMTP_URL"));
                    smtp.setSMTPPort(resultSet.getInt("SMTP_PORT"));
                    smtp.setReminderInterval(resultSet.getInt("REMINDER_INTERVAL"));
                }
            }
        }
        return smtp;
    }

    /**
     * Takes in an SMTPSettings object and uses it's ID value to find and update a record in the SMTP_SETTINGS table.
     * 
     * @param smtp
     * @return record   The number of updated records. Should be either 0 or 1.
     * @throws SQLException 
     */
    @Override
    public int update(SMTPSettings smtp) throws SQLException {
        int record = 0;
        String updateStatement = "UPDATE SMTP_SETTINGS SET USERNAME = ?, EMAIL = ?, EMAIL_PASSWORD = ?, SMTP_URL = ?, SMTP_PORT = ?,"
                + "DEFAULT_SMTP = ?, REMINDER_INTERVAL = ? WHERE SMTP_ID = ?";
        
        try
        (Connection conn = util.getConnection(); PreparedStatement pStatement = conn.prepareStatement(updateStatement);)
        {
            pStatement.setString(1, smtp.getUsername());
            pStatement.setString(2, smtp.getEmail());
            pStatement.setString(3, smtp.getEmailPassword());
            pStatement.setString(4, smtp.getSMTPURL());
            pStatement.setInt(5, smtp.getSMTPPort());
            pStatement.setInt(6, smtp.getDefaultSMTP());
            pStatement.setInt(7, smtp.getReminderInterval());
            pStatement.setInt(8, smtp.getSMTPID());
            
            if(smtp.getDefaultSMTP() == 1)
            {
                String updateDefaultSQL = "UPDATE SMTP_SETTINGS SET DEFAULT_SMTP = 0 WHERE DEFAULT_SMTP = 1";
                try(PreparedStatement updateDefaultStmt = conn.prepareStatement(updateDefaultSQL))
                {
                    updateDefaultStmt.executeUpdate();
                }
            }
            
            pStatement.executeUpdate();
            record = 1;
        }
        
        return record;
    }

    /**
     * Takes in an int ID and uses it to remove a record of the SMTP_SETTINGS table with the same ID value.
     * 
     * @param ID    ID of the record that's to be deleted.
     * @return record   The number of deleted records. Should be either 0 or 1.
     * @throws SQLException 
     */
    @Override
    public int delete(int ID) throws SQLException {
       int record = 0;
        String stmt = "DELETE FROM SMTP_SETTINGS WHERE SMTP_ID = ?";
        
        try
        (Connection connection = util.getConnection(); PreparedStatement pStatement = connection.prepareStatement(stmt);)
        {
            pStatement.setInt(1, ID);
            
            pStatement.executeUpdate();
            record = 1;
        }
        
        return record;
    }

    /**
     * Takes in a String representing an email address and uses it to retrieve a record of the SMTP_SETTINGS
     * table with the same EMAIL value. The values of the selected record will be used to fill in an
     * SMTPSettings object which will then be returned.
     * 
     * @param email An email address
     * @return smtp An SMTPSettings object that represents a record of the SMTP_SETTINGS table
     * @throws SQLException 
     */
    @Override
    public SMTPSettings findByEmail(String email) throws SQLException {
        SMTPSettings smtp = new SMTPSettings();
        
        String selectQuery = "SELECT * FROM SMTP_SETTINGS WHERE EMAIL = ?";
        
        try (Connection connection = util.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) 
        {
            pStatement.setString(1, email);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    smtp.setSMTPID(resultSet.getInt("SMTP_ID"));
                    smtp.setUsername(resultSet.getString("USERNAME"));
                    smtp.setEmail(resultSet.getString("EMAIL"));
                    smtp.setEmailPassword(resultSet.getString("EMAIL_PASSWORD"));
                    smtp.setSMTPURL(resultSet.getString("SMTP_URL"));
                    smtp.setSMTPPort(resultSet.getInt("SMTP_PORT"));
                    smtp.setDefaultSMTP(resultSet.getInt("DEFAULT_SMTP"));
                    smtp.setReminderInterval(resultSet.getInt("REMINDER_INTERVAL"));
                }
            }
        }
        return smtp;
    }
    
}
