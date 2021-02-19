package cloud.marchand.hypex.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cloud.marchand.hypex.core.models.geom.Point;
import cloud.marchand.hypex.core.models.geom.Segment;
import cloud.marchand.hypex.core.utils.FileUtil;

/**
 * Playing environnement.
 */
public class Map {

    /**
     * List of points that compose the map.
     */
    private List<Point> points = new ArrayList<>();

    /**
     * Walls of the map.
     */
    private List<Segment> segments = new ArrayList<>();

    /**
     * Origin of the map.
     */
    private Point origin = null;

    /**
     * Create a virgin map.
     */
    protected Map() {
    }

    /**
     * Create a map from points and segments.
     */
    public Map(List<Point> points, List<Segment> segments) {
        this.points = points;
        this.segments = segments;
    }

    /**
     * Load a map from a file.
     * 
     * @param filepath file path inside the resource folder
     * @return the loaded map
     * @throws IOException throwed if the file does not exists
     */
    public static Map fromFile(String filepath) throws IOException {
        String content = FileUtil.readFile(filepath);
        Map map = new Map();

        String[] lines = content.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String[] line = lines[i].split(":");
            String indicator = line[0];
            String[] arguments = line[1].split(";");
            if (indicator.equals("p")) {
                map.points.add(new Point(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1])));
            } else if (indicator.equals("s")) {
                map.segments.add(new Segment(map.points.get(Integer.parseInt(arguments[0])),
                        map.points.get(Integer.parseInt(arguments[1]))));
            } else if (indicator.equals("o")) {
                map.setOrigin(new Point(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1])));
            }
        }

        return map;
    }

    /**
     * Get the projection point from a point of view
     * 
     * @param pov   point of view
     * @param point point projected
     * @return projection
     */
    public Point getProjection(Point pov, Point point) {
        Segment ray = new Segment(pov, point);
        Point closest = null;

        for (Segment segment : segments) {
            Point intersect = segment.intersect(ray);
            if (intersect == null) {
                continue;
            }
            if (closest == null || intersect.distanceFrom(pov) < closest.distanceFrom(pov)) {
                closest = intersect;
            }
        }
        return closest;
    }

    /**
     * Give the origin point.
     * 
     * @return origin point
     */
    public Point getOrigin() {
        return origin;
    }

    /**
     * Defines the origin point.
     * 
     * @param origin new origin point
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * Give a point set.
     * 
     * @return point set
     */
    public List<Point> getPoints() {
        return points;
    }

    /**
     * Defines the points set.
     * 
     * @param points new points set
     */
    public void setPoints(List<Point> points) {
        this.points = points;
    }

    /**
     * Give a segment set.
     * 
     * @return segment set
     */
    public List<Segment> getSegments() {
        return segments;
    }

    /**
     * Defines the segment set.
     * 
     * @param segments new segment set
     */
    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

}