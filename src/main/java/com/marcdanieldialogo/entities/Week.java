package com.marcdanieldialogo.entities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Marc-Daniel
 */
public class Week {
    
    private LocalDate date;
    
    public Week(LocalDate date)
    {
        this.date = date;
    }
    
    public List<StringProperty> getWeek()
    {
        List<StringProperty> week = new ArrayList<StringProperty>();
        
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
    
    public LocalDate getDate()
    {
        return date;
    }
    
    public LocalDate nextWeek()
    {
        return date.plusDays(7);
    }
}
