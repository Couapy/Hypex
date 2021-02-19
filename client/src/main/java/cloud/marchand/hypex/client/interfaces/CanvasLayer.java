package cloud.marchand.hypex.client.interfaces;

import java.awt.Graphics;

import cloud.marchand.hypex.client.Pov;
import cloud.marchand.hypex.core.models.Map;

/**
 * Layer of a drawing zone.
 */
public abstract class CanvasLayer {

    /**
     * Indicates if the overlay need the mouse focus.
     */
    protected boolean mouseFocus;

    /**
     * Indicates if the overlay need the keyboard focus.
     */
    protected boolean keyboardFocus;

    /**
     * Create a new overlay instance.
     */
    protected CanvasLayer() {
        mouseFocus = false;
        keyboardFocus = false;
    }

    /**
     * Draw the overlay on the drawing zone.
     * @param graphics graphical zone to draw in
     */
    public abstract void draw(Graphics graphics);
    
}
