package ch.fhnw.oop2.departure_app.Model.Entity;

import org.junit.Assert;
import org.junit.Test;
import java.time.LocalTime;

/**
 * Test class for Departure model class
 */
public class DepartureTest
{
    @Test
    public void testSetDestination()
    {
        String lenzburg = "Lenzburg";
        String aarau = "Aarau";

        Departure dep = new Departure(lenzburg, LocalTime.of(0, 1));

        Assert.assertEquals(lenzburg, dep.getDestination());

        dep.setDestination(aarau);

        Assert.assertEquals(aarau, dep.getDestination());
    }

    @Test
    public void testSetDepartureTime()
    {
        LocalTime eightthrity = LocalTime.of(8, 30);
        LocalTime ninetwenty = LocalTime.of(9, 20);

        Departure dep = new Departure("Lenzburg", eightthrity);

        Assert.assertEquals(eightthrity, dep.getDepartureTime());

        dep.setDepartureTime(ninetwenty);

        Assert.assertEquals(ninetwenty, dep.getDepartureTime());
    }

    @Test
    public void testToString()
    {
        Departure dep = new Departure("Lenzburg", LocalTime.of(8, 30));

        Assert.assertEquals("Lenzburg  08:30", dep.toString());
    }
}
