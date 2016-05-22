package ch.fhnw.oop2.departure_app.Service.Event;

/**
 * Event listener interface
 * Used to inject functionality from elsewhere
 * This follows the Seperation of Concerns principle, alloweing the
 * controller to define the logic executed when some UI event is triggered
 */
public interface IEventListener
{
    /**
     * List of events
     */
    String EVENT_SAVE = "esave";
    String EVENT_NEW = "enew";
    String EVENT_DELETE = "edelete";
    String EVENT_SELECT = "eselect";
    String EVENT_CHANGE = "echange";
    String EVENT_UNDO = "eundo";
    String EVENT_REDO = "eredo";
    String EVENT_SEARCH = "esearch";
    String EVENT_STARTSCOREBOARD = "estartscoreboard";
    String EVENT_QUITSCOREBOARD = "equitscoreboard";
    String EVENT_ANNOUNCE_DEPARTURE = "eanouncedeparture";
    String EVENT_DEPART = "edepart";
    String EVENT_UPLOAD = "eupload";

    /**
     * Add an event to the event list
     * @param eventName Name of the event
     * @param event     The actual event
     */
    void on(String eventName, EventExecutionContext event);

    /**
     * Get an event by its name
     * @param eventName Name of the event
     * @return event corresponding this name
     */
    EventExecutionContext getEvent(String eventName);
}
