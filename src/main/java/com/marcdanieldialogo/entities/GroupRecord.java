package com.marcdanieldialogo.entities;

import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

/**
 * This class models a group record.
 * 
 * @author Marc-Daniel
 */
public class GroupRecord {
    private IntegerProperty group_number;
    private StringProperty group_name;
    private ColorBean colourBean;
    
    public GroupRecord(int group_number, String group_name, String colour)
    {
        this.group_number = new SimpleIntegerProperty(group_number);
        this.group_name = new SimpleStringProperty(group_name);
        this.colourBean = new ColorBean(colour);
    }

    public GroupRecord() {
        this(-1, "", "");
    }
    
    public int getGroupNumber()
    {
        return group_number.get();
    }
    
    public IntegerProperty groupNumberProperty()
    {
        return group_number;
    }
    
    public String getGroupName()
    {
        return group_name.get();
    }
    
    public StringProperty groupNameProperty()
    {
        return group_name;
    }
    
    public String getColour()
    {
        return colourBean.colorProperty().get().toString();
    }
    
    public ColorBean getColourBean()
    {
        return colourBean;
    }
    
    public ObjectProperty<Color> colourProperty()
    {
        return colourBean.colorProperty();
    }
    
    public void setGroupNumber(int group_number) {
        this.group_number.set(group_number);
    }
    
    public void setGroupName(String group_name)
    {
        this.group_name.set(group_name);
    }
    
    public void setColour(String colour)
    {
        this.colourBean.setColorHex(colour);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.group_number.get();
        hash = 37 * hash + Objects.hashCode(this.group_name.get());
        hash = 37 * hash + Objects.hashCode(this.colourBean.colorProperty().get().toString());
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final GroupRecord other = (GroupRecord) obj;
        
        if (this.group_number.get() != other.group_number.get()) {
            return false;
        }
        
        if (!Objects.equals(this.group_name.get(), other.group_name.get())) {
            return false;
        }
        
        if (!Objects.equals(this.colourBean.getColorHex(), other.colourBean.getColorHex())) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "GroupRecord{" + "group_number=" + group_number.get() + ", group_name=" + group_name.get() + ", colour=" + this.colourBean.getColorHex() + '}';
    }
}
