package cloud.marchand.hypex.client.layer.gamevue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

import cloud.marchand.hypex.client.interfaces.Layer;
import cloud.marchand.hypex.client.map.Map;


/**
 * Display an FPS counter on the graphics.
 */
public class FPSCounter extends Layer {

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
     * Good FPS average limit.
     */
    private int GOOD_AVERAGE;

    /**
     * Medium FPS average limit.
     */
    private int MEDIUM_AVERAGE;

    public FPSCounter() {
        int maxRefreshRate = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
        GOOD_AVERAGE = maxRefreshRate - 10;
        MEDIUM_AVERAGE = (int)(maxRefreshRate * 0.7d);
    }

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
        if (FPS_AVERAGE > GOOD_AVERAGE) {
            graphics.setColor(Color.GREEN);
        }
        else if (FPS_AVERAGE > MEDIUM_AVERAGE) {
            graphics.setColor(Color.ORANGE);
        }
        else {
            graphics.setColor(Color.RED);
        }
        graphics.drawString("FPS " + FPS_AVERAGE, 0, 10);
    }
    
}
