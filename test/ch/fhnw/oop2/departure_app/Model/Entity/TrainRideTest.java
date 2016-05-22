package ch.fhnw.oop2.departure_app.Model.Entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Test class for Repository model class
 */
public class TrainRideTest
{
    private TrainRide ride;

    @Before
    public void setUp()
    {
        Departure finalDestination = new Departure("foo", LocalTime.now());
        ArrayList<Departure> stopovers = new ArrayList<>();

        ride = new TrainRide(1, finalDestination, stopovers, "ice1", 1);
    }

    @Test
    public void testId()
    {
        Assert.assertEquals(1, ride.getId());
    }

    @Test
    public void testFinalDestnation()
    {
        Departure newFinalDestnation = new Departure("bar", LocalTime.now());

        Assert.assertEquals("foo", ride.getFinalDestination().getDestination());

        ride.setFinalDestination(newFinalDestnation);

        Assert.assertEquals("bar", ride.getFinalDestination().getDestination());
    }

    @Test
    public void testStopovers()
    {
        Departure firstStopOver = new Departure("foo1", LocalTime.now());
        Departure secondStopOver = new Departure("foo2", LocalTime.now());
        Departure[] stopovers = new Departure[2];
        stopovers[0] = firstStopOver;
        stopovers[1] = secondStopOver;

        Assert.assertEquals(0, ride.getStopovers().size());

        ride.addStopover(firstStopOver);

        Assert.assertEquals(1, ride.getStopovers().size());

        ride.addStopover(secondStopOver);

        Assert.assertEquals(2, ride.getStopovers().size());
        Assert.assertArrayEquals(stopovers, ride.getStopovers().toArray());
    }

    @Test
    public void testTrainId()
    {
        Assert.assertEquals("ice1", ride.getTrainId());

        ride.setTrainId("ice2");

        Assert.assertEquals("ice2", ride.getTrainId());
    }

    @Test
    public void testTrack()
    {
        Assert.assertEquals(1, ride.getTrack());

        ride.setTrack(2);

        Assert.assertEquals(2, ride.getTrack());

        ride.setTrack(-1);

        Assert.assertEquals(-1, ride.getTrack());
    }
}
