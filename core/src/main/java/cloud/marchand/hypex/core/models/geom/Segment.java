package cloud.marchand.hypex.core.models.geom;

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
        Point res;
        if (vA.x == 0d) {
            FunctionLinear lineB = new FunctionLinear(segment.p1, segment.p2);
            res = lineB.evaluate(p1.x);
        } else if (vB.x == 0d) {
            FunctionLinear lineA = new FunctionLinear(p1, p2);
            res = lineA.evaluate(segment.p1.x);
        } else {
            FunctionLinear lineA = new FunctionLinear(p1, p2);
            FunctionLinear lineB = new FunctionLinear(segment.p1, segment.p2);
            double x = (lineB.c - lineA.c) / (lineA.m - lineB.m);
            double y = lineA.m * x + lineA.c;

            res = new Point(x, y);
        }

        // Verify if the point belong to the segment
        if (Math.min(p1.x, p2.x) <= res.x && res.x <= Math.max(p1.x, p2.x) && Math.min(p1.y, p2.y) <= res.y
                && res.y <= Math.max(p1.y, p2.y)) {
            return res;
        }

        return null;
    }

}
