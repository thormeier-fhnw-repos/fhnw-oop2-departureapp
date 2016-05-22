package ch.fhnw.oop2.departure_app.Model.Factory;

import ch.fhnw.oop2.departure_app.Model.Entity.Departure;
import java.time.LocalTime;

/**
 * Factory class for departures
 */
class DepartureFactory
{
    /**
     * Generates a Departurefrom string
     * @param input Input string
     * @return Departure from string
     */
    static Departure fromString(String input)
    {
        // [0] Place
        // [1] Time
        String[] splittedDeparture = input.split("  ");
        try {
            String[] splittedTime = splittedDeparture[1].split(":");

            int hour = Integer.parseInt(splittedTime[0]);
            int minute = Integer.parseInt(splittedTime[1]);

            return new Departure(splittedDeparture[0], LocalTime.of(hour, minute));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Creates an empty departure
     * @return Empty departure
     */
    static Departure fromNull()
    {
        return new Departure(
            "",
            LocalTime.of(0, 0)
        );
    }

}
