package ch.fhnw.oop2.departure_app.Service.Event;

/**
 * Interface for lambda expressions that should be executed on a given event
 */
public interface EventExecutionContext<T>
{
    /**
     * Executes this event
     * @param payload Payload that should be attached to the event that is being executed
     * @param context Context on which th eevent was executed, mainly debugging purpose
     */
    void execute(T payload, String context);
}
