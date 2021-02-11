package cloud.marchand.hypex.client.vues;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cloud.marchand.hypex.client.App;
import cloud.marchand.hypex.client.interfaces.Layer;
import cloud.marchand.hypex.client.interfaces.Vue;
import cloud.marchand.hypex.client.layer.gamevue.FPSCounter;
import cloud.marchand.hypex.client.layer.gamevue.RaycastingSkeleton;
import cloud.marchand.hypex.client.layer.gamevue.Renderer;
import cloud.marchand.hypex.client.listener.gamevue.KeyboardListener;
import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.core.models.Timer;
import cloud.marchand.hypex.core.models.geom.OrientablePoint;
import cloud.marchand.hypex.core.models.geom.Point;
import cloud.marchand.hypex.core.models.geom.PointOfView;

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
    private Set<Layer> layers = new HashSet<>();

    private boolean running;

    private Timer timer;

    public PointOfView pov;

    /**
     * Instanciate a canvas.
     * 
     * @param map map to display
     */
    public GameVue(App app, Map map) {
        super(app);
        this.timer = new Timer(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDisplayMode().getRefreshRate());
        this.map = map;
        this.running = false;
        this.pov = new PointOfView(3d, 3d, Math.PI / 3, Math.PI / 3);

        // Add layers
        layers.add(new Renderer());
        layers.add(new RaycastingSkeleton(this));
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
        Iterator<Layer> iterator = layers.iterator();
        while (iterator.hasNext()) {
            Layer overlay = iterator.next();
            overlay.draw(graphics, map);
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
