package ch.fhnw.oop2.departure_app.View.Editor;

import ch.fhnw.oop2.departure_app.Model.Entity.TrainRide;
import ch.fhnw.oop2.departure_app.Service.Event.EventExecutionContext;
import ch.fhnw.oop2.departure_app.Service.Event.IEventListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.time.LocalTime;

/**
 * List view class for TrainRides
 */
class TrainRideTableView extends TableView<TrainRide>
{
    /**
     * Table to display all TrainRides
     * @param rides  List of rides
     * @param editor Editor pane to display a selection
     */
    TrainRideTableView(ObservableList<TrainRide> rides, Editor editor)
    {
        super(rides);

        getStyleClass().addAll("rides-list", "bordered-pane", "white-background");

        TableColumn<TrainRide, LocalTime> finalDestinationTimeCol = new TableColumn<>("Abfahrt");
        TableColumn<TrainRide, String> finalDestinationPlaceCol = new TableColumn<>("Nach");
        TableColumn<TrainRide, Integer> trackCol = new TableColumn<>("Gleis");

        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        finalDestinationTimeCol.setCellValueFactory(cellData -> cellData.getValue().getFinalDestination().getDepartureTimeProperty());
        finalDestinationPlaceCol.setCellValueFactory(cellData -> cellData.getValue().getFinalDestination().getDestinationProperty());
        trackCol.setCellValueFactory(cellData -> cellData.getValue().getTrackProperty().asObject());

        getColumns().addAll(finalDestinationTimeCol, finalDestinationPlaceCol, trackCol);

        getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (null != newSelection) {
                EventExecutionContext editorChangeEvent = editor.getEvent(IEventListener.EVENT_CHANGE);
                editor.on(IEventListener.EVENT_CHANGE, (ride, context) -> noop());
                editor.display(newSelection);
                editor.on(IEventListener.EVENT_CHANGE, editorChangeEvent);
            }
        });

    }

    /**
     * No operation
     */
    private void noop() {}
}
