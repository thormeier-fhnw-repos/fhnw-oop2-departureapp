package ch.fhnw.oop2.departure_app.Model.Repository;

import ch.fhnw.oop2.departure_app.Model.Entity.Departure;
import ch.fhnw.oop2.departure_app.Model.Entity.TrainRide;
import ch.fhnw.oop2.departure_app.Model.Factory.TrainRideFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Reads a CSV file and creates a list of TrainRides
 */
public class StreamRepository
{
    /**
     * Create an ArrayList of TrainRides
     * @return Observable list of TrainRides from inputStream
     */
    public ObservableList<TrainRide> getTrainRides(Stream<String> inputStream)
    {
        ObservableList<TrainRide> list = FXCollections.observableArrayList();
        list.addAll(inputStream.skip(1)
            .collect(Collectors.toCollection(ArrayList::new))
            .stream()
            .map(TrainRideFactory::fromString)
            .collect(Collectors.toCollection(ArrayList::new)));

        return list;
    }

    /**
     * Persists a list of TrainRides
     * @param outputStream Output stream to desired file
     * @param rides        List of rides to persist
     */
    public void persist(FileOutputStream outputStream, ObservableList<TrainRide> rides)
    {
        String content = "#id;Uhrzeit;Zugnummer;in Richtung;über;Gleis\n";

        for (TrainRide ride : rides) {
            content += trainRideToCsvLine(ride) + "\n";
        }

        byte[] contentInBytes = content.getBytes();

        try {
            outputStream.write(contentInBytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Converts a TrainRide to CSV line
     * @param ride TrainRide to convert
     * @return CSV representation of this ride
     */
    private String trainRideToCsvLine(TrainRide ride)
    {
        return
            Integer.toString(ride.getId()) + ";"
            + ride.getDepartureTimeAsString() + ";"
            + ride.getTrainId() + ";"
            + ride.getFinalDestinationPlace() + ";"
            + departureToCsvLine(ride.getStopovers()) + ";"
            + Integer.toString(ride.getTrack())
        ;
    }

    /**
     * Reduces a list of Departures to a single string
     * @param departures List of departures
     * @return CSV representation of this list, empty ones filtered out
     */
    private String departureToCsvLine(ObservableList<Departure> departures)
    {
        return departures.stream()
            .filter(d -> d.getDestination().trim().length() > 0)
            .map(Departure::toString)
            .collect(Collectors.joining(" - "));
    }
}
