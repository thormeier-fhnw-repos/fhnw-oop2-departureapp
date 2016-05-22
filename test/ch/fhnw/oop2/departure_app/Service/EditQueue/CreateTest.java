package ch.fhnw.oop2.departure_app.Service.EditQueue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Create change
 */
public class CreateTest
{
    /**
     * Simple ArrayList to test against
     */
    private ObservableList<String> list;

    @Before
    public void setUp()
    {
        list = FXCollections.observableArrayList();
    }

    @Test
    public void testUndo()
    {
        String obj1 = "a";
        String obj2 = "b";
        String obj3 = "c";

        list.add(obj1);

        Create<String> create1 = new Create<>(list, obj1);
        Create<String> create2 = new Create<>(list, obj2);
        Create<String> create3 = new Create<>(list, obj3);

        Assert.assertEquals(1, list.size());
        create1.undo();
        Assert.assertEquals(0, list.size());

        list.add(obj2);
        list.add(obj3);

        Assert.assertEquals(2, list.size());

        create1.undo();

        Assert.assertEquals(2, list.size());

        create2.undo();

        Assert.assertEquals(1, list.size());

        create2.undo();

        Assert.assertEquals(1, list.size());

        create3.undo();

        Assert.assertEquals(0, list.size());
    }

    @Test
    public void testRedo()
    {
        String obj = "a";

        Create<String> create = new Create<>(list, obj);

        Assert.assertEquals(0, list.size());

        create.redo();

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(obj, list.get(0));
    }
}
