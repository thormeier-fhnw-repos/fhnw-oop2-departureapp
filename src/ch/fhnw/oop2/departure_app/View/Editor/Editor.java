package ch.fhnw.oop2.departure_app.View.Editor;

import ch.fhnw.oop2.departure_app.Model.Entity.Departure;
import ch.fhnw.oop2.departure_app.Model.Entity.TrainRide;
import ch.fhnw.oop2.departure_app.Service.EditQueue.Change;
import ch.fhnw.oop2.departure_app.Service.EditQueue.Select;
import ch.fhnw.oop2.departure_app.Service.Event.EventExecutionContext;
import ch.fhnw.oop2.departure_app.Service.Event.IEventListener;
import ch.fhnw.oop2.departure_app.Service.Event.UnknownEventException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Editor ScrollPane UI: Contains form for editing a single TrainRide
 */
class Editor extends ScrollPane implements IEventListener
{
    /**
     * List of events to fire
     */
    private HashMap<String, EventExecutionContext<Change>> events = new HashMap<>();

    /**
     * Complete form for a Departure
     */
    private DepartureBox finalDestination;

    /**
     * Train ID
     */
    private TextField trainIdField = new TextField();

    /**
     * Track
     */
    private TextField trackField = new TextField();

    /**
     * Button to add new stopovers
     */
    private Button addStopoverButton = new Button("Zwischenhalt hinzufügen");

    /**
     * Array of stopover form boxes
     */
    private ArrayList<DepartureBox> stopoverForms = new ArrayList<>();

    /**
     * VBox that contains all controls
     */
    private VBox contentBox = new VBox();

    /**
     * The currently display TrainRide
     */
    private TrainRide currentRide;

    /**
     * Event which is triggered when change on a DepartureBox happened
     */
    private EventExecutionContext departureBoxEventExecutionContext;

    /**
     * Constructor, sets up the whole editor form
     */
    Editor()
    {
        initializeControls();
        layoutControls();
        addEventHandlers();
    }

    /**
     * Display a TrainRide in the editor form
     * @param ride TrainRide to display
     */
    void display(TrainRide ride)
    {
        if (null != currentRide) {
            trackField.textProperty().unbindBidirectional(currentRide.getTrackProperty());
            trainIdField.textProperty().unbindBidirectional(currentRide.getTrainIdProperty());
        }

        if (null != ride) {
            trackField.setText(Integer.toString(ride.getTrack()));
            trackField.textProperty().bindBidirectional(ride.getTrackProperty(), new NumberStringConverter());

            trainIdField.setText(ride.getTrainId());
            trainIdField.textProperty().bindBidirectional(ride.getTrainIdProperty());

            finalDestination.setDeparture(ride.getFinalDestination());

            contentBox.getChildren().removeAll(stopoverForms);
            stopoverForms.removeAll(stopoverForms);

            ride.getStopovers().forEach(this::addStopover);
        } else {
            trackField.setText("");
            trainIdField.setText("");
            finalDestination.setDeparture(null);
            contentBox.getChildren().removeAll(stopoverForms);
        }

        Select change = new Select<>(currentRide, ride);

        currentRide = ride;

        execute(IEventListener.EVENT_SELECT, change, "editor, display");
    }

    /**
     * Initializes the controls, i.e. the form of this editor
     */
    private void initializeControls()
    {
        contentBox.getStyleClass().addAll("editor", "white-background");
        contentBox.fillWidthProperty();
        VBox.setVgrow(this, Priority.ALWAYS);
        getStyleClass().addAll("editor-pane", "bordered-pane", "white-background");

        finalDestination = new DepartureBox();

        contentBox.getChildren().addAll(
            finalDestination,
            new Label("Zugnummer"),
            trainIdField,
            new Label("Gleis"),
            trackField,
            new Label("Zwischenhalte"),
            addStopoverButton
        );
    }

    /**
     * Adds the Controls to the layout
     */
    private void layoutControls()
    {
        setContent(contentBox);
        setFitToWidth(true);
        setFitToHeight(true);
    }

    /**
     * Add event handlers
     */
    private void addEventHandlers()
    {
        Editor self = this;

        departureBoxEventExecutionContext = (EventExecutionContext<Change>) (payload, context) -> {
            if (null != currentRide) {
                self.execute(IEventListener.EVENT_CHANGE, payload, context);
            }
        };

        addStopoverButton.setOnAction(event -> addEmptyStopover());
        finalDestination.on(IEventListener.EVENT_CHANGE, departureBoxEventExecutionContext);
        trainIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (null != currentRide) {
                Change change = new Change<>(currentRide, oldValue, newValue, "trainId");
                self.execute(IEventListener.EVENT_CHANGE, change, "editor, train ID");
            }
        });
        trackField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (null != currentRide) {
                Change change = new Change<>(currentRide, Integer.parseInt(oldValue), Integer.parseInt(newValue), "track");
                self.execute(IEventListener.EVENT_CHANGE, change, "editor, track field");
            }
        });
    }

    /**
     * Add a stopOver form
     */
    private void addEmptyStopover()
    {
        DepartureBox departureBox = new DepartureBox(new Label(Integer.toString(1 + stopoverForms.size())));
        departureBox.on(IEventListener.EVENT_CHANGE, departureBoxEventExecutionContext);

        Departure newStopOver = new Departure("", LocalTime.of(0, 0));
        if (null != currentRide) {
            currentRide.addStopover(newStopOver);
        }

        departureBox.setDeparture(newStopOver);

        stopoverForms.add(departureBox);
        contentBox.getChildren().add(departureBox);
    }

    /**
     * Add a stopOver form from a Departure
     */
    private void addStopover(Departure stopover)
    {
        DepartureBox departureBox = new DepartureBox(new Label(Integer.toString(1 + stopoverForms.size())));
        departureBox.on(IEventListener.EVENT_CHANGE, departureBoxEventExecutionContext);

        stopoverForms.add(departureBox);
        departureBox.setDeparture(stopover);

        contentBox.getChildren().add(departureBox);
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
     * Executes a given event
     * @param eventName Name of the event to execute
     * @param payload   Change object as event payload
     * @param context   Context in which the event was executed, mainly debugging purpose
     */
    private void execute(String eventName, Change payload, String context)
    {
        if (!events.containsKey(eventName)) {
            throw new UnknownEventException(eventName + ", " + getClass());
        }

        events.get(eventName).execute(payload, context);
    }
}
