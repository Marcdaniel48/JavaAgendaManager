/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcdanieldialogo.entities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Marc-Daniel
 */
public class WeekBean 
{
    List<DayBean> days;
    LocalDate currentDate;
    
    StringProperty timeCell;
    StringProperty sundayCell;
    StringProperty mondayCell;
    StringProperty tuesdayCell;
    StringProperty wednesdayCell;
    StringProperty thursdayCell;
    StringProperty fridayCell;
    StringProperty saturdayCell;
    
    public WeekBean(LocalDate date)
    {
        this.currentDate = date;
        setDaysForWeek(date);
        
        for(int i = 0; i < 48; i++)
        {
            
        }
    }
    
    public WeekBean()
    {
        this(LocalDate.now());
    }
    
    public void setDaysForWeek(LocalDate date)
    {
        List<DayBean> days = new ArrayList<>();
        while(date.getDayOfWeek() != DayOfWeek.SATURDAY)
        {
            if(date.getMonthValue() != date.getMonthValue())
            {
                days.add(null);
            }
            else
            {
                DayBean dayBean = new DayBean(date);
                days.add(dayBean);
            }
            
            date = date.plusDays(1);
        }
        
        if(date.getMonthValue() != this.currentDate.getMonthValue())
        {
            days.add(null);
        }
        else
        {
            DayBean dayBean = new DayBean(date);
            days.add(dayBean);
        }
        
        this.days = days;
    }
}
