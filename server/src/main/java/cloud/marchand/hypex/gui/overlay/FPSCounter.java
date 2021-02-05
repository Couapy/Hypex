package cloud.marchand.hypex.gui.overlay;

import java.awt.Color;
import java.awt.Graphics;

import cloud.marchand.hypex.gui.interfaces.Overlay;
import cloud.marchand.hypex.gui.map.Map;


/**
 * Display an FPS counter on the graphics.
 */
public class FPSCounter extends Overlay {

    /**
     * Last timestamp the overlay updated the fps counter.
     * Updated each second.
     */
    private long lastTime = 0;

    /**
     * Number of graphical updates since the last second.
     */
    private int FPS = 0;

    /**
     * Number of graphical updates happened last second.
     */
    private int FPS_AVERAGE = 0;

    /**
     * Update the FPS counter and display it on the top left corner.
     */
    @Override
    public void draw(Graphics graphics, Map map) {
        if (System.currentTimeMillis() > lastTime + 1000) {
            lastTime = System.currentTimeMillis();
            FPS_AVERAGE = FPS;
            FPS = 0;
        }
        FPS++;
        if (FPS_AVERAGE > 120) {
            graphics.setColor(Color.GREEN);
        }
        else if (FPS_AVERAGE > 60) {
            graphics.setColor(Color.ORANGE);
        }
        else {
            graphics.setColor(Color.RED);
        }
        graphics.drawString("FPS " + FPS_AVERAGE, 0, 10);
    }
    
}
