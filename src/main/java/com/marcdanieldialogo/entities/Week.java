package com.marcdanieldialogo.entities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is mainly used by the MonthViewController to display the days of a month on a calendar-looking table
 * @author Marc-Daniel
 */
public class Week 
{
    
    // Used to know the range of the week
    private LocalDate date;
    
    public Week(LocalDate date)
    {
        this.date = date;
    }
    
    /**
     * Returns a list of StringProperty that holds the numbers of each day for a particular week
     * @return 
     */
    public List<StringProperty> getWeek()
    {
        List<StringProperty> week = new ArrayList<>();
        
        LocalDate newDate = LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        
        while(newDate.getDayOfWeek() != DayOfWeek.SUNDAY)
        {
            newDate = newDate.minusDays(1);
        }
        
        while(newDate.getDayOfWeek() != DayOfWeek.SATURDAY)
        {
            if(newDate.getMonthValue() != date.getMonthValue())
            {
                week.add(new SimpleStringProperty(""));
            }
            else
            {
                week.add(new SimpleStringProperty(String.valueOf(newDate.getDayOfMonth())));
            }
            
            newDate = newDate.plusDays(1);
        }
        
        if(newDate.getMonthValue() != date.getMonthValue())
        {
            week.add(new SimpleStringProperty(""));
        }
        else
        {
            week.add(new SimpleStringProperty(String.valueOf(newDate.getDayOfMonth())));
        }
        
        return week;
    }
    
    /**
     * Moves LocalDate date to the next week
     * @return 
     */
    public LocalDate nextWeek()
    {
        return date.plusDays(7);
    }
    
    /**
     * Getter for LocalDate date
     * @return 
     */
    public LocalDate getDate()
    {
        return date;
    }
}
