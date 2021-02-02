package cloud.marchand.hypex.gui.map;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import cloud.marchand.hypex.gui.Edge;

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
    protected Set<Edge> edges;

    /**
     * Instanciate a map.
     */
    public Map() {
        points = new HashSet<>();
        edges = new HashSet<>();
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
     * Get edges of map instance.
     * 
     * @return edges
     */
    public Set<Edge> getEdges() {
        return edges;
    }

}
