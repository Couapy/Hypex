package cloud.marchand.hypex.core.models.geom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represent a segment between two points.
 * 
 * @see cloud.marchand.hypex.core.models.geom.Point
 */
public class Segment {

    /**
     * First point, A.
     */
    public Point p1;

    /**
     * Second point B.
     */
    public Point p2;

    /**
     * Create a segment from two points.
     * 
     * @param a first point
     * @param b second point
     */
    public Segment(Point a, Point b) {
        p1 = a;
        p2 = b;
    }

    /**
     * Give the intersection of segments.
     * 
     * @param segment other segment to intersect
     * @return the intersection point, or null if there isn't
     */
    public Point getIntersectionPoint(Segment segment) {
        // Colinerarity
        Point vA = new Point(p2.x - p1.x, p2.y - p1.y);
        Point vB = new Point(segment.p2.x - segment.p1.x, segment.p2.y - segment.p1.y);

        double product = vA.x * vB.y - vA.y * vB.x;
        if (product == 0d) {
            return null;
        }

        // Intersection
        // Avoid vertical functions
        if (vA.x == 0d) {
            FunctionLinear lineB = new FunctionLinear(segment.p1, segment.p2);
            return lineB.evaluate(p1.x);
        } else if (vB.x == 0d) {
            FunctionLinear lineA = new FunctionLinear(p1, p2);
            return lineA.evaluate(segment.p1.x);
        } else {
            FunctionLinear lineA = new FunctionLinear(p1, p2);
            FunctionLinear lineB = new FunctionLinear(segment.p1, segment.p2);
            return lineA.getIntersectionPoint(lineB);
        }
    }

    /**
     * Defines the first point.
     * 
     * @param p1 first point
     */
    public void setPoint1(Point p1) {
        this.p1 = p1;
    }

    /**
     * Defines the second point.
     * 
     * @param p2 second point
     */
    public void setPoint2(Point p2) {
        this.p2 = p2;
    }

    /**
     * Get the segment points in a list.
     * 
     * @return a list of points
     */
    public List<Point> getPoints() {
        return new ArrayList<>(Arrays.asList(new Point[] { p1, p2 }));
    }

    /**
     * Get the segment as a vector.
     * 
     * @return vector
     */
    public Point asVector() {
        return new Point(p2.x - p1.x, p2.y - p1.y);
    }

    /**
     * Return the angle of the segment with x axis.
     * 
     * @return angle in radians
     */
    public double getAngle() {
        Point p = new Point(p2.x - p1.x, p2.y - p1.y);
        return Math.atan2(p.y, p.x);
    }

}
