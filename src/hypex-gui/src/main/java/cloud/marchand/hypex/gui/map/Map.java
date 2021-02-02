package cloud.marchand.hypex.gui.map;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import cloud.marchand.hypex.gui.Edge;

public class Map {

    protected Set<Point> points;
    protected Set<Edge> edges;

    public Map() {
        points = new HashSet<>();
        edges = new HashSet<>();
    }

    public Set<Point> getPoints() {
        return points;
    }
 
	public Set<Edge> getEdges() {
		return edges;
	}

}
