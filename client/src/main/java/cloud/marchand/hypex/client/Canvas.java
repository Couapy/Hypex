package cloud.marchand.hypex.client;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import cloud.marchand.hypex.client.interfaces.Layer;
import cloud.marchand.hypex.client.layer.FPSCounter;
import cloud.marchand.hypex.client.layer.RaycastingSkeleton;
import cloud.marchand.hypex.client.layer.Renderer;
import cloud.marchand.hypex.client.map.Map;

/**
 * Drawing surface.
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {

    /**
     * Map to display.
     */
    private Map map;
    private Set<Layer> layers = new HashSet<>();

    /**
     * Instanciate a canvas.
     * 
     * @param map map to display
     */
    public Canvas(Map map) {
        this.map = map;

        // Overlays
        layers.add(new Renderer(this));
        layers.add(new RaycastingSkeleton(this));
        layers.add(new FPSCounter());

        // Controllers
        // this.addMouseMotionListener(new MouseMotionController(this));
    }

    /**
     * Draw edges, and visible zones.
     * 
     * @param graphics graphics zone
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setBackground(Color.BLACK);
        Iterator<Layer> iterator = layers.iterator();
        while (iterator.hasNext()) {
            Layer overlay = iterator.next();
            overlay.draw(graphics, map);
        }
    }

}
