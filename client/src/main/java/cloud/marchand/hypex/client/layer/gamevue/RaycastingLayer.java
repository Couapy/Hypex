package cloud.marchand.hypex.client.layer.gamevue;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import cloud.marchand.hypex.client.Pov;
import cloud.marchand.hypex.client.interfaces.CanvasLayer;
import cloud.marchand.hypex.client.interfaces.Vue;
import cloud.marchand.hypex.core.models.Map;
import cloud.marchand.hypex.core.models.geom.Point;
import cloud.marchand.hypex.core.models.geom.Segment;

/**
 * 3D rendering from a map and a pov.
 */
public class RaycastingLayer extends CanvasLayer {

    private class Projection extends Point {

        public double angle;
        public double angleModulo;
        public double distance;

        public Projection(Point p) {
            super(p.x, p.y);
            angle = (new Segment(pov, this)).getAngle();
            angleModulo = angle < 0 ? angle + 2 * Math.PI : angle;
            distance = distanceFrom(pov);
        }

    }

    private class ProjectionComparator implements Comparator<Projection> {
        @Override
        public int compare(Projection p1, Projection p2) {
            if (Math.abs(pov.angle) < Math.PI / 2) {
                return p1.angle < p2.angle ? -1 : 1;
            }
            return p1.angleModulo < p2.angleModulo ? -1 : 1;
        }
    }

    private static final double ANGLE_THRESHOLD = 0.0001;

    private static final Color WALL_COLOR = Color.GRAY;

    private static final Color WALL_OUTLINE_COLOR = Color.BLACK;

    /**
     * Current vue.
     */
    private Vue vue;

    /**
     * Map to display.
     */
    private Map map;

    /**
     * Point of view to display.
     */
    private Pov pov;

    /**
     * Create a raycasting layer from a vue.
     */
    public RaycastingLayer(Vue vue, Map map, Pov pov) {
        this.vue = vue;
        this.map = map;
        this.pov = pov;
    }

    @Override
    public void draw(Graphics graphics) {
        List<Projection> projections = getProjections();
        if (!projections.isEmpty()) {
            Projection povProjection = new Projection(pov);
            drawWall(graphics, povProjection, projections.get(0));
            for (int i = 0; i < projections.size() - 1; i++) {
                drawWall(graphics, projections.get(i), projections.get(i + 1));
            }
            drawWall(graphics, povProjection, projections.get(projections.size() - 1));
        }
    }

    private List<Projection> getProjections() {
        List<Projection> projections = new ArrayList<>();

        for (Point point : map.getPoints()) {
            Segment seg = new Segment(pov, point);
            double angle = seg.getAngle();
            double angleMinus = angle - ANGLE_THRESHOLD;
            double anglePlus = angle + ANGLE_THRESHOLD;
            projections.add(getProjection(point));
            projections.add(getProjection(new Point(pov.x + Math.cos(anglePlus), pov.y + Math.sin(anglePlus))));
            projections.add(getProjection(new Point(pov.x + Math.cos(angleMinus), pov.y + Math.sin(angleMinus))));
        }

        filterProjections(projections);

        double fovAngle1 = pov.angle - pov.fov / 2;
        double fovAngle2 = pov.angle + pov.fov / 2;
        Projection projectionFov1 = getProjection(new Point(pov.x + Math.cos(fovAngle1), pov.y + Math.sin(fovAngle1)));
        Projection projectionFov2 = getProjection(new Point(pov.x + Math.cos(fovAngle2), pov.y + Math.sin(fovAngle2)));
        if (projectionFov1 != null) {
            projections.add(projectionFov1);
        }
        if (projectionFov2 != null) {
            projections.add(projectionFov2);
        }

        try {
            Collections.sort(projections, new ProjectionComparator());
        } catch (Exception e) {
            return new ArrayList<>();
        }

        return projections;
    }

    private void drawWall(Graphics graphics, Projection p1, Projection p2) {
        int fovDistance = 50;
        int wallHeight = Math.min(vue.getHeight() / 2, 1080);

        // Correct the fish-eye effect
        double p1d = p1.distance * Math.cos(pov.angle - p1.angle);
        double p2d = p2.distance * Math.cos(pov.angle - p2.angle);
        double a1 = pov.angle + pov.fov / 2;
        double a2 = pov.angle - pov.fov / 2;
        int x1;
        int y1;
        int x2;
        int y2;
        
        if (Math.abs(pov.angle) < Math.PI / 2) {
            x1 = (int) (((p1.angle - a2) / (a1 - a2)) * vue.getWidth());
            y1 = (int) (((wallHeight / p1d) / fovDistance) * vue.getHeight());
            x2 = (int) (((p2.angle - a2) / (a1 - a2)) * vue.getWidth());
            y2 = (int) (((wallHeight / p2d) / fovDistance) * vue.getHeight());
        } else {
            a1 = a1 < 0 ? a1 + 2 * Math.PI : a1;
            a2 = a2 < 0 ? a2 + 2 * Math.PI : a2;
            x1 = (int) (((p1.angleModulo - a2) / (a1 - a2)) * vue.getWidth());
            y1 = (int) (((wallHeight / p1d) / fovDistance) * vue.getHeight());
            x2 = (int) (((p2.angleModulo - a2) / (a1 - a2)) * vue.getWidth());
            y2 = (int) (((wallHeight / p2d) / fovDistance) * vue.getHeight());
        }
        
        int[] xPoints = new int[4];
        xPoints[0] = x1;
        xPoints[1] = x2;
        xPoints[2] = x2;
        xPoints[3] = x1;
        int[] yPoints = new int[4];
        yPoints[0] = vue.getHeight() / 2 + y1;
        yPoints[1] = vue.getHeight() / 2 + y2;
        yPoints[2] = vue.getHeight() / 2 - y2;
        yPoints[3] = vue.getHeight() / 2 - y1;
        
        graphics.setColor(WALL_COLOR);
        graphics.fillPolygon(xPoints, yPoints, 4);
        graphics.setColor(WALL_OUTLINE_COLOR);
        graphics.drawPolygon(xPoints, yPoints, 4);
    }

    private void filterProjections(List<Projection> projections) { // TODO: refactor RaycastingLayer.filerProjections(projections)
        double fovAngle1 = pov.angle - pov.fov / 2;
        double fovAngle2 = pov.angle + pov.fov / 2;
        Iterator<Projection> iterator = projections.iterator();
        if (Math.abs(pov.angle) < Math.PI / 2) {
            while (iterator.hasNext()) {
                Projection p = iterator.next();
                if (p == null || p.angle < fovAngle1 || fovAngle2 < p.angle) {
                    iterator.remove();
                }
            }
        } else {
            fovAngle1 = fovAngle1 < 0 ? fovAngle1 + 2 * Math.PI : fovAngle1;
            fovAngle2 = fovAngle2 < 0 ? fovAngle2 + 2 * Math.PI : fovAngle2;
            while (iterator.hasNext()) {
                Projection p = iterator.next();
                if (p == null || p.angleModulo < fovAngle1 || fovAngle2 < p.angleModulo) {
                    iterator.remove();
                }
            } 
        }
    }

    protected Projection getProjection(Point point) {
        Point projection = map.getProjection(pov, point);
        if (projection != null) {
            return new Projection(projection);
        }
        return null;
    }
    
}
