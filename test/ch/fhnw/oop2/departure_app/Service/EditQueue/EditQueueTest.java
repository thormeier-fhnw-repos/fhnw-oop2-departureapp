package ch.fhnw.oop2.departure_app.Service.EditQueue;

import javafx.beans.property.SimpleStringProperty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the EditQueue
 */
public class EditQueueTest
{
    /**
     * Basic value object to test with
     */
    private SimpleStringProperty obj;

    @Before
    public void setUp()
    {
        obj = new SimpleStringProperty();
        obj.setValue("a");
    }

    @Test
    public void testAddChangeUndoRedo()
    {
        EditQueue queue = new EditQueue();

        String oldValue = obj.getValue();
        String newValue = obj.getValue() + "b";

        queue.addChange(new Change<>(obj, oldValue, newValue, "value"));

        obj.setValue(newValue);

        Assert.assertTrue(queue.undoPossible());
        Assert.assertFalse(queue.redoPossible());

        queue.undo();

        Assert.assertEquals(oldValue, obj.getValue());

        Assert.assertFalse(queue.undoPossible());
        Assert.assertTrue(queue.redoPossible());

        queue.redo();

        Assert.assertEquals(newValue, obj.getValue());

        Assert.assertTrue(queue.undoPossible());
        Assert.assertFalse(queue.redoPossible());
    }

    @Test
    public void testIsUndoIsRedo()
    {
        EditQueue queue = new EditQueue();

        String oldValue = obj.getValue();
        String newValue = obj.getValue() + "b";

        Change change = new Change<>(obj, oldValue, newValue, "value");
        Change inverseChange = new Change<>(obj, newValue, oldValue, "value");

        queue.addChange(change);

        obj.setValue(newValue);

        Assert.assertTrue(queue.isUndo(inverseChange));
        Assert.assertTrue(queue.isRedo(change));
    }

    @Test
    public void testCutQueue()
    {
        EditQueue queue = new EditQueue();

        String value1 = obj.getValue();
        String value2 = obj.getValue() + "b";
        String value3 = obj.getValue() + "c";
        String value4 = obj.getValue() + "d";

        Change change1 = new Change<>(obj, value1, value2, "value");
        Change change2 = new Change<>(obj, value2, value3, "value");
        Change change3 = new Change<>(obj, value1, value4, "value");

        queue.addChange(change1);
        queue.addChange(change2);

        Assert.assertFalse(queue.redoPossible());

        obj.setValue(value3);

        queue.undo();
        Assert.assertEquals(value2, obj.getValue());

        queue.undo();
        Assert.assertEquals(value1, obj.getValue());

        queue.addChange(change3);
        obj.setValue(value4);
        Assert.assertFalse(queue.redoPossible());

        queue.undo();
        Assert.assertTrue(queue.redoPossible());
        Assert.assertFalse(queue.undoPossible());
        Assert.assertEquals(value1, obj.getValue());

        queue.redo();
        Assert.assertFalse(queue.redoPossible());
        Assert.assertTrue(queue.undoPossible());
        Assert.assertEquals(value4, obj.getValue());
    }
}
