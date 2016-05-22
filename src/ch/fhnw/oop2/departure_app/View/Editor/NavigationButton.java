package ch.fhnw.oop2.departure_app.View.Editor;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Button;

/**
 * Custom button for navigation
 */
class NavigationButton extends Button
{
    /**
     * Constructor
     * @param icon Icon to display
     */
    NavigationButton(FontAwesomeIcon icon)
    {
        GlyphsDude.setIcon(this, icon);
        getStyleClass().add("button-icon");
    }

    /**
     * Disables the button
     */
    void disable()
    {
        if (!getStyleClass().contains("button-disabled")) {
            getStyleClass().add("button-disabled");
        }
    }

    /**
     * Enables the button
     */
    void enable()
    {
        if (getStyleClass().contains("button-disabled")) {
            getStyleClass().remove("button-disabled");
        }
    }
}
