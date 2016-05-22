package ch.fhnw.oop2.departure_app.View.ScoreBoard;

import ch.fhnw.oop2.departure_app.Model.Entity.TrainRide;
import ch.fhnw.oop2.departure_app.View.ScoreBoard.Led.LedTrafficLight;
import ch.fhnw.oop2.departure_app.View.ScoreBoard.SplitFlap.SplitFlapSentence;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Row of the score board
 */
class ScoreBoardRow
{
    /**
     * 2 LEDs on top of each other
     */
    private SimpleObjectProperty<LedTrafficLight> led;

    /**
     * 5 SplitFlaps representing time
     */
    private SimpleObjectProperty<SplitFlapSentence> departure;

    /**
     * 16 SPlitFlaps representing the destination
     */
    private SimpleObjectProperty<SplitFlapSentence> destination;

    /**
     * 2 SPlitFlaps representing the track
     */
    private SimpleObjectProperty<SplitFlapSentence> track;

    /**
     * Width of the time sentence
     */
    private int timeWidth = 5;

    /**
     * Width of the destination sentence
     */
    private int destinationWidth = 16;

    /**
     * Width of the track sentence
     */
    private int trackWidth = 2;

    /**
     * Single row in the table
     */
    ScoreBoardRow()
    {
        SplitFlapSentence departureSentence = new SplitFlapSentence(timeWidth);
        departureSentence.setText(padRight("", 5));

        SplitFlapSentence destinationSentence = new SplitFlapSentence(destinationWidth);
        SplitFlapSentence trackSentence = new SplitFlapSentence(trackWidth);

        led = new SimpleObjectProperty<>(new LedTrafficLight());
        destination = new SimpleObjectProperty<>(destinationSentence);
        departure = new SimpleObjectProperty<>(departureSentence);
        track = new SimpleObjectProperty<>(trackSentence);
    }

    /**
     * Display a given TrainRide
     * @param ride TrainRide to display
     */
    void display(TrainRide ride)
    {
        departure.getValue().setText(ride.getDepartureTimeAsString());
        destination.getValue().setText(padRight(ride.getFinalDestinationPlace().toUpperCase(), destinationWidth));
        track.getValue().setText(padLeft(Integer.toString(ride.getTrack()), trackWidth));
    }

    /**
     * Display the row as empty
     */
    void displayEmpty()
    {
        departure.getValue().setText(padRight("", 5));
        destination.getValue().setText(padRight("", destinationWidth));
        track.getValue().setText(padRight("", trackWidth));
    }

    /**
     * Announces the departure of this row
     */
    void announceDeparture()
    {
        led.getValue().announceDeparture();
    }

    /**
     * Departs the current row
     */
    void depart()
    {
        led.getValue().depart();
    }

    /**
     * Resets the LED to the initial state (all red)
     */
    void resetLed()
    {
        led.getValue().reset();
    }

    /**
     * Gets the LED
     * @return LED property
     */
    SimpleObjectProperty getLed()
    {
        return led;
    }

    /**
     * Get the departure object
     * @return Departure property
     */
    public SimpleObjectProperty getDeparture()
    {
        return departure;
    }

    /**
     * Get the destination object
     * @return Destination property
     */
    SimpleObjectProperty getDestination()
    {
        return destination;
    }

    /**
     * Get the track object
     * @return Track property
     */
    SimpleObjectProperty getTrack()
    {
        return track;
    }

    /**
     * Pads a string to the right with spaces
     * Thanks to http://stackoverflow.com/a/391978/2115232
     * @param s String to pad
     * @param n Total number of characters the string should have in the end
     * @return Padded string
     */
    private static String padRight(String s, int n)
    {
        return String.format("%1$-" + n + "s", s);
    }

    /**
     * Pads a string to the left with spaces
     * Thanks to http://stackoverflow.com/a/391978/2115232
     * @param s String to pad
     * @param n Total number of characters the string should have in the end
     * @return Padded string
     */
    private static String padLeft(String s, int n)
    {
        return String.format("%1$" + n + "s", s);
    }
}
