/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcdanieldialogo.entities;

import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Used by the WeekViewController to set and display data into the weekly view table.
 * @author Marc-Daniel
 */
public class HalfHourOfWeek 
{
    private List<LocalDateTime> daysOfWeek;
    private LocalDateTime currentDate;
    
    private StringProperty timeCol;
    private StringProperty sundayCol;
    private List<Appointment> sundayAppointments;
    private StringProperty mondayCol;
    private List<Appointment> mondayAppointments;
    private StringProperty tuesdayCol;
    private List<Appointment> tuesdayAppointments;
    private StringProperty wednesdayCol;
    private List<Appointment> wednesdayAppointments;
    private StringProperty thursdayCol;
    private List<Appointment> thursdayAppointments;
    private StringProperty fridayCol;
    private List<Appointment> fridayAppointments;
    private StringProperty saturdayCol;
    private List<Appointment> saturdayAppointments;
    
    AppointmentDAO appointmentDAO;
    
    public HalfHourOfWeek(String timeCol, String sundayCol, String mondayCol, String tuesdayCol, String wednesdayCol, String thursdayCol, String fridayCol,
            String saturdayCol)
    {
        this.timeCol = new SimpleStringProperty(timeCol);
        this.sundayCol = new SimpleStringProperty(sundayCol);
        this.mondayCol = new SimpleStringProperty(mondayCol);
        this.tuesdayCol = new SimpleStringProperty(tuesdayCol);
        this.wednesdayCol = new SimpleStringProperty(wednesdayCol);
        this.thursdayCol = new SimpleStringProperty(thursdayCol);
        this.fridayCol = new SimpleStringProperty(fridayCol);
        this.saturdayCol = new SimpleStringProperty(saturdayCol);
        
        daysOfWeek = new ArrayList<>();
        
        sundayAppointments = new ArrayList<>();
        mondayAppointments = new ArrayList<>();
        tuesdayAppointments = new ArrayList<>();
        wednesdayAppointments = new ArrayList<>();
        thursdayAppointments = new ArrayList<>();
        fridayAppointments = new ArrayList<>();
        saturdayAppointments = new ArrayList<>();
        
        appointmentDAO = new AppointmentDAOImpl();
    }
    
    public HalfHourOfWeek()
    {
        this("", "", "", "", "", "", "", "");
    }
    
    public StringProperty timeProperty()
    {
        return timeCol;
    }
    public StringProperty sundayProperty()
    {
        return sundayCol;
    }
    public StringProperty mondayProperty()
    {
        return mondayCol;
    }
    public StringProperty tuesdayProperty()
    {
        return tuesdayCol;
    }
    public StringProperty wednesdayProperty()
    {
        return wednesdayCol;
    }
    public StringProperty thursdayProperty()
    {
        return thursdayCol;
    }
    public StringProperty fridayProperty()
    {
        return fridayCol;
    }
    public StringProperty saturdayProperty()
    {
        return saturdayCol;
    }
    
    public void setDate(LocalDateTime date)
    {
        this.currentDate = date;
        daysOfWeek.clear();
        
        while(date.getDayOfWeek() != DayOfWeek.SUNDAY)
        {
            date = date.minusDays(1);
        }
        
        while(date.getDayOfWeek() != DayOfWeek.SATURDAY)
        {
            daysOfWeek.add(date);
            date = date.plusDays(1);
        }
        
        daysOfWeek.add(date);
        
        try 
        {
            insertPropertyStrings();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(HalfHourOfWeek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertPropertyStrings() throws SQLException
    {
        LocalDateTime date = currentDate;
        
        timeCol.set(LocalTime.of(currentDate.getHour(), currentDate.getMinute()).toString());
        
        sundayAppointments = appointmentDAO.findBetweenDateTimes(daysOfWeek.get(0), daysOfWeek.get(0).plusMinutes(29));
        mondayAppointments = appointmentDAO.findBetweenDateTimes(daysOfWeek.get(1), daysOfWeek.get(1).plusMinutes(29));
        tuesdayAppointments = appointmentDAO.findBetweenDateTimes(daysOfWeek.get(2), daysOfWeek.get(2).plusMinutes(29));
        wednesdayAppointments = appointmentDAO.findBetweenDateTimes(daysOfWeek.get(3), daysOfWeek.get(3).plusMinutes(29));
        thursdayAppointments = appointmentDAO.findBetweenDateTimes(daysOfWeek.get(4), daysOfWeek.get(4).plusMinutes(29));
        fridayAppointments = appointmentDAO.findBetweenDateTimes(daysOfWeek.get(5), daysOfWeek.get(5).plusMinutes(29));
        saturdayAppointments = appointmentDAO.findBetweenDateTimes(daysOfWeek.get(6), daysOfWeek.get(6).plusMinutes(29));
        
        LocalTime time = LocalTime.of(currentDate.getHour(), currentDate.getMinute());
        timeCol.set(time.toString());
        
        for(int i = 0; i < sundayAppointments.size(); i++)
        {
            String prepend = "";
            if(!sundayCol.get().isEmpty())
            {
                prepend = sundayCol.get() + "\n";
            }
            sundayCol.set(prepend + sundayAppointments.get(i).getTitle());
        }
        for(int i = 0; i < mondayAppointments.size(); i++)
        {
            String prepend = "";
            if(!mondayCol.get().isEmpty())
            {
                prepend = mondayCol.get() + "\n";
            }
            mondayCol.set(prepend + mondayAppointments.get(i).getTitle());
        }
        for(int i = 0; i < tuesdayAppointments.size(); i++)
        {
            String prepend = "";
            if(!tuesdayCol.get().isEmpty())
            {
                prepend = tuesdayCol.get() + "\n";
            }
            tuesdayCol.set(prepend + tuesdayAppointments.get(i).getTitle());
        }
        for(int i = 0; i < wednesdayAppointments.size(); i++)
        {
            String prepend = "";
            if(!wednesdayCol.get().isEmpty())
            {
                prepend = wednesdayCol.get() + "\n";
            }
            wednesdayCol.set(prepend + wednesdayAppointments.get(i).getTitle());
        }
        for(int i = 0; i < thursdayAppointments.size(); i++)
        {
            String prepend = "";
            if(!thursdayCol.get().isEmpty())
            {
                prepend = thursdayCol.get() + "\n";
            }
            thursdayCol.set(prepend + thursdayAppointments.get(i).getTitle());
        }
        for(int i = 0; i < fridayAppointments.size(); i++)
        {
            String prepend = "";
            if(!fridayCol.get().isEmpty())
            {
                prepend = fridayCol.get() + "\n";
            }
            fridayCol.set(prepend + fridayAppointments.get(i).getTitle());
        }
        for(int i = 0; i < saturdayAppointments.size(); i++)
        {
            String prepend = "";
            if(!saturdayCol.get().isEmpty())
            {
                prepend = saturdayCol.get() + "\n";
            }
            saturdayCol.set(prepend + saturdayAppointments.get(i).getTitle());
        }
    }
}
