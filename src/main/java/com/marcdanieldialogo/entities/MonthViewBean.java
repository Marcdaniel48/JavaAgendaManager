package com.marcdanieldialogo.entities;

import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Marc-Daniel
 */
public class MonthViewBean 
{
    private StringProperty sundayCol;
    private StringProperty mondayCol;
    private StringProperty tuesdayCol;
    private StringProperty wednesdayCol;
    private StringProperty thursdayCol;
    private StringProperty fridayCol;
    private StringProperty saturdayCol;
    
    private LocalDate currentDay;
    private AppointmentDAO appointmentDAO;
    
    public MonthViewBean(String sundayCol, String mondayCol, String tuesdayCol, String wednesdayCol, String thursdayCol, String fridayCol,
            String saturdayCol)
    {
        this.sundayCol = new SimpleStringProperty(sundayCol);
        this.mondayCol = new SimpleStringProperty(mondayCol);
        this.tuesdayCol = new SimpleStringProperty(tuesdayCol);
        this.wednesdayCol = new SimpleStringProperty(wednesdayCol);
        this.thursdayCol = new SimpleStringProperty(thursdayCol);
        this.fridayCol = new SimpleStringProperty(fridayCol);
        this.saturdayCol = new SimpleStringProperty(saturdayCol);
        
        currentDay = LocalDate.now();
        appointmentDAO = new AppointmentDAOImpl();
    }
    
    public MonthViewBean()
    {
        this("", "", "", "", "", "", "");
    }
    
    public void setDate(LocalDate day)
    {
        currentDay = day;
    }
    
