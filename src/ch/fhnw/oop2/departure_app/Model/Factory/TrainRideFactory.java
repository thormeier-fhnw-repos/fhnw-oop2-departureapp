package ch.fhnw.oop2.departure_app.Model.Factory;

import ch.fhnw.oop2.departure_app.Model.Entity.TrainRide;
import java.util.ArrayList;

/**
 * Factory class for TrainRides
 */
public class TrainRideFactory
{
    /**
     * Genereates a Repository from string
     * @param input String
     * @return TrainRide from String
     */
    public static TrainRide fromString(String input)
    {
        // [0] ID
        // [1] Departure time
        // [2] Train ID
        // [3] Departure place
        // [4] List of stopovers
        // [5] Track
        String[] splittedInput = input.split(";");

        TrainRide ride = new TrainRide(
            Integer.parseInt(splittedInput[0]),
            DepartureFactory.fromString(splittedInput[3] + "  " + splittedInput[1]),
            new ArrayList<>(),
            splittedInput[2],
            Integer.parseInt(splittedInput[5])
        );

        if (splittedInput[4].length() > 0) {
            String[] rawStopovers = splittedInput[4].split(" - ");
            for (String rawStopover : rawStopovers) {
                ride.addStopover(DepartureFactory.fromString(rawStopover));
            }
        }

        return ride;
    }

    /**
     * Creates an empty TrainRide, only needs an ID
     * @param id ID must be given
     * @return Empty TrainRide with only an ID set
     */
    public static TrainRide fromNullWithId(int id)
    {
        return new TrainRide(
            id,
            DepartureFactory.fromNull(),
            new ArrayList<>(),
            "",
            -1
        );
    }
}
