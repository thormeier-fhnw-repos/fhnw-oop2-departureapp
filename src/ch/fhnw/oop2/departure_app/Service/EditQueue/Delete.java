package ch.fhnw.oop2.departure_app.Service.EditQueue;

import javafx.collections.ObservableList;

/**
 * Represents a deletion
 */
public class Delete<T> extends Change<ObservableList<T>, T>
{
    private int index;

    /**
     * Represents a deletion
     * @param rides List on which the change happened
     * @param ride  TrainRide that was deleted
     */
    public Delete(ObservableList<T> rides, T ride)
    {
        super(rides, ride, null, null);

        index = rides.indexOf(ride);
    }

    @Override
    public void undo()
    {
        getObject().add(index, getOldValue());
    }

    @Override
    public void redo()
    {
        getObject().remove(getOldValue());
    }

    @Override
    public boolean isInverse(Change change)
    {
        return false;
    }
}
