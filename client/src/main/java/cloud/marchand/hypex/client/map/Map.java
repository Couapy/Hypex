package cloud.marchand.hypex.client.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cloud.marchand.hypex.core.models.geom.LinearFunction;
import cloud.marchand.hypex.core.models.geom.Point;
import cloud.marchand.hypex.core.models.geom.Segment;
import cloud.marchand.hypex.core.models.geom.Point.DistanceComparator;

/**
 * Map resource.
 */
public abstract class Map {

    /**
     * Nodes of the map.
     */
    protected Set<Point> points;

    /**
     * Edges of the map.
     */
    protected Set<Segment> segments;

    /**
     * Instanciate a map.
     */
    protected Map() {
        points = new HashSet<>();
        segments = new HashSet<>();
    }

    /**
     * Get nodes of map instance.
     * 
     * @return nodes
     */
    public Set<Point> getPoints() {
        return points;
    }

    /**
     * Get segments of map instance.
     * 
     * @return segments
     */
    public Set<Segment> getSegments() {
        return segments;
    }

    /**
     * Get the projection point from an angle and a position
     * 
     * @param point point of view
     * @param angle angle of view
     * @return intersection point, if none, null is returned
     */
    public Point getProjection(Point point, double angle) {
        Point orientedPoint = new Point(point.x + Math.cos(angle), point.y + Math.sin(angle));
        LinearFunction line = new LinearFunction(point, orientedPoint);
        List<Point> intersections = new ArrayList<>();

        for (Segment segment : segments) {
            Point intersection = line.getIntersectionPoint(segment);
            if (intersection != null && (new Segment(point, intersection)).getAngle() == angle) {
                intersections.add(intersection);
            }
        }

        if (!intersections.isEmpty()) {
            Collections.sort(intersections, new DistanceComparator(point));
            return intersections.get(0);
        }

        return null;
    }

}
