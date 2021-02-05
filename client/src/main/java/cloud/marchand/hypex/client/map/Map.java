package cloud.marchand.hypex.client.map;

import java.util.HashSet;
import java.util.Set;

import cloud.marchand.hypex.core.models.geom.Point;
import cloud.marchand.hypex.core.models.geom.Segment;

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
    public Map() {
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

}
