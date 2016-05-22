package ch.fhnw.oop2.departure_app.View.ScoreBoard;

import ch.fhnw.oop2.departure_app.Model.Entity.TrainRide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import java.util.ArrayList;

/**
 * Main UI class for the additional score board window
 */
public class MainScoreBoardUI extends BorderPane
{
    private ArrayList<TrainRide> rides = new ArrayList<>();
    private ObservableList<ScoreBoardRow> tableRows = FXCollections.observableArrayList();
    private ScoreBoardTableView scoreBoardTable;
    private int rows = 6;
    private boolean departureAnnounced = false;
    private boolean isDeparting = false;

    /**
     * Main UI class for the additional score board window
     */
    public MainScoreBoardUI()
    {
        getStyleClass().addAll("scoreboard");

        initializeControls();
        layoutControls();
    }

    /**
     * Initializes the table view
     */
    private void initializeControls()
    {
        for (int i = 0; i < rows; i++) {
            tableRows.add(new ScoreBoardRow());
        }

        scoreBoardTable = new ScoreBoardTableView(tableRows);
    }

    /**
     * Adds a trainride to the stack
     * @param ride Newly added trainride to the showing queue, won't be displayed if there's no space available
     */
    public void addTrainRide(TrainRide ride)
    {
        rides.add(ride);
        int index = rides.indexOf(ride);

        if (index <= rows - 1) {
            displayTrainRideAtRow(ride, index);
        }
    }

    /**
     * Announces the departure of the first row of the score board
     */
    public void announceDeparture()
    {
        if (!departureAnnounced && !isDeparting) {
            scoreBoardTable.getItems().get(0).announceDeparture();
            departureAnnounced = true;
        }
    }

    /**
     * Lets the current row depart
     */
    public void depart()
    {
        if (isDeparting) {
            scoreBoardTable.getItems().get(0).resetLed();

            rides.remove(0);

            for (int i = 0; i < rows; i++) {
                ScoreBoardRow row = tableRows.get(i);
                if (rides.size() - 1 < i) {
                    row.displayEmpty();
                } else {
                    row.display(rides.get(i));
                }
            }

            isDeparting = false;
            departureAnnounced = false;
        }

        if (departureAnnounced) {
            scoreBoardTable.getItems().get(0).depart();
            isDeparting = true;
        }
    }

    /**
     * Displays a train ride at a gievn row
     * @param ride  TrainRide to display
     * @param index Row number to display the TrainRide in
     */
    public void displayTrainRideAtRow(TrainRide ride, int index)
    {
        if (index > rows -1) {
            return;
        }

        tableRows.get(index).display(ride);
    }

    /**
     * Layouts the whole UI
     */
    private void layoutControls()
    {
        setCenter(scoreBoardTable);
    }
}
