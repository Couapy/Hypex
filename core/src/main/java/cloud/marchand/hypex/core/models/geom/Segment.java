package cloud.marchand.hypex.core.models.geom;

/**
 * Represent a segment between two points.
 * 
 * @see cloud.marchand.hypex.core.models.geom.Point
 */
public class Segment {

    /**
     * First point.
     */
    public Point a;

    /**
     * Second point
     */
    public Point b;

    /**
     * Create a segment from two points.
     * 
     * @param a first point
     * @param b second point
     */
    public Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Get intersection point with an other segment.
     * 
     * @param ray other segment
     * @return intersection point
     */
    public Point intersect(Segment ray) {
        // Avoid parallel segments
        Point vr = ray.getVertex();
        Point vs = this.getVertex();
        double dotProduct = vr.x * vs.y - vr.y * vs.x;
        if (dotProduct == 0d) {
            return null;
        }

        double t2 = (vr.x * (a.y - ray.a.y) + vr.y * (ray.a.x - a.x)) / (vs.x * vr.y - vs.y * vr.x);
        double t1 = (a.x + vs.x * t2 - ray.a.x) / vr.x;
        if (t1 < 0 || t2 < 0 || t2 > 1) {
            return null;
        }

        return new Point(ray.a.x + vr.x * t1, ray.a.y + vr.y * t1);
    }

    /**
     * Get the segment as a vertex.
     * 
     * @return the segment as a vertex
     */
    public Point getVertex() {
        return new Point(b.x - a.x, b.y - a.y);
    }

    /**
     * Get the angle of the segment;
     * 
     * @return angle between -PI and PI
     */
    public double getAngle() {
        Point vertex = getVertex();
        return Math.atan2(vertex.y, vertex.x);
    }

    /**
     * Make readable the object.
     * 
     * @return human readable description
     */
    @Override
    public String toString() {
        return "[" + a + ", " + b + "]";
    }

}
