package ch.fhnw.oop2.departure_app.View.ScoreBoard;

import ch.fhnw.oop2.departure_app.View.ScoreBoard.Led.LedTrafficLight;
import ch.fhnw.oop2.departure_app.View.ScoreBoard.SplitFlap.SplitFlapSentence;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Single row in the score board
 */
class ScoreBoardTableView extends TableView<ScoreBoardRow>
{
    /**
     * Table view for the score board
     */
    ScoreBoardTableView(ObservableList<ScoreBoardRow> rows)
    {
        super(rows);

        TableColumn<ScoreBoardRow, LedTrafficLight> ledCol = new TableColumn<>("");
        TableColumn<ScoreBoardRow, SplitFlapSentence> departureCol = new TableColumn<>("Abfahrt");
        TableColumn<ScoreBoardRow, SplitFlapSentence> finalDestinationPlaceCol = new TableColumn<>("Nach");
        TableColumn<ScoreBoardRow, SplitFlapSentence> trackCol = new TableColumn<>("Gleis");

        ledCol.setCellValueFactory(cellData -> cellData.getValue().getLed());
        departureCol.setCellValueFactory(cellData -> cellData.getValue().getDeparture());
        finalDestinationPlaceCol.setCellValueFactory(cellData -> cellData.getValue().getDestination());
        trackCol.setCellValueFactory(cellData -> cellData.getValue().getTrack());

        getColumns().addAll(ledCol, departureCol, finalDestinationPlaceCol, trackCol);
    }
}
