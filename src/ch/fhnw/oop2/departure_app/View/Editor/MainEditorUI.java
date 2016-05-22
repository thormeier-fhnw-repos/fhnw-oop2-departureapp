package ch.fhnw.oop2.departure_app.View.Editor;

import ch.fhnw.oop2.departure_app.Model.Entity.TrainRide;
import ch.fhnw.oop2.departure_app.Service.Event.EventExecutionContext;
import ch.fhnw.oop2.departure_app.Service.Event.IEventListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import java.util.HashMap;

/**
 * Basic UI class where everything else builds upon
 */
public class MainEditorUI extends BorderPane implements IEventListener
{
    /**
     * List of events to fire
     */
    private HashMap<String, EventExecutionContext> events = new HashMap<>();

    /**
     * Array list of TrainRides from the model
     */
    private ObservableList<TrainRide> rides;

    /**
     * Top navigation
     */
    private Navigation navigation;

    /**
     * Table containing all TrainRides
     */
    private TrainRideTableView ridesList;

    /**
     * Split pane, contains RdiesList and editor interface
     */
    private SplitPane mainPane;

    /**
     * Editor interface
     */
    private Editor editor;

    /**
     * Basic UI class where everything else builds upon
     */
    public MainEditorUI(ObservableList<TrainRide> rides)
    {
        this.rides = rides;
        initializeControls();
        layoutControls();
    }

    /**
     * Initializes the controls, i.e. Navigatio, TrainRideTableView and EditInterface
     */
    private void initializeControls()
    {
        navigation = new Navigation();
        editor = new Editor();
        ridesList = new TrainRideTableView(rides, editor);
        mainPane = new SplitPane();
        mainPane.getStyleClass().addAll("white-background");
    }

    /**
     * Adds the Controls to the layout
     */
    private void layoutControls()
    {
        mainPane.getItems().addAll(ridesList, editor);

        setTop(navigation);
        setCenter(mainPane);
    }

    /**
     * Toggles the redo buttons enabled state
     * @param enabled true = enabled, false = disable
     */
    public void toggleUndo(boolean enabled)
    {
        navigation.toggleUndo(enabled);
    }

    /**
     * Toggles the redo buttons enabled state
     * @param enabled true = enabled, false = disable
     */
    public void toggleRedo(boolean enabled)
    {
        navigation.toggleRedo(enabled);
    }

    /**
     * Toggles the buttons for the score board
     * @param enabled true = enabled, false = disable
     */
    public void toggleScoreBoard(boolean enabled)
    {
        navigation.toggleUpload(enabled);
        navigation.togglePlay(enabled);
        navigation.togglePause(enabled);
    }

    /**
     * Search for a given string
     * @param searchString String to search for
     */
    public void search(String searchString)
    {
        ObservableList<TrainRide> subentries = FXCollections.observableArrayList();

        rides.forEach(o -> {
            if (o.matches(searchString)) {
                subentries.add(o);
            }
        });

        ridesList.setItems(subentries);
    }

    @Override
    public void on(String eventName, EventExecutionContext event)
    {
        navigation.on(eventName, event);
        editor.on(eventName, event);
    }

    @Override
    public EventExecutionContext getEvent(String eventName)
    {
        return events.get(eventName);
    }
}
