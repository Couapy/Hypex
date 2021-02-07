package cloud.marchand.hypex.core.models.geom;

/**
 * Represent a linear function.
 */
public class FunctionLinear {

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
    public FunctionLinear(double m, double c) {
        this.m = m;
        this.c = c;
    }

    /**
     * Create a linear function from two points. Order of points doesn't matter.
     * 
     * @param a first point
     * @param b second point
     */
    public FunctionLinear(Point a, Point b) {
        m = (b.y - a.y) / (b.x - a.x);
        c = a.y - m * a.x;
    }

    /**
     * Create a linear function from a segement.
     * 
     * @param segment two points
     */
    public FunctionLinear(Segment segment) {
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

}
