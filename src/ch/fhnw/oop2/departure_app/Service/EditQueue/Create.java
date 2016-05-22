package ch.fhnw.oop2.departure_app.Service.EditQueue;

import javafx.collections.ObservableList;

/**
 * Represents the creation of a new entity
 */
public class Create<T> extends Change<ObservableList<T>, T>
{
    /**
     * Represents a deletion
     * @param rides List of rides that have changed
     * @param ride  Newly created train ride
     */
    public Create(ObservableList<T> rides, T ride)
    {
        super(rides, null, ride, null);
    }

    @Override
    public void undo()
    {
        getObject().remove(getNewValue());
    }

    @Override
    public void redo()
    {
        getObject().add(getNewValue());
    }

    @Override
    public boolean isInverse(Change change)
    {
        return false;
    }
}
