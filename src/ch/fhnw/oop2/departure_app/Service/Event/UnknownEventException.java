package ch.fhnw.oop2.departure_app.Service.Event;

/**
 * Convenience exception to be thrown when an unknown event occurs
 */
public class UnknownEventException  extends IllegalArgumentException
{
    public UnknownEventException(String eventName)
    {
        super("Unknown event: " + eventName);
    }
}
