package ch.fhnw.oop2.departure_app.Controller;

import ch.fhnw.oop2.departure_app.Model.Entity.TrainRide;
import ch.fhnw.oop2.departure_app.Service.Event.EventExecutionContext;
import ch.fhnw.oop2.departure_app.Service.Event.IEventListener;
import ch.fhnw.oop2.departure_app.Service.Event.UnknownEventException;
import ch.fhnw.oop2.departure_app.Service.FileHelper;
import ch.fhnw.oop2.departure_app.View.ScoreBoard.MainScoreBoardUI;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;

/**
 * Manages the extra window with the split flip
 */
class ScoreBoardController implements IEventListener
{
    /**
     * List of events to fire
     */
    private HashMap<String, EventExecutionContext> events = new HashMap<>();

    /**
     * Main UI of the score board
     */
    private MainScoreBoardUI ui;

    /**
     * The stage of the score board
     */
    private Stage stage;

    /**
     * If the score board is being shown
     */
    private boolean isShowing = false;

    /**
     * Controller for the score board
     */
    ScoreBoardController()
    {
        stage = createStage();
        addEventHandlers(stage);
    }

    /**
     * Starts the score board
     */
    void start()
    {
        if (isShowing) {
            stage.close();
        }

        ui = new MainScoreBoardUI();
        Scene scene = createScene(ui);

        //ScenicView.show(scene); // Uncomment for debugging

        stage.setScene(scene);
        stage.show();

        attachEventHandlers();

        execute(IEventListener.EVENT_STARTSCOREBOARD, "", "scoreboardcontroller, start");

        isShowing = true;
    }

    /**
     * Attaches event handlers
     */
    void attachEventHandlers()
    {
        ScoreBoardController self = this;

        stage.setOnCloseRequest(event -> self.execute(IEventListener.EVENT_QUITSCOREBOARD, "", "scoreboardcontroller, quit"));
    }

    /**
     * Displays a TrainRide
     * @param ride TrainRide to display
     */
    void display(TrainRide ride)
    {
        if (isShowing) {
            ui.addTrainRide(ride);
        }
    }

    /**
     * Announces the departure of the first row
     */
    void announceDeparture()
    {
        if (isShowing) {
            ui.announceDeparture();
        }
    }

    /**
     * Lets the current row depart
     */
    void depart()
    {
        if (isShowing) {
            ui.depart();
        }
    }

    /**
     * Createa a scene object
     * @param ui Main UI for the score board
     * @return A Scene object
     */
    private Scene createScene(MainScoreBoardUI ui)
    {
        Scene scene = new Scene(ui, 1120, 600);
        scene.getStylesheets().add(FileHelper.getAbsolutePath("resources/assets/css/main.css"));
        scene.getStylesheets().add(FileHelper.getAbsolutePath("resources/assets/css/scoreboard.css"));
        ui.prefHeightProperty().bind(scene.heightProperty());
        ui.prefWidthProperty().bind(scene.widthProperty());
        scene.setCamera(new PerspectiveCamera(false));

        return scene;
    }
    /**
     * Createa a Stage
     * @return A Stage object
     */
    private Stage createStage()
    {
        Stage stage = new Stage();
        stage.setTitle("Departure App: Anzeigetafel");
        stage.setResizable(false);

        return stage;
    }

    /**
     * Attaches event handlers
     * @param stage Stage on which event handlers should be attached
     */
    private void addEventHandlers(Stage stage)
    {
        ScoreBoardController self = this;

        stage.setOnCloseRequest(event -> self.isShowing = false);
    }

    @Override
    public void on(String eventName, EventExecutionContext event)
    {
        this.events.put(eventName, event);
    }

    /**
     * Executes a given event
     * @param eventName Name of the event
     * @param payload   Payload of the event as a String
     * @param context   Context of the event, mainly debugging purpose
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
