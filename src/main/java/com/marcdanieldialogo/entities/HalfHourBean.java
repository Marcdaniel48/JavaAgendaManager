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
public class HalfHourBean 
{
    private StringProperty timeCol;
    private StringProperty appointmentCol;
    
    private LocalDateTime currentDay;
    private AppointmentDAO appointmentDAO;
    private List<Appointment> appointmentList;
    
    public HalfHourBean(String timeCol, String appointmentCol)
    {
        this.timeCol = new SimpleStringProperty(timeCol);
        this.appointmentCol = new SimpleStringProperty(appointmentCol);
        
        appointmentDAO = new AppointmentDAOImpl();
        appointmentList = new ArrayList<>();
    }
    
    public HalfHourBean()
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
        try 
        {
            if(day.getMinute() < 30)
            {
                appointmentList = appointmentDAO.findBetweenDateTimes(currentDay.withMinute(0), currentDay.withMinute(29));
            }
            else if(day.getMinute() > 29)
            {
                appointmentList = appointmentDAO.findBetweenDateTimes(currentDay.withMinute(30), currentDay.withMinute(59));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(HalfHourBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertPropertyStrings()
    {
        LocalTime time = LocalTime.of(currentDay.getHour(), currentDay.getMinute());
        timeCol.set(time.toString());
        
        for(Appointment item : appointmentList)
        {
            String prepend = "";
            if(!appointmentCol.get().isEmpty())
            {
                prepend = appointmentCol.get() + "\n";
            }

            appointmentCol.set(prepend + item.getTitle() + ",\tlocation: " + item.getLocation() +
                    ",\tstart time: " + LocalTime.of(item.getStartTime().toLocalDateTime().getHour(), item.getStartTime().toLocalDateTime().getMinute()).toString() +
                    ",\tend time: " + LocalTime.of(item.getEndTime().toLocalDateTime().getHour(), item.getEndTime().toLocalDateTime().getMinute()).toString());
        }
    }
}
