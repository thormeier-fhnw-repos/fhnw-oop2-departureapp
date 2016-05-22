package ch.fhnw.oop2.departure_app.Model.Repository;

import ch.fhnw.oop2.departure_app.Model.Entity.TrainRide;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Test class for the StreamTrainRepository class
 */
@RunWith(MockitoJUnitRunner.class)
public class TrainRideRepositoryTest
{
    @Mock
    Stream<String> inputStream;

    @Mock
    FileOutputStream outputStream;

    @Test
    public void testReadFromStream()
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("0;00:00;IC 747;Zürich HB;Olten  00:00 - Aarau  00:08 - Zürich HB  00:33;-1;");
        lines.add("1;00:04;IC 746;Bern;Olten  00:04 - Bern  00:31;-1;");

        Mockito.when(inputStream.skip(1))
            .thenReturn(inputStream);
        Mockito.when(inputStream.collect(Mockito.any()))
            .thenReturn(lines);

        StreamRepository repository = new StreamRepository();

        ObservableList<TrainRide> rides = repository.getTrainRides(inputStream);

        Assert.assertEquals(2, rides.size());
        Assert.assertEquals("IC 747", rides.get(0).getTrainId());
    }
}
