package ch.fhnw.oop2.departure_app.View.ScoreBoard.Led;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Two LEDs on top of each other
 */
public class LedTrafficLight extends VBox
{
    /**
     * Top LED
     */
    private Led top;

    /**
     * Bottom LED
     */
    private Led bottom;


    private int ledSize = 19;

    /**
     * Two LEDs on top of each other
     */
    public LedTrafficLight()
    {
        setAlignment(Pos.CENTER);

        top = LedBuilder.create()
            .on(true)
            .ledType(Led.LedType.ROUND)
            .prefSize(ledSize, ledSize)
            .build();
        bottom = LedBuilder.create()
            .on(true)
            .ledType(Led.LedType.ROUND)
            .prefSize(ledSize, ledSize)
            .build();

        getChildren().addAll(top, bottom);
    }

    /**
     * Announces the departure via blinking
     */
    public void announceDeparture()
    {
        top.setLedColor(Color.YELLOW);
        bottom.setLedColor(Color.YELLOW);
        bottom.setOn(false);

        top.setBlinking(true);
        bottom.setBlinking(true);
    }

    /**
     * Departs the train via show green light
     */
    public void depart()
    {
        top.setLedColor(Color.GREEN);
        top.setBlinking(false);
        top.setOn(true);

        bottom.setLedColor(Color.GREEN);
        bottom.setBlinking(false);
        bottom.setOn(true);
    }

    /**
     * Resets this LED panel
     */
    public void reset()
    {
        top.setLedColor(Color.RED);
        top.setBlinking(false);
        top.setOn(true);

        bottom.setLedColor(Color.RED);
        bottom.setBlinking(false);
        bottom.setOn(true);
    }
}
