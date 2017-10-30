package com.marcdanieldialogo.persistence;

import com.marcdanieldialogo.entities.Appointment;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for Appointment CRUD methods
 * 
 * @author Marc-Daniel
 */
public interface AppointmentDAO {
    // Create
    public int create(Appointment appointment) throws SQLException;

    // Read
    public List<Appointment> findAll() throws SQLException;

    public Appointment findID(int id) throws SQLException;
    // Update

    public int update(Appointment appointment) throws SQLException;

    // Delete
    public int delete(int ID) throws SQLException;
    
    // Queries
    public List<Appointment> findByTitle(String title) throws SQLException;
    
    public List<Appointment> findByDate(LocalDateTime date) throws SQLException;
    
    public List<Appointment> findByDay(LocalDateTime date) throws SQLException;
    
    public List<Appointment> findByWeek(LocalDateTime date) throws SQLException;
    
    public List<Appointment> findByMonth(LocalDateTime date) throws SQLException;
    
    public List<Appointment> findBetweenDates(LocalDateTime date1, LocalDateTime date2) throws SQLException;
    public List<Appointment> findBetweenDateTimes(LocalDateTime date1, LocalDateTime date2) throws SQLException;
    
    public Appointment findNextByID(Appointment appointment) throws SQLException;
    public Appointment findPrevByID(Appointment appointment) throws SQLException;
}
