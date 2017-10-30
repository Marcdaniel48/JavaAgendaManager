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
    List<HalfHourBean> halfHours;
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
        List<HalfHourBean> halfHours = new ArrayList<>();
        LocalDateTime ldt = date.atStartOfDay();
        
        for(int i = 0; i < 48; i++)
        {
            HalfHourBean dayBean = new HalfHourBean();
            dayBean.setDate(ldt);
            dayBean.insertPropertyStrings();
            halfHours.add(dayBean);

            ldt = ldt.plusMinutes(30);
        }
        
        this.halfHours = halfHours;
    }
    
    public List<HalfHourBean> getHalfHourList()
    {
        return halfHours;
    }
}
