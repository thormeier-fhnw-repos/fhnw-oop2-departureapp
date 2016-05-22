package ch.fhnw.oop2.departure_app.Service.EditQueue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the Delete change
 */
public class DeleteTest
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

        list.addAll(obj1, obj2, obj3);

        Delete<String> delete1 = new Delete<>(list, obj1);
        Delete<String> delete2 = new Delete<>(list, obj2);
        Delete<String> delete3 = new Delete<>(list, obj3);

        list.clear();

        Assert.assertEquals(0, list.size());

        delete1.undo();
        Assert.assertEquals(1, list.size());

        delete2.undo();
        Assert.assertEquals(2, list.size());

        delete3.undo();
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void testRedo()
    {
        String obj = "a";

        Delete<String> delete = new Delete<>(list, obj);

        list.add(obj);
        Assert.assertEquals(1, list.size());

        delete.redo();

        Assert.assertEquals(0, list.size());
    }
}
