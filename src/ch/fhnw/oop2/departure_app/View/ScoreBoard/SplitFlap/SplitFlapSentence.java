package ch.fhnw.oop2.departure_app.View.ScoreBoard.SplitFlap;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

/**
 * Represents a time in with split flaps
 */
public class SplitFlapSentence extends HBox
{
    private ArrayList<SplitFlap> splitFlaps = new ArrayList<>();
    private int width = 45;
    private int height = 85;

    /**
     * Creates a sentence of SplitFlaps
     * @param size
     */
    public SplitFlapSentence(int size)
    {
        for (int i = 0; i < size; i++) {
            splitFlaps.add(SplitFlapBuilder.create()
                .prefWidth(width)
                .prefHeight(height)
                .build()
            );
        }
        getChildren().addAll(splitFlaps);
    }

    /**
     * Set a text at a given position
     * @param index     int
     * @param character char
     */
    public void setTextAt(int index, char character)
    {
        if (index > splitFlaps.size() - 1) {
            return;
        }

        splitFlaps.get(index).setText(character);
    }

    /**
     * Set the whole text
     * @param text String
     */
    public void setText(String text)
    {
        int i = 0;
        for (char character : text.toCharArray()) {
            setTextAt(i, character);
            i++;
        }
    }
}
