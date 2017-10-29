package com.marcdanieldialogo.entities;

import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Marc-Daniel
 */
public class DayBean 
{
    StringProperty timeCol;
    StringProperty appointmentCol;
    
    private LocalDateTime currentDay;
    private AppointmentDAO appointmentDAO;
    
    public DayBean(String timeCol, String appointmentCol)
    {
        this.timeCol = new SimpleStringProperty(timeCol);
        this.appointmentCol = new SimpleStringProperty(appointmentCol);
        
        appointmentDAO = new AppointmentDAOImpl();
    }
    
    public DayBean()
    {
        this("", "");
    }
    
    public void setTimeCol(String timeCol)
    {
        this.timeCol.set(timeCol);
    }
    
    public void setAppointmentCol(String appointmentCol)
    {
        this.appointmentCol.set(appointmentCol);
    }
    
    public String getTimeCol()
    {
        return timeCol.get();
    }
    
    public StringProperty timeColProperty()
    {
        return timeCol;
    }
    
    public String getAppointmentCol()
    {
        return appointmentCol.get();
    }
    
    public StringProperty appointmentColProperty()
    {
        return appointmentCol;
    }
    
    public void setDate(LocalDateTime day)
    {
        currentDay = day;
        
        LocalTime time = LocalTime.of(day.getHour(), day.getMinute());
        timeCol.set(time.toString());
        try 
        {
            List<Appointment> appointments = appointmentDAO.findByDate(currentDay);
            for(Appointment item : appointments)
            {
                String prepend = "";
                if(!appointmentCol.get().isEmpty())
                {
                    prepend = appointmentCol.get() + "\n";
                }
                
                appointmentCol.set(prepend + item.getTitle() + ",\tlocation: " + item.getLocation() +
                        ",\tend time: " + LocalTime.of(item.getEndTime().toLocalDateTime().getHour(), item.getEndTime().toLocalDateTime().getMinute()).toString());
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DayBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
