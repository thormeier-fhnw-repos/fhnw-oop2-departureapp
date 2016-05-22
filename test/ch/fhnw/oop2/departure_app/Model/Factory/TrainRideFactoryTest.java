package ch.fhnw.oop2.departure_app.Model.Factory;

import ch.fhnw.oop2.departure_app.Model.Entity.Departure;
import ch.fhnw.oop2.departure_app.Model.Entity.TrainRide;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Test class for Repository factory
 */
public class TrainRideFactoryTest
{
    @Test
    public void testFromString()
    {
        ArrayList<Departure> stopovers = new ArrayList<>();
        stopovers.add(new Departure("Olten", LocalTime.of(1, 0, 0)));

        TrainRide expectedRide = new TrainRide(0, new Departure("Zürich HB", LocalTime.of(0, 0, 0)), stopovers, "IC 747", -1);

        TrainRide actualRide = TrainRideFactory.fromString("0;00:00;IC 747;Zürich HB;Olten  01:00;-1;");

        Assert.assertEquals(expectedRide.toString(), actualRide.toString());
    }

    @Test
    public void testFromNull()
    {
        TrainRide expectedRide = new TrainRide(0, new Departure("", LocalTime.of(0, 0)), new ArrayList<>(), "", -1);

        TrainRide actualRide = TrainRideFactory.fromNullWithId(0);

        Assert.assertEquals(expectedRide.toString(), actualRide.toString());
    }
}
