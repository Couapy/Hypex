package cloud.marchand.hypex.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import cloud.marchand.hypex.gui.interfaces.Overlay;
import cloud.marchand.hypex.gui.map.Map;
import cloud.marchand.hypex.gui.overlay.FPSCounter;
import cloud.marchand.hypex.gui.overlay.Renderer;

/**
 * Drawing surface.
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {

    /**
     * Map to display.
     */
    private Map map;
    private Set<Overlay> overlays = new HashSet<>();

    /**
     * Instanciate a canvas.
     * 
     * @param map map to display
     */
    public Canvas(Map map) {
        this.map = map;

        // Overlays
        overlays.add(new Renderer(this));
        overlays.add(new FPSCounter());

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
        Iterator<Overlay> iterator = overlays.iterator();
        while (iterator.hasNext()) {
            Overlay overlay = iterator.next();
            overlay.draw(graphics, map);
        }
    }

}
