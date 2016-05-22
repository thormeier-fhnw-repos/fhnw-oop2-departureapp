package ch.fhnw.oop2.departure_app.Service.EditQueue;

/**
 * Represents a selection change
 */
public class Select<T> extends Change<T, T>
{
    /**
     * Constructor
     * @param oldEntity Entity before Select
     * @param newEntity Newly selected entity
     */
    public Select(T oldEntity, T newEntity)
    {
        super(null, oldEntity, newEntity, null);
    }

    @Override
    public void undo()
    {
        // noop
    }

    @Override
    public void redo()
    {
        // noop
    }

    @Override
    public boolean isInverse(Change change) {
        return false;
    }

    /**
     * Get the new entity
     * @return The currently selected entity
     */
    public T getNewEntity()
    {
        return getNewValue();
    }
}
