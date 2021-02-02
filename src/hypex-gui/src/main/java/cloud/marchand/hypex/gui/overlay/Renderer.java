package cloud.marchand.hypex.gui.overlay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Set;

import cloud.marchand.hypex.gui.Canvas;
import cloud.marchand.hypex.gui.Edge;
import cloud.marchand.hypex.gui.interfaces.Overlay;
import cloud.marchand.hypex.gui.map.Map;

/**
 * Render the map.
 */
public class Renderer extends Overlay {

    /**
     * Number of pixel for drawing a point.
     */
    private static final int POINT_WIDTH = 10;

    /**
     * Number of pixels displayed for one unit.
     */
    private static final int WIDTH_SQUARE = 100;

    /**
     * Component to paint into.
     */
    private Canvas canvas;

    /**
     * Initialize the renderer.
     * @param canvas drawing component
     */
    public Renderer(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Draw map.
     * @param graphics graphical zone
     * @param map map to draw into
     */
    @Override
    public void draw(Graphics graphics, Map map) {
        // Paint edges and nodes
        Set<Edge> edges = map.getEdges();
        for (Edge edge : edges) {
            graphics.setColor(Color.GREEN);
            graphics.drawLine((int) edge.getA().getX() * WIDTH_SQUARE, (int) edge.getA().getY() * WIDTH_SQUARE,
                    (int) edge.getB().getX() * WIDTH_SQUARE, (int) edge.getB().getY() * WIDTH_SQUARE);
            graphics.setColor(Color.RED);
            graphics.fillOval((int) (edge.getA().getX() * WIDTH_SQUARE - POINT_WIDTH / 2),
                    (int) (edge.getA().getY() * WIDTH_SQUARE - POINT_WIDTH / 2), POINT_WIDTH, POINT_WIDTH);
            graphics.fillOval((int) (edge.getB().getX() * WIDTH_SQUARE - POINT_WIDTH / 2),
                    (int) (edge.getB().getY() * WIDTH_SQUARE - POINT_WIDTH / 2), POINT_WIDTH, POINT_WIDTH);
        }

        // Paint mouse position
        Point mousePosition = canvas.getMousePosition();
        if (mousePosition != null) {
            graphics.setColor(Color.GRAY);
            for (Edge edge : map.getEdges()) {
                int[] xPoints = new int[3], yPoints = new int[3];
                xPoints[0] = (int) edge.getA().getX() * WIDTH_SQUARE;
                xPoints[1] = (int) edge.getB().getX() * WIDTH_SQUARE;
                xPoints[2] = (int) mousePosition.getX();
                yPoints[0] = (int) edge.getA().getY() * WIDTH_SQUARE;
                yPoints[1] = (int) edge.getB().getY() * WIDTH_SQUARE;
                yPoints[2] = (int) mousePosition.getY();
                graphics.drawPolygon(xPoints, yPoints, 3);
            }
            
            graphics.setColor(Color.MAGENTA);
            graphics.fillOval((int) mousePosition.getX() - POINT_WIDTH / 2,
                    (int) mousePosition.getY() - POINT_WIDTH / 2, POINT_WIDTH, POINT_WIDTH);
        }

    }
    
}
