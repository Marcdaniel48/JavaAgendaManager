package com.marcdanieldialogo.entities;

import java.sql.Timestamp;
import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Timestamp does not map to any JavaFX control. LocalDate does map to the
 * DatePicker control. It is easy to convert from Timestamp to LocalDate.
 *
 * When you retrieve data from the database you call setTimeStampValue with the
 * Timestamp value and it will be placed into the LocalDate property. When you
 * need to retrieve the TimeStamp you call on getTimeStampValue that converts
 * the LocalDate into a TimeStamp
 *
 * @author Ken Fogel
 * @version 1.0
 * @date 2017-06-26
 */
public class TimestampBean {

    // This is the property we will bind
    ObjectProperty<LocalDate> dateField;

    public TimestampBean() {
        // Initialize to the current date
        dateField = new SimpleObjectProperty<>(LocalDate.now());
    }

    /**
     * There is no Timestamp variable. Instead we convert the LocalDate to a
     * Timestamp and return that.
     *
     * @return
     */
    public Timestamp getTimeStampValue() {
        // Timestamp can map to a LocalDateTime only so we need to add the time
        // portion to our Localdate with atStartOfDay
        return Timestamp.valueOf(dateField.get().atStartOfDay());
    }

    /**
     * There is no Timestamp variable. Instead we convert the Timestamp into a
     * LocalDate and assign that to the dateField property
     *
     * @param ts
     */
    public void setTimeStampValue(Timestamp ts) {
        // Timestamp uses toLocalDateTime to convert to LocalDateTime
        // Next we convert LocalDateTime to LocalDate with toLocaldate
        dateField.set(ts.toLocalDateTime().toLocalDate());
    }

    // The usual methods below
    public LocalDate getDateField() {
        return dateField.get();
    }

    public void setDateField(LocalDate value) {
        this.dateField.set(value);
    }

    public ObjectProperty<LocalDate> dateFieldProperty() {
        return dateField;
    }

}
