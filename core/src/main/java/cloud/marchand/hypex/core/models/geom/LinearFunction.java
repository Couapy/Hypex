package cloud.marchand.hypex.core.models.geom;

/**
 * Represent a linear function.
 */
public class LinearFunction {

    /**
     * Proportionality coefficient.
     */
    public double m;

    /**
     * Constant.
     */
    public double c;

    /**
     * Create a linear function from constants.
     * 
     * @param m coefficient
     * @param c constant
     */
    public LinearFunction(double m, double c) {
        this.m = m;
        this.c = c;
    }

    /**
     * Create a linear function from two points. Order of points doesn't matter.
     * 
     * @param a first point
     * @param b second point
     */
    public LinearFunction(Point a, Point b) {
        m = (b.y - a.y) / (b.x - a.x);
        c = a.y - m * a.x;
    }

    /**
     * Create a linear function from a segement.
     * 
     * @param segment two points
     */
    public LinearFunction(Segment segment) {
        this(segment.p1, segment.p2);
    }

    /**
     * Give the point correspond to f(x) = y.
     * 
     * @param x x parameter
     * @return point on the linear function
     */
    public Point evaluate(double x) {
        return new Point(x, m * x + c);
    }

    /**
     * Get the intersection point of two linear functions
     * 
     * @param line other linear function to intersect
     * @return an intersection point, null if no intersection
     */
    public Point getIntersectionPoint(LinearFunction line) {
        if (m == line.m && c == line.c) {
            return evaluate(0d);
        } else if (m == line.m) {
            return null;
        }

        double x = (line.c - c) / (m - line.m);
        double y = m * x + c;
        return new Point(x, y);
    }

    /**
     * Get the intersection point with a segment
     * 
     * @param segment segment to intersect
     * @return an intersection point, null if no intersection
     */
    public Point getIntersectionPoint(Segment segment) {
        Point intersection = null;
        if (segment.p1.x - segment.p2.x == 0d) {
            intersection = evaluate(segment.p1.x);
        } else {
            LinearFunction lineSegment = new LinearFunction(segment.p1, segment.p2);
            intersection = getIntersectionPoint(lineSegment);
        }
        if (intersection != null && segment.pointBelongTo(intersection)) {
            return intersection;
        }
        return null;
    }

}
