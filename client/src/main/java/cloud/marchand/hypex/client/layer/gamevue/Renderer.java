package cloud.marchand.hypex.client.layer.gamevue;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

import cloud.marchand.hypex.client.interfaces.Layer;
import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.core.models.geom.Segment;

/**
 * Render the map.
 */
public class Renderer extends Layer {

    /**
     * Number of pixel for drawing a point.
     */
    public static final int POINT_WIDTH = 10;

    /**
     * Number of pixels displayed for one unit.
     */
    public static final int WIDTH_SQUARE = 100;

    /**
     * Draw map.
     * 
     * @param graphics graphical zone
     * @param map      map to draw into
     */
    @Override
    public void draw(Graphics graphics, Map map) {
        Set<Segment> segments = map.getSegments();
        for (Segment segment : segments) {
            graphics.setColor(Color.GREEN);
            graphics.drawLine((int) segment.p1.getX() * WIDTH_SQUARE, (int) segment.p1.getY() * WIDTH_SQUARE,
                    (int) segment.p2.getX() * WIDTH_SQUARE, (int) segment.p2.getY() * WIDTH_SQUARE);
            graphics.setColor(Color.RED);
            graphics.fillOval((int) (segment.p1.getX() * WIDTH_SQUARE - POINT_WIDTH / 2),
                    (int) (segment.p1.getY() * WIDTH_SQUARE - POINT_WIDTH / 2), POINT_WIDTH, POINT_WIDTH);
            graphics.fillOval((int) (segment.p2.getX() * WIDTH_SQUARE - POINT_WIDTH / 2),
                    (int) (segment.p2.getY() * WIDTH_SQUARE - POINT_WIDTH / 2), POINT_WIDTH, POINT_WIDTH);
        }
    }

}
