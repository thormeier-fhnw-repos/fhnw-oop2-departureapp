package ch.fhnw.oop2.departure_app.Service.EditQueue;

import java.util.ArrayList;

/**
 * Manages changes
 */
public class EditQueue
{
    /**
     * Queue of changes
     */
    private ArrayList<Change> changeQueue = new ArrayList<>();

    /**
     * Internal pointer inside the changeQueue
     */
    private int pointer = -1;

    /**
     * Adds a change to the queue
     * @param change Change to be added to the queue
     */
    public void addChange(Change change)
    {
        if (pointer < maxPointer()) {
            changeQueue = new ArrayList<>(changeQueue.subList(0, pointer > 0 ? pointer : 0));
        }

        pointer++;

        changeQueue.add(change);
    }

    /**
     * Undoes a change
     */
    public void undo()
    {
        if (pointer < 0) {
            return;
        }

        changeQueue.get(pointer).undo();

        pointer--;
    }

    /**
     * Redoes a change
     */
    public void redo()
    {
        if (pointer >= maxPointer()) {
            return;
        }

        pointer++;

        changeQueue.get(pointer).redo();
    }

    /**
     * Determines if an undo is possible
     * @return true = there's still undos possible, false = beginning of queue
     */
    public boolean undoPossible()
    {
        return pointer > -1 && maxPointer() >= 0;
    }

    /**
     * Determines if a redo is possible
     * @return true = there's still redos possible, false = end of queue
     */
    public boolean redoPossible()
    {
        return pointer < maxPointer();
    }

    /**
     * Determines if the current change is an undo
     * @param change Change to check
     * @return true = this change is the currently applied Undo
     */
    public boolean isUndo(Change change)
    {
        return pointer >= 0 && change.isInverse(changeQueue.get(pointer));
    }

    /**
     * Determines if the current change is a redo
     * @param change Change to check
     * @return true = this change is the currently applied Undo
     */
    public boolean isRedo(Change change)
    {
        return pointer <= maxPointer() && pointer > -1 && change.isForward(changeQueue.get(pointer));
    }

    /**
     * Returns the maximum position fo the pointer
     * @return Maximum value of the pointer
     */
    private int maxPointer()
    {
        return changeQueue.size() - 1;
    }
}
