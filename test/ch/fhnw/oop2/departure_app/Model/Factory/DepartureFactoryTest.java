package ch.fhnw.oop2.departure_app.Model.Factory;

import ch.fhnw.oop2.departure_app.Model.Entity.Departure;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalTime;

/**
 * Test class for departure factory
 */
public class DepartureFactoryTest
{
    @Test
    public void testFromString()
    {
        Departure expectedDeparture = new Departure("foo", LocalTime.of(1, 0));

        Departure actualDeparture = DepartureFactory.fromString("foo  01:00");

        Assert.assertEquals(expectedDeparture.toString(), actualDeparture.toString());
    }

    @Test
    public void testFromNull()
    {
        Departure expectedDeparture = new Departure("", LocalTime.of(0, 0));

        Departure actualDeparture = DepartureFactory.fromNull();

        Assert.assertEquals(expectedDeparture.toString(), actualDeparture.toString());
    }
}
