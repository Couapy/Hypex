package cloud.marchand.hypex.client.layer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cloud.marchand.hypex.client.Canvas;
import cloud.marchand.hypex.client.interfaces.Layer;
import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.core.models.geom.Point;
import cloud.marchand.hypex.core.models.geom.Segment;

public class RaycastingSkeleton extends Layer {

    private final Color PROJECTION_COLOR = Color.ORANGE;
    private final Color CURSOR_COLOR = Color.MAGENTA;
    private final int CURSOR_THICK = 10;

    private final double ANGLE_THRESHOLD = 0.0001;

    private class Projection {

        public Point pos;
        public double angle;
        public double distance;

        public Projection(Point point) {
            pos = point;
        }
    }

    private class ProjectionComparator implements Comparator<Projection> {

        @Override
        public int compare(Projection p1, Projection p2) {
            if (p1.angle == p2.angle) {
                return 0;
            } else if (p1.angle < p2.angle) {
                return -1;
            } else {
                return 1;
            }
        }

    }

    private Canvas canvas;

    public RaycastingSkeleton(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void draw(Graphics graphics, Map map) {
        java.awt.Point mousePosition = canvas.getMousePosition();
        mousePosition = new java.awt.Point(100, 300);
        if (mousePosition != null) {
            List<Projection> projections = getProjections(map,
                    new Point(mousePosition.x / Renderer.WIDTH_SQUARE, mousePosition.y / Renderer.WIDTH_SQUARE));
            graphics.setColor(PROJECTION_COLOR);
            for (Projection projection : projections) {
                graphics.drawLine(mousePosition.x, mousePosition.y, (int) (projection.pos.x * Renderer.WIDTH_SQUARE),
                        (int) (projection.pos.y * Renderer.WIDTH_SQUARE));
            }

            graphics.setColor(CURSOR_COLOR);
            graphics.fillOval((int) mousePosition.getX() - CURSOR_THICK / 2,
                    (int) mousePosition.getY() - CURSOR_THICK / 2, CURSOR_THICK, CURSOR_THICK);
        }
    }

    /**
     * Get a list of projections from a position on a map.
     * 
     * @param map    map
     * @param origin point of view
     * @return list of projection
     * @see cloud.marchand.hypex.client.layer.RaycastingSkeleton.Projection
     */
    private List<Projection> getProjections(Map map, Point origin) {
        List<Projection> projections = new ArrayList<>();

        // for (Segment segment : map.getSegments()) {
        //     for (Point point : segment.getPoints()) {
        //         Segment vision = new Segment(origin, point);
        //         double angle = vision.getAngle();
        //         for (int i = -1; i < 2; i++) {
        //             Point projection = map.getProjection(origin, angle + i * ANGLE_THRESHOLD);
        //             if (projection != null) {
        //                 projections.add(new Projection(projection));
        //             }
        //         }
        //     }
        // }

        java.awt.Point mousePosition = canvas.getMousePosition();
        if (mousePosition != null) {
            Point mousePoint = new Point((double) mousePosition.x / Renderer.WIDTH_SQUARE, (double) mousePosition.y / Renderer.WIDTH_SQUARE);
            double angle  = (new Segment(origin, mousePoint)).getAngle();
            Point projection = map.getProjection(origin, angle);
            System.out.print(projection + "\r");
            if (projection != null) {
                projections.add(new Projection(projection));
            }
        }

        Collections.sort(projections, new ProjectionComparator());

        return projections;
    }

    public double angle;

}
