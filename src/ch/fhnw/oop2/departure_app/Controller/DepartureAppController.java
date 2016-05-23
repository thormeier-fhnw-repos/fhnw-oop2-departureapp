package ch.fhnw.oop2.departure_app.Controller;

import ch.fhnw.oop2.departure_app.Model.Entity.TrainRide;
import ch.fhnw.oop2.departure_app.Model.Factory.TrainRideFactory;
import ch.fhnw.oop2.departure_app.Model.Repository.StreamRepository;
import ch.fhnw.oop2.departure_app.Service.EditQueue.*;
import ch.fhnw.oop2.departure_app.Service.Event.EventExecutionContext;
import ch.fhnw.oop2.departure_app.Service.Event.IEventListener;
import ch.fhnw.oop2.departure_app.Service.FileHelper;
import ch.fhnw.oop2.departure_app.View.Editor.MainEditorUI;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main controller app, brings view and model together
 */
public class DepartureAppController extends Application
{
    /**
     * File paths to static resources
     */
    private static String DATA_FILE_PATH = "resources/data/olten.csv";

    /**
     * The currently active train ride
     */
    private TrainRide currentRide = null;

    /**
     * List of rides
     */
    private ObservableList<TrainRide> rides;

    /**
     * Score board
     */
    private ScoreBoardController scoreBoardController = new ScoreBoardController();

    /**
     * Main method of program
     * @param args All app arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        StreamRepository trainRideRepository = new StreamRepository();

        rides = trainRideRepository.getTrainRides(Files.lines(Paths.get(DATA_FILE_PATH), StandardCharsets.UTF_8));

        MainEditorUI ui = new MainEditorUI(rides);
        ui.toggleUndo(false);
        ui.toggleRedo(false);
        ui.toggleScoreBoard(false);

        Scene scene = createScene(ui);

        stage.setTitle("Departure App");
        stage.setScene(scene);
        stage.show();

        //ScenicView.show(scene); // Uncomment for debugging

        EditQueue editQueue = new EditQueue();

        addEventHandlers(ui, editQueue, trainRideRepository);
    }

    /**
     * Creates the Scene object with all stylesheets and fonts loaded
     * @param ui Main editor UI
     * @return A Scene object
     */
    private Scene createScene(MainEditorUI ui)
    {
        Scene scene = new Scene(ui, 1080, 720);

        Font.loadFont(FileHelper.getAbsolutePath("resources/assets/font/Roboto-Medium.ttf"), 12);
        scene.getStylesheets().add(FileHelper.getAbsolutePath("resources/assets/css/main.css"));
        scene.getStylesheets().add(FileHelper.getAbsolutePath("resources/assets/css/editor.css"));
        scene.getStylesheets().add(FileHelper.getAbsolutePath("resources/assets/css/navigation.css"));
        ui.prefHeightProperty().bind(scene.heightProperty());
        ui.prefWidthProperty().bind(scene.widthProperty());

        return scene;
    }

    /**
     * Attaches logic to UI elements
     * @param ui                  Main editor UI
     * @param editQueue           An instance of EditQueue to store changes
     * @param trainRideRepository Repository to persist changes on save
     */
    private void addEventHandlers(MainEditorUI ui, EditQueue editQueue, StreamRepository trainRideRepository)
    {
        DepartureAppController self = this;

        ui.on(IEventListener.EVENT_SAVE, (ride, context) -> {
            try {
                trainRideRepository.persist(new FileOutputStream(new File(DATA_FILE_PATH)), rides);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ui.on(IEventListener.EVENT_SELECT, (EventExecutionContext<Select<TrainRide>>) (payload, context) -> self.currentRide = payload.getNewEntity());
        ui.on(IEventListener.EVENT_CHANGE, (EventExecutionContext<Change>) (payload, context) -> {
            if (!editQueue.isUndo(payload) && !editQueue.isRedo(payload)) {
                editQueue.addChange(payload);

                ui.toggleUndo(editQueue.undoPossible());
                ui.toggleRedo(editQueue.redoPossible());
            }
        });
        ui.on(IEventListener.EVENT_UNDO, (payload, context) -> {
            editQueue.undo();

            ui.toggleUndo(editQueue.undoPossible());
            ui.toggleRedo(editQueue.redoPossible());
        });
        ui.on(IEventListener.EVENT_REDO, (payload, context) -> {
            editQueue.redo();

            ui.toggleUndo(editQueue.undoPossible());
            ui.toggleRedo(editQueue.redoPossible());
        });
        ui.on(IEventListener.EVENT_DELETE, (payload, context) -> {
            editQueue.addChange(new Delete<>(rides, currentRide));
            rides.remove(currentRide);

            ui.toggleUndo(editQueue.undoPossible());
            ui.toggleRedo(editQueue.redoPossible());
        });
        ui.on(IEventListener.EVENT_NEW, (payload, context) -> {
            int id = rides.size() > 0 ? rides.get(rides.size() - 1).getId() + 1 : 1;
            TrainRide ride = TrainRideFactory.fromNullWithId(id);
            editQueue.addChange(new Create<>(rides, ride));
            rides.add(ride);

            ui.toggleUndo(editQueue.undoPossible());
            ui.toggleRedo(editQueue.redoPossible());
        });
        ui.on(IEventListener.EVENT_SEARCH, (EventExecutionContext<String>) (payload, context) -> ui.search(payload));
        ui.on(IEventListener.EVENT_STARTSCOREBOARD, (payload, context) -> scoreBoardController.start());
        ui.on(IEventListener.EVENT_ANNOUNCE_DEPARTURE, (payload, context) -> scoreBoardController.announceDeparture());
        ui.on(IEventListener.EVENT_DEPART, (payload, context) -> scoreBoardController.depart());
        ui.on(IEventListener.EVENT_UPLOAD, (payload, context) -> scoreBoardController.display(currentRide));

        scoreBoardController.on(IEventListener.EVENT_STARTSCOREBOARD, (payload, context) -> ui.toggleScoreBoard(true));
        scoreBoardController.on(IEventListener.EVENT_QUITSCOREBOARD, (payload, context) -> ui.toggleScoreBoard(false));
    }
}
