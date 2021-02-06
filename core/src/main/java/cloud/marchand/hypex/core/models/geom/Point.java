package cloud.marchand.hypex.core.models.geom;

import java.util.Comparator;

/**
 * 2D point.
 */
public class Point {

    /**
     * Comparator for sorting list of points.
     * @see cloud.marchand.hypex.client.map.Map.getProjection
     */
    public static class DistanceComparator implements Comparator<Point> {

        /**
         * Origin of the comparaison.
         */
        private Point origin;

        /**
         * Create a comparator from a point of view.
         * @param origin
         */
        public DistanceComparator(Point origin) {
            this.origin = origin;
        }

        @Override
        public int compare(Point o1, Point o2) {
            double distance1, distance2;
            distance1 = origin.distance(o1);
            distance2 = origin.distance(o2);
            if (distance1 == distance2) {
                return 0;
            } else if (distance1 < distance2) {
                return -1;
            }
            else {
                return 1;
            }
        }

    }

    /**
     * x component.
     */
    public double x;

    /**
     * y component.
     */
    public double y;

    /**
     * Create a point at the origin.
     */
    public Point() {
        x = 0d;
        y = 0d;
    }

    /**
     * Create a point with coordinates.
     * 
     * @param x x component
     * @param y y component
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Translate the point.
     * 
     * @param x x translation
     * @param y y translation
     */
    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Give x component.
     * 
     * @return x component
     */
    public double getX() {
        return x;
    }

    /**
     * Give y component.
     * 
     * @return y component
     */
    public double getY() {
        return y;
    }

    /**
     * Return the distance between two points
     * 
     * @param point other point reference
     * @return distance
     */
    public double distance(Point point) {
        if (point == null) {
            return 0d;
        }
        return Math.sqrt(Math.pow(x - point.x, 2) + Math.pow(y - point.y, 2));
    }

    /**
     * Make readable the object in console.
     * 
     * @return human-readable coordinates
     */
    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }

}
