package ch.fhnw.oop2.departure_app.Service.EditQueue;

import javafx.beans.property.SimpleStringProperty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests a change
 */
public class ChangeTest
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
    public void testUndo()
    {
        String oldValue = obj.getValue();
        String newValue = obj.getValue() + "b";

        Change<SimpleStringProperty, String> change = new Change<>(obj, oldValue, newValue, "value");

        obj.setValue(newValue);

        Assert.assertEquals(newValue, obj.getValue());

        change.undo();

        Assert.assertEquals(oldValue, obj.getValue());
    }

    @Test
    public void testRedo()
    {
        String oldValue = obj.getValue();
        String newValue = obj.getValue() + "b";

        Change<SimpleStringProperty, String> change = new Change<>(obj, oldValue, newValue, "value");

        Assert.assertEquals(oldValue, obj.getValue());

        change.redo();

        Assert.assertEquals(newValue, obj.getValue());
    }

    @Test
    public void testIsForward()
    {
        String oldValue = obj.getValue();
        String newValue = obj.getValue() + "b";
        String differentNewValue = obj.getValue() + "c";

        Change<SimpleStringProperty, String> change1 = new Change<>(obj, oldValue, newValue, "value");
        Change<SimpleStringProperty, String> change2 = new Change<>(obj, oldValue, newValue, "value");
        Change<SimpleStringProperty, String> change3 = new Change<>(obj, oldValue, differentNewValue, "value");

        Assert.assertTrue(change1.isForward(change1));
        Assert.assertTrue(change1.isForward(change2));
        Assert.assertFalse(change1.isForward(change3));
        Assert.assertFalse(change2.isForward(change3));
    }

    @Test
    public void testIsInverse()
    {
        String oldValue = obj.getValue();
        String newValue = obj.getValue() + "b";
        String differentNewValue = obj.getValue() + "c";

        Change<SimpleStringProperty, String> change1 = new Change<>(obj, oldValue, newValue, "value");
        Change<SimpleStringProperty, String> change2 = new Change<>(obj, oldValue, newValue, "value");
        Change<SimpleStringProperty, String> change3 = new Change<>(obj, oldValue, differentNewValue, "value");

        Change<SimpleStringProperty, String> change4 = new Change<>(obj, newValue, oldValue, "value");
        Change<SimpleStringProperty, String> change5 = new Change<>(obj, differentNewValue, oldValue, "value");

        Assert.assertFalse(change1.isInverse(change1));
        Assert.assertFalse(change1.isInverse(change2));
        Assert.assertFalse(change1.isInverse(change3));
        Assert.assertFalse(change2.isInverse(change3));
        Assert.assertTrue(change1.isInverse(change4));
        Assert.assertFalse(change1.isInverse(change5));
    }
}
