package cloud.marchand.hypex.client.layer.gamevue;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cloud.marchand.hypex.client.interfaces.Layer;
import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.client.vues.GameVue;
import cloud.marchand.hypex.core.models.geom.Point;
import cloud.marchand.hypex.core.models.geom.PointOfView;
import cloud.marchand.hypex.core.models.geom.Segment;

public class RaycastingSkeleton extends Layer {

    private static final Color VISION_COLOR = Color.RED;
    private static final Color PROJECTION_COLOR = Color.ORANGE;
    private static final Color CURSOR_COLOR = Color.MAGENTA;
    private static final int CURSOR_THICK = 10;

    private static final double ANGLE_THRESHOLD = 0.0001;

    protected class Projection {
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

    protected GameVue vue;

    public RaycastingSkeleton(GameVue vue) {
        this.vue = vue;
    }

    @Override
    public void draw(Graphics graphics, Map map) {
        PointOfView pov = vue.pov;
        // List<Projection> projections = getProjections(map, pov);

        // // Draw projections
        // graphics.setColor(PROJECTION_COLOR);
        // for (Projection projection : projections) {
        //     graphics.drawLine((int) (pov.x * MapRenderer.WIDTH_SQUARE), (int) (pov.y * MapRenderer.WIDTH_SQUARE),
        //             (int) (projection.pos.x * MapRenderer.WIDTH_SQUARE), (int) (projection.pos.y * MapRenderer.WIDTH_SQUARE));
        // }

        // Draw vision
        System.out.print(pov + "\r");
        graphics.setColor(VISION_COLOR);
        Point projection;
        projection = map.getProjection(pov, pov.angle - pov.angleOfView / 2);
        if (projection != null) {
            graphics.drawLine(
                (int)(pov.x * MapRenderer.WIDTH_SQUARE),
                (int)(pov.y * MapRenderer.WIDTH_SQUARE),
                (int)(projection.x * MapRenderer.WIDTH_SQUARE),
                (int)(projection.y * MapRenderer.WIDTH_SQUARE)
            );
        }
        graphics.setColor(Color.BLUE);
        projection = map.getProjection(pov, pov.angle);
        if (projection != null) {
            graphics.setColor(Color.BLUE);
            graphics.drawLine(
                (int)(pov.x * MapRenderer.WIDTH_SQUARE),
                (int)(pov.y * MapRenderer.WIDTH_SQUARE),
                (int)(projection.x * MapRenderer.WIDTH_SQUARE),
                (int)(projection.y * MapRenderer.WIDTH_SQUARE)
            );
        }
        graphics.setColor(VISION_COLOR);
        projection = map.getProjection(pov, pov.angle + pov.angleOfView / 2);
        if (projection != null) {
            graphics.drawLine(
                (int)(pov.x * MapRenderer.WIDTH_SQUARE),
                (int)(pov.y * MapRenderer.WIDTH_SQUARE),
                (int)(projection.x * MapRenderer.WIDTH_SQUARE),
                (int)(projection.y * MapRenderer.WIDTH_SQUARE)
            );
        }

        // Draw position
        graphics.setColor(CURSOR_COLOR);
        graphics.fillOval((int) (pov.getX() * MapRenderer.WIDTH_SQUARE - CURSOR_THICK / 2d),
                (int) (pov.getY() * MapRenderer.WIDTH_SQUARE - CURSOR_THICK / 2d), CURSOR_THICK, CURSOR_THICK);
    }

    /**
     * Get a list of projections from a position on a map.
     * 
     * @param map    map
     * @param origin point of view
     * @return list of projection
     * @see cloud.marchand.hypex.client.layer.RaycastingSkeleton.Projection
     */
    protected List<Projection> getProjections(Map map, Point origin) {
        List<Projection> projections = new ArrayList<>();
        PointOfView pov = vue.pov;

        for (Segment segment : map.getSegments()) {
            for (Point point : segment.getPoints()) {
                Segment vision = new Segment(origin, point);
                double angle = vision.getAngle();
                for (int i = -1; i < 2; i++) {
                    double angleProjection = angle + i * ANGLE_THRESHOLD;
                    Point pointProjection = map.getProjection(origin, angleProjection);
                    if (pointProjection != null) {
                        Projection projection = new Projection(pointProjection);
                        projection.angle = angleProjection;
                        projection.distance = pov.distance(pointProjection);
                        projections.add(projection);
                    }
                }
            }
        }

        Collections.sort(projections, new ProjectionComparator());

        return projections;
    }

}
