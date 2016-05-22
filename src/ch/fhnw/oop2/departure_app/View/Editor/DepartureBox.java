package ch.fhnw.oop2.departure_app.View.Editor;

import ch.fhnw.oop2.departure_app.Model.Entity.Departure;
import ch.fhnw.oop2.departure_app.Service.EditQueue.Change;
import ch.fhnw.oop2.departure_app.Service.Event.EventExecutionContext;
import ch.fhnw.oop2.departure_app.Service.Event.IEventListener;
import ch.fhnw.oop2.departure_app.Service.Event.UnknownEventException;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalTimeStringConverter;
import java.util.HashMap;

/**
 * VBox for a Departure
 */
class DepartureBox extends VBox implements IEventListener
{
    /**
     * List of events to fire
     */
    private HashMap<String, EventExecutionContext> events = new HashMap<>();

    /**
     * Text field for departure time
     */
    private TextField departureTimeField = new TextField();

    /**
     * Text field for departure destination
     */
    private TextField destinationField = new TextField();

    /**
     * Currently editing departure
     */
    private Departure currentDeparture;

    /**
     * Basic constructor, adds all necessary text fields
     */
    DepartureBox()
    {
        layoutControls();
        addEventHandlers();
    }

    /**
     * Adds all necessary text fields with a custom label above the whole form
     * @param label Label of this departure box
     */
    DepartureBox(Label label)
    {
        label.getStyleClass().add("stopover-label");
        getStyleClass().addAll("bordered-pane", "white-background");
        getChildren().add(label);
        layoutControls();
        addEventHandlers();
    }

    /**
     * Adds the Controls to the layout
     */
    private void layoutControls()
    {
        getChildren().addAll(
            new Label("Abfahrt"),
            departureTimeField,
            new Label("Bahnhof"),
            destinationField
        );
    }

    /**
     * Attaches event handlers to this departure box
     */
    private void addEventHandlers()
    {
        DepartureBox self = this;

        departureTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (null != currentDeparture) {
                Change<Departure, String> change = new Change<>(currentDeparture, oldValue, newValue, "departureTime");
                self.execute(IEventListener.EVENT_CHANGE, change, "depbox");
            }
        });

        destinationField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (null != currentDeparture) {
                Change<Departure, String> change = new Change<>(currentDeparture, oldValue, newValue, "destination");
                self.execute(IEventListener.EVENT_CHANGE, change, "depbox");
            }
        });
    }

    /**
     * Unbind previous departure, bind new one and set texts
     * @param departure Departure to display
     */
    public void setDeparture(Departure departure)
    {
        if (null != currentDeparture) {
            departureTimeField.textProperty().unbindBidirectional(currentDeparture.getDepartureTimeProperty());
            destinationField.textProperty().unbindBidirectional(currentDeparture.getDestinationProperty());
        }

        if (null != departure) {
            departureTimeField.setText(departure.getDepartureTime().toString());
            departureTimeField.textProperty().bindBidirectional(departure.getDepartureTimeProperty(), new LocalTimeStringConverter());

            destinationField.setText(departure.getDestination());
            destinationField.textProperty().bindBidirectional(departure.getDestinationProperty());
        } else {
            departureTimeField.setText("");
            destinationField.setText("");
        }
        currentDeparture = departure;
    }

    @Override
    public void on(String eventName, EventExecutionContext event)
    {
        events.put(eventName, event);
    }

    @Override
    public EventExecutionContext getEvent(String eventName) {
        return events.get(eventName);
    }

    /**
     * Execute a given event
     * @param eventName     Name of the event to execute
     * @param changePayload A Change object as event payload
     * @param context       Context in which the event was executed, mainly debugging purpose
     */
    private void execute(String eventName, Change<Departure, String> changePayload, String context)
    {
        if (!events.containsKey(eventName)) {
            throw new UnknownEventException(eventName + ", " + getClass());
        }

        events.get(eventName).execute(changePayload, context);
    }
}
