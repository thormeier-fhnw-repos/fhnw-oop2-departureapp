package ch.fhnw.oop2.departure_app.Model.Entity;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalTime;

/**
 * Basic model for departure
 */
public class Departure
{
    /**
     * Destination String, i.e. Lenzburg, Brugg, Zurich, etc.
     */
    private SimpleStringProperty destination;

    /**
     * Date/time of departure, only time part will be used
     */
    private SimpleObjectProperty<LocalTime> departureTime;

    /**
     * @param destination   Destination name, i.e. Lenzburg, Brugg, Zurich, etc.
     * @param departureTime Date/time of departure, only time part will be used
     */
    public Departure(String destination, LocalTime departureTime)
    {
        this.destination = new SimpleStringProperty(destination);
        this.departureTime = new SimpleObjectProperty<>(departureTime);
    }

    /**
     * Get the destination
     * @return Destination as a String
     */
    public String getDestination()
    {
        return destination.getValue();
    }

    /**
     * Get this departures destination as SimpleStringProterty
     * @return Detsination property
     */
    public SimpleStringProperty getDestinationProperty()
    {
        return destination;
    }

    /**
     * Sets the destination
     * @param destination String New destination name
     */
    public void setDestination(String destination)
    {
        this.destination.set(destination);
    }

    /**
     * Get the departures datetime
     * @return Departure time
     */
    public LocalTime getDepartureTime()
    {
        return departureTime.getValue();
    }

    /**
     * Get this departures departure time as SimpleObjectProterty
     * @return Property of the departure time
     */
    public SimpleObjectProperty getDepartureTimeProperty()
    {
        return departureTime;
    }

    /**
     * Set a new departure datetime
     * @param departureTime New datetime of departure
     */
    public void setDepartureTime(LocalTime departureTime)
    {
        this.departureTime.set(departureTime);
    }

    /**
     * Returns this Departures String representation
     * @return String representation of this Departure
     */
    public String toString()
    {
        return  getDestination() + "  " + getDepartureTime().toString();
    }
}
