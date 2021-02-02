package cloud.marchand.hypex.gui.interfaces;

import java.awt.Graphics;

import cloud.marchand.hypex.gui.map.Map;

/**
 * Layer of a drawing zone.
 */
public abstract class Overlay {

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
    public Overlay() {
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
