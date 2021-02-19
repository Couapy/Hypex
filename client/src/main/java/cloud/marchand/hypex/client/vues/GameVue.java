package cloud.marchand.hypex.client.vues;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cloud.marchand.hypex.client.App;
import cloud.marchand.hypex.client.Pov;
import cloud.marchand.hypex.client.interfaces.CanvasLayer;
import cloud.marchand.hypex.client.interfaces.Vue;
import cloud.marchand.hypex.client.layer.gamevue.EnvironnementLayer;
import cloud.marchand.hypex.client.layer.gamevue.FPSCounter;
import cloud.marchand.hypex.client.layer.gamevue.RaycastingLayer;
import cloud.marchand.hypex.client.listener.gamevue.KeyboardListener;
import cloud.marchand.hypex.core.models.Map;
import cloud.marchand.hypex.core.models.Timer;

/**
 * Game vue.
 */
@SuppressWarnings("serial")
public class GameVue extends Vue implements Runnable {

    private static final Color BACKGROUND_COLOR = Color.BLACK;

    /**
     * Map to display.
     */
    private Map map;

    /**
     * Layers of the drawing.
     */
    protected Set<CanvasLayer> layers = new HashSet<>();

    private boolean running;

    private Timer timer;

    public Pov pov;

    /**
     * Instanciate a canvas.
     * 
     * @param map map to display
     */
    public GameVue(App app, Map map, Pov pov) {
        super(app);
        this.timer = new Timer(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDisplayMode().getRefreshRate());
        this.map = map;
        this.running = false;
        this.pov = pov;

        // Add layers
        layers.add(new EnvironnementLayer(this));
        layers.add(new RaycastingLayer(this, map, pov));
        layers.add(new FPSCounter());

        // Add controllers
        setFocusable(true);
        this.addKeyListener(new KeyboardListener(pov));
    }

    /**
     * Draw edges, and visible zones.
     * 
     * @param graphics graphics zone
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setBackground(BACKGROUND_COLOR);
        Iterator<CanvasLayer> iterator = layers.iterator();
        while (iterator.hasNext()) {
            CanvasLayer overlay = iterator.next();
            overlay.draw(graphics);
        }
    }

    @Override
    public void run() {
        while (running) {
            timer.limitRefreshRate();
            revalidate();
            repaint();
        }
    }

    @Override
    public void load() {
        if (running) {
            return;
        }
        running = true;
        Thread thread = new Thread(this);
        setName("GameVue");
        thread.start();
        thread.setName("GameVue");
    }

    @Override
    public void unload() {
        running = false;
    }

}
