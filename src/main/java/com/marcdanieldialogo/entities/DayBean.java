package com.marcdanieldialogo.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marc-Daniel
 */
public class DayBean 
{
    List<HalfHourOfDay> halfHours;
    LocalDate date;
    
    public DayBean(LocalDate date)
    {
        this.date = date;
        setHoursForDay(this.date);
    }
    
    public DayBean()
    {
        this(LocalDate.now());
    }
    
    public void setHoursForDay(LocalDate date)
    {
        List<HalfHourOfDay> halfHours = new ArrayList<>();
        LocalDateTime ldt = date.atStartOfDay();
        
        for(int i = 0; i < 48; i++)
        {
            HalfHourOfDay dayBean = new HalfHourOfDay();
            dayBean.setDate(ldt);
            dayBean.insertPropertyStrings();
            halfHours.add(dayBean);

            ldt = ldt.plusMinutes(30);
        }
        
        this.halfHours = halfHours;
    }
    
    public List<HalfHourOfDay> getHalfHourList()
    {
        return halfHours;
    }
}
