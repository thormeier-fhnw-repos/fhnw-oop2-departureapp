package ch.fhnw.oop2.departure_app.View.Editor;

import ch.fhnw.oop2.departure_app.Service.Event.EventExecutionContext;
import ch.fhnw.oop2.departure_app.Service.Event.IEventListener;
import ch.fhnw.oop2.departure_app.Service.Event.UnknownEventException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import java.util.HashMap;

/**
 * Top navigation
 */
class Navigation extends BorderPane implements IEventListener
{
    /**
     * List of events to fire
     */
    private HashMap<String, EventExecutionContext> events = new HashMap<>();

    private NavigationButton saveButton;
    private NavigationButton plusButton;
    private NavigationButton timesButton;
    private NavigationButton undoButton;
    private NavigationButton redoButton;
    private NavigationButton trainButton;
    private NavigationButton uploadButton;
    private NavigationButton pauseButton;
    private NavigationButton playButton;
    private TextField searchField;
    private EventHandler<ActionEvent> undoEvent;
    private EventHandler<ActionEvent> redoEvent;
    private EventHandler<ActionEvent> uploadEvent;
    private EventHandler<ActionEvent> playEvent;
    private EventHandler<ActionEvent> pauseEvent;

    /**
     * Top navigation
     */
    Navigation()
    {
        getStyleClass().add("navigation");

        initializeControls();
        layoutControls();
        addEventListeners();
    }

    /**
     * Creates a HBox for the navigation
     * @param positioning Position, i.e. CENTER, CENTER_RIGHT etc.
     * @param elements    List of nodes to add to this box
     * @return HBox object
     */
    private HBox createHBox(Pos positioning, Node... elements)
    {
        HBox box = new HBox();
        box.setSpacing(5);
        box.setPadding(new Insets(5, 5, 5, 5));
        box.setAlignment(positioning);
        box.getChildren().addAll(elements);

        return box;
    }

    /**
     * Initializes the controls, i.e. Navigatio, TrainRideTableView and EditInterface
     */
    private void initializeControls()
    {
        saveButton = new NavigationButton(FontAwesomeIcon.SAVE);
        plusButton = new NavigationButton(FontAwesomeIcon.PLUS);
        timesButton = new NavigationButton(FontAwesomeIcon.TIMES);
        undoButton = new NavigationButton(FontAwesomeIcon.UNDO);
        redoButton = new NavigationButton(FontAwesomeIcon.REPEAT);
        trainButton = new NavigationButton(FontAwesomeIcon.TRAIN);
        uploadButton = new NavigationButton(FontAwesomeIcon.UPLOAD);
        pauseButton = new NavigationButton(FontAwesomeIcon.PAUSE);
        playButton = new NavigationButton(FontAwesomeIcon.PLAY);
        searchField = new TextField();
        searchField.getStyleClass().add("search-field");
    }

    /**
     * Adds the Controls to the layout
     */
    private void layoutControls()
    {
        HBox left = createHBox(Pos.CENTER_LEFT, saveButton, plusButton, timesButton, undoButton, redoButton);
        HBox center = createHBox(Pos.CENTER, trainButton, uploadButton, pauseButton, playButton);
        HBox right = createHBox(Pos.CENTER_RIGHT, searchField);

        setLeft(left);
        setCenter(center);
        setRight(right);
    }

    /**
     * Adds event listeners to the navigation
     */
    private void addEventListeners()
    {
        Navigation self = this;

        undoEvent = event -> self.execute(IEventListener.EVENT_UNDO, "", "navigation, undo");
        redoEvent = event -> self.execute(IEventListener.EVENT_REDO, "", "navigation, redo");
        uploadEvent = event -> self.execute(IEventListener.EVENT_UPLOAD, "", "navigation, upload");
        playEvent = event -> self.execute(IEventListener.EVENT_DEPART, "", "navigation, depart");
        pauseEvent = event -> self.execute(IEventListener.EVENT_ANNOUNCE_DEPARTURE, "", "navigation, announce departure");

        saveButton.setOnAction(event -> self.execute(IEventListener.EVENT_SAVE, "", "navigation, save"));
        plusButton.setOnAction(event -> self.execute(IEventListener.EVENT_NEW, "", "navigation, new"));
        timesButton.setOnAction(event -> self.execute(IEventListener.EVENT_DELETE, "", "navigation, delete"));
        undoButton.setOnAction(event -> self.execute(IEventListener.EVENT_UNDO, "", "navigation, undo"));
        redoButton.setOnAction(event -> self.execute(IEventListener.EVENT_REDO, "", "navigation, redo"));
        searchField.textProperty().addListener((observable, oldValue, newValue) -> self.execute(IEventListener.EVENT_SEARCH, newValue, "editor, track field"));
        trainButton.setOnAction(event -> self.execute(IEventListener.EVENT_STARTSCOREBOARD, "", "navigation, start scoreboard"));
    }

    /**
     * Toggles the undo buttons enabled state
     * @param enabled true = enabled, false = disable
     */
    void toggleUndo(boolean enabled)
    {
        if (enabled) {
            undoButton.enable();
            undoButton.setOnAction(undoEvent);
        } else {
            undoButton.disable();
            undoButton.setOnAction(null);
        }
    }

    /**
     * Toggles the redo buttons enabled state
     * @param enabled true = enabled, false = disable
     */
    void toggleRedo(boolean enabled)
    {
        if (enabled) {
            redoButton.enable();
            redoButton.setOnAction(redoEvent);
        } else {
            redoButton.disable();
            redoButton.setOnAction(null);
        }
    }

    /**
     * Toggles the upload button
     * @param enabled true = enabled, false = disable
     */
    void toggleUpload(boolean enabled)
    {
        if (enabled) {
            uploadButton.enable();
            uploadButton.setOnAction(uploadEvent);
        } else {
            uploadButton.disable();
            uploadButton.setOnAction(null);
        }
    }

    /**
     * Toggles the play button
     * @param enabled true = enabled, false = disable
     */
    void togglePlay(boolean enabled)
    {
        if (enabled) {
            playButton.enable();
            playButton.setOnAction(playEvent);
        } else {
            playButton.disable();
            playButton.setOnAction(null);
        }
    }

    /**
     * Toggles the pause button
     * @param enabled true = enabled, false = disable
     */
    void togglePause(boolean enabled)
    {
        if (enabled) {
            pauseButton.enable();
            pauseButton.setOnAction(pauseEvent);
        } else {
            pauseButton.disable();
            pauseButton.setOnAction(null);
        }
    }

    @Override
    public void on(String eventName, EventExecutionContext event)
    {
        this.events.put(eventName, event);
    }

    /**
     * Executes a given event
     * @param eventName Name of the event to execute
     * @param payload   Payload of the event as string
     * @param context   Context in which the event was executed, mainly debugging purpose
     */
    private void execute(String eventName, String payload, String context)
    {
        if (!events.containsKey(eventName)) {
            throw new UnknownEventException(eventName + ", " + getClass());
        }

        events.get(eventName).execute(payload, context);
    }

    @Override
    public EventExecutionContext getEvent(String eventName)
    {
        return this.events.get(eventName);
    }
}
