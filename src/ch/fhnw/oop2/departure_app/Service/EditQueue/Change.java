package ch.fhnw.oop2.departure_app.Service.EditQueue;

import java.beans.Statement;
import java.util.Objects;

/**
 * Represents a change
 */
public class Change<T, V>
{
    /**
     * Object on which the change occured
     */
    private T object;

    /**
     * The old value
     */
    private V oldValue;

    /**
     * The new value
     */
    private V newValue;

    /**
     * Property that changed
     */
    private String property;

    /**
     *
     * @param object   Object on which the change happened
     * @param oldValue Value before change
     * @param newValue Value after change
     * @param property Property that has changed
     */
    public Change(T object, V oldValue, V newValue, String property)
    {
        this.object = object;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.property = property;
    }

    /**
     * Undoes a change
     */
    public void undo()
    {
        invokeChange(oldValue);
    }

    /**
     * Redoes a change
     */
    public void redo()
    {
        invokeChange(newValue);
    }

    /**
     * Compares a change
     * @param change Change that should be checked
     * @return true = is inverse operation, false = is not inverse operation
     */
    public boolean isInverse(Change change)
    {
        return  Objects.equals(change.getProperty(), property)
                && Objects.equals(change.getOldValue(), newValue)
                && Objects.equals(change.getNewValue(), oldValue);
    }

    /**
     * Compares a change
     * @param change Change
     * @return true = is same operation, false is not same operation
     */
    boolean isForward(Change change)
    {
        return  Objects.equals(change.getProperty(), property)
                && Objects.equals(change.getOldValue(), oldValue)
                && Objects.equals(change.getNewValue(), newValue);
    }

    /**
     * Get the property on which the change occurred
     * @return Property on which the change happened
     */
    private String getProperty()
    {
        return property;
    }

    /**
     * Get the old value of the change
     * @return Old value
     */
    V getOldValue()
    {
        return oldValue;
    }

    /**
     * Get the new value of the change
     * @return New value
     */
    V getNewValue()
    {
        return newValue;
    }

    /**
     * Get the object on which the change occurred
     * @return Object on which the change happened
     */
    T getObject()
    {
        return object;
    }

    /**
     * Executes the change with a given value
     * @param value Value to apply
     */
    private void invokeChange(V value)
    {
        String methodName = "set" + Character.toUpperCase(property.charAt(0)) + property.substring(1);

        try {
            Statement statement = new Statement(object, methodName, new Object[]{value});
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
