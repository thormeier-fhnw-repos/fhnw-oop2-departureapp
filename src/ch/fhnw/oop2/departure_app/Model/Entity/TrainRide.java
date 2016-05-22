package ch.fhnw.oop2.departure_app.Model.Entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

/**
 * Basic model for train rides
 */
public class TrainRide
{
    /**
     * ID of this trainride
     */
    private SimpleIntegerProperty id;

    /**
     * Final destination as Departure
     */
    private SimpleObjectProperty<Departure> finalDestination;

    /**
     * A list of Departures that are stopovers of this train
     */
    private ObservableList<Departure> stopovers = FXCollections.observableArrayList();

    /**
     * Train ID, i.e. ICE-1234
     */
    private SimpleStringProperty trainId;

    /**
     * Track ID, may be negative
     */
    private SimpleIntegerProperty track;

    /**
     *
     * @param id               ID of the train ride, immutable
     * @param finalDestination Final destination
     * @param stopovers        List of stop overs as own departures
     * @param trainId          ID of the train, i.e. ICE-1234
     * @param track            Track ID, may be negative
     */
    public TrainRide(int id, Departure finalDestination, ArrayList<Departure> stopovers, String trainId, int track)
    {
        this.id = new SimpleIntegerProperty(id);
        this.finalDestination = new SimpleObjectProperty<>(finalDestination);
        this.stopovers.addAll(stopovers);
        this.trainId = new SimpleStringProperty(trainId);
        this.track = new SimpleIntegerProperty(track);
    }

    /**
     * Get the ID of this train ride
     * @return ID (!= Train ID)
     */
    public int getId()
    {
        return id.getValue();
    }

    /**
     * Get the final destination
     * @return The final destination as a Departure
     */
    public Departure getFinalDestination()
    {
        return finalDestination.getValue();
    }

    /**
     * Set the final destination
     * @param finalDestination Final destnation
     */
    public void setFinalDestination(Departure finalDestination)
    {
        this.finalDestination = new SimpleObjectProperty<>(finalDestination);
    }

    /**
     * Add a Departure to the stopovers
     * @param stopover A new stopover
     */
    public void addStopover(Departure stopover)
    {
        stopovers.add(stopover);
    }

    /**
     * Get an ArrayList of Departures that represent stopovers
     * @return List of departures
     */
    public ObservableList<Departure> getStopovers()
    {
        return stopovers;
    }

    /**
     * Get the Train ID
     * @return Train ID (!= ID)
     */
    public String getTrainId()
    {
        return trainId.getValue();
    }

    /**
     * Get the Train ID as SimpleStringPeroperty
     * @return Property of the Train ID
     */
    public SimpleStringProperty getTrainIdProperty()
    {
        return trainId;
    }

    /**
     * Set a new Train ID
     * @param trainId The new train ID
     */
    public void setTrainId(String trainId)
    {
        this.trainId.set(trainId);
    }

    /**
     * Get the track of this train ride
     * @return Track
     */
    public int getTrack()
    {
        return track.getValue();
    }

    /**
     * Get the track of this train ride as SimpleIntegerProperty
     * @return Property of the Track
     */
    public SimpleIntegerProperty getTrackProperty()
    {
        return track;
    }

    /**
     * Set the track of this train ride
     * @param track Track
     */
    public void setTrack(int track)
    {
        this.track.set(track);
    }

    /**
     * Get the departure time of this TrainRide
     * @return Departure time as a String
     */
    public String getDepartureTimeAsString()
    {
        return finalDestination.getValue().getDepartureTime().toString();
    }

    /**
     * Get the final destination's place string
     * @return Final destnation place as a String
     */
    public String getFinalDestinationPlace()
    {
        return finalDestination.getValue().getDestination();
    }

    /**
     * Returns a String representation of this Repository
     * @return A string representation of this TrainRide
     */
    public String toString()
    {
        String output = getId() + ": " + getTrainId() + ", " + getTrack() + " - " + getFinalDestination().toString() + ", via: ";

        for (Departure stopover : getStopovers()) {
            output += stopover.toString() + " ";
        }

        return output;
    }

    /**
     * Determines if this TrainRide matches a given string
     * @param string Search string
     * @return true = matches the search string, false = doesn't match the search string
     */
    public boolean matches(String string)
    {
        final boolean[] matches = {0 == string.length()
                || getTrainId().contains(string)
                || getFinalDestinationPlace().contains(string)};

        stopovers.forEach(o -> matches[0] = matches[0] || o.getDestination().contains(string));

        return matches[0];
    }
}