    /**
     * Each column will be set to its day of the month plus the titles of any appointments that are in the database for that day.
     */
    public void setWeek()
    {
        LocalDate newDate = LocalDate.of(currentDay.getYear(), currentDay.getMonthValue(), currentDay.getDayOfMonth());
        
        while(newDate.getDayOfWeek() != DayOfWeek.SUNDAY)
        {
            newDate = newDate.minusDays(1);
        }
        
        while(newDate.getDayOfWeek() != DayOfWeek.SATURDAY)
        {
            switch (newDate.getDayOfWeek()) 
            {
                case SUNDAY:
                    if(newDate.getMonthValue() != currentDay.getMonthValue())
                        sundayCol.set("");
                    else
                    {
                        sundayCol.set(String.valueOf(newDate.getDayOfMonth()));
                        
                        try 
                        {
                            List<Appointment> list = appointmentDAO.findByDay(LocalDateTime.of(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth(), 0, 0));
                            for(Appointment item : list)
                            {
                                sundayCol.set(sundayCol.get()+ "\n" + item.getTitle());
                            }
                        } 
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(MonthViewBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    break;
                case MONDAY:
                    if(newDate.getMonthValue() != currentDay.getMonthValue())
                        mondayCol.set("");
                    else
                    {
                        mondayCol.set(String.valueOf(newDate.getDayOfMonth()));
                        
                        try 
                        {
                            List<Appointment> list = appointmentDAO.findByDay(LocalDateTime.of(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth(), 0, 0));
                            for(Appointment item : list)
                            {
                                mondayCol.set(mondayCol.get()+ "\n" + item.getTitle());
                            }
                        } 
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(MonthViewBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case TUESDAY:
                    if(newDate.getMonthValue() != currentDay.getMonthValue())
                        tuesdayCol.set("");
                    else
                    {
                        tuesdayCol.set(String.valueOf(newDate.getDayOfMonth()));
                        try 
                        {
                            List<Appointment> list = appointmentDAO.findByDay(LocalDateTime.of(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth(), 0, 0));
                            for(Appointment item : list)
                            {
                                tuesdayCol.set(tuesdayCol.get()+ "\n" + item.getTitle());
                            }
                        } 
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(MonthViewBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case WEDNESDAY:
                    if(newDate.getMonthValue() != currentDay.getMonthValue())
                        wednesdayCol.set("");
                    else
                    {
                        wednesdayCol.set(String.valueOf(newDate.getDayOfMonth()));
                        try 
                        {
                            List<Appointment> list = appointmentDAO.findByDay(LocalDateTime.of(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth(), 0, 0));
                            for(Appointment item : list)
                            {
                                wednesdayCol.set(wednesdayCol.get()+ "\n" + item.getTitle());
                            }
                        } 
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(MonthViewBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case THURSDAY:
                    if(newDate.getMonthValue() != currentDay.getMonthValue())
                        thursdayCol.set("");
                    else
                    {
                        thursdayCol.set(String.valueOf(newDate.getDayOfMonth()));
                        try 
                        {
                            List<Appointment> list = appointmentDAO.findByDay(LocalDateTime.of(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth(), 0, 0));
                            for(Appointment item : list)
                            {
                                thursdayCol.set(thursdayCol.get()+ "\n" + item.getTitle());
                            } 
                        } 
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(MonthViewBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case FRIDAY:
                    if(newDate.getMonthValue() != currentDay.getMonthValue())
                        fridayCol.set("");
                    else
                    {
                        fridayCol.set(String.valueOf(newDate.getDayOfMonth()));
                        try 
                        {
                            List<Appointment> list = appointmentDAO.findByDay(LocalDateTime.of(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth(), 0, 0));
                            for(Appointment item : list)
                            {
                                fridayCol.set(fridayCol.get()+ "\n" + item.getTitle());
                            }
                        } 
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(MonthViewBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case SATURDAY:
                    
                    
                    break;
                default:
                    break;
            }
            
            newDate = newDate.plusDays(1);
        }
        
        // SATURDAY
        if(newDate.getMonthValue() != currentDay.getMonthValue())
            saturdayCol.set("");
        else
        {
            saturdayCol.set(String.valueOf(newDate.getDayOfMonth()));
            try 
            {
                List<Appointment> list = appointmentDAO.findByDay(LocalDateTime.of(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth(), 0, 0));
                for(Appointment item : list)
                {
                    saturdayCol.set(saturdayCol.get()+ "\n" + item.getTitle());
                }
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(MonthViewBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setSundayCol(String data)
    {
        sundayCol.set(data);
    }
    
    public void setMondayCol(String data)
    {
        mondayCol.set(data);
    }
    
    public void setTuesdayCol(String data)
    {
        tuesdayCol.set(data);
    }
    
    public void setWednesdayCol(String data)
    {
        wednesdayCol.set(data);
    }
    
    public void setThursdayCol(String data)
    {
        thursdayCol.set(data);
    }
    
    public void setFridayCol(String data)
    {
        fridayCol.set(data);
    }
    
    public void setSaturdayCol(String data)
    {
        saturdayCol.set(data);
    }
    
    public String getSundayCol()
    {
        return sundayCol.get();
    }
    
    public StringProperty sundayColProperty()
    {
        return sundayCol;
    }
    
    public String getMondayCol()
    {
        return mondayCol.get();
    }
    
    public StringProperty mondayColProperty()
    {
        return mondayCol;
    }
    
    public String getTuesdayCol()
    {
        return tuesdayCol.get();
    }
    
    public StringProperty tuesdayColProperty()
    {
        return tuesdayCol;
    }
    
    public String getWednesdayCol()
    {
        return wednesdayCol.get();
    }
    
    public StringProperty wednesdayColProperty()
    {
        return wednesdayCol;
    }
    
    public String getThursdayCol()
    {
        return thursdayCol.get();
    }
    
    public StringProperty thursdayColProperty()
    {
        return thursdayCol;
    }
    
    public String getFridayCol()
    {
        return fridayCol.get();
    }
    
    public StringProperty fridayColProperty()
    {
        return fridayCol;
    }
    
    public String getSaturdayCol()
    {
        return saturdayCol.get();
    }
    
    public StringProperty saturdayColProperty()
    {
        return saturdayCol;
    }
}


