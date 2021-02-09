package cloud.marchand.hypex.client.interfaces;

import java.awt.Graphics;

import cloud.marchand.hypex.client.map.Map;

/**
 * Layer of a drawing zone.
 */
public abstract class Layer {

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
    protected Layer() {
        mouseFocus = false;
        keyboardFocus = false;
    }

    /**
     * Draw the overlay on the drawing zone.
     * @param graphics graphical zone to draw in
     * @param map map to draw in
     */
    public abstract void draw(Graphics graphics, Map map);
    
}
