package com.marcdanieldialogo.persistence;

import com.marcdanieldialogo.entities.SMTPSettings;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface for SMTPSettings CRUD methods
 * 
 * @author Marc-Daniel
 */
public interface SMTPSettingsDAO {
    // Create
    public int create(SMTPSettings smtp) throws SQLException;

    // Read
    public List<SMTPSettings> findAll() throws SQLException;

    public SMTPSettings findID(int id) throws SQLException;
    
    // Update
    public int update(SMTPSettings smtp) throws SQLException;

    // Delete
    public int delete(int ID) throws SQLException;
    
    // Queries
    public SMTPSettings findByEmail(String email) throws SQLException;
    
    public SMTPSettings findNextByID(SMTPSettings smtpSettings) throws SQLException;
    public SMTPSettings findPrevByID(SMTPSettings smtpSettings) throws SQLException;
    
    public SMTPSettings findDefaultSMTP() throws SQLException;
}
