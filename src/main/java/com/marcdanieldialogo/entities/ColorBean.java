/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcdanieldialogo.entities;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author Marc-Daniel
 */
public class ColorBean {
    ObjectProperty<Color> color;
    
    public ColorBean(String hex)
    {
        try
        {
            this.color = new SimpleObjectProperty<>(Color.web(hex));
        }
        catch(IllegalArgumentException iae)
        {
            this.color = new SimpleObjectProperty<>(Color.BLACK);
        }
    }
    
    public ColorBean()
    {
        this.color = new SimpleObjectProperty<>(Color.BLACK);
    }
    
    public void setColorHex(String hex)
    {
        color.set(Color.web(hex));
    }
    
    public ObjectProperty<Color> colorProperty()
    {
        return color;
    }
    
    public String getColorHex()
    {
        return color.get().toString();
    }
}
