package cloud.marchand.hypex.core.models.geom;

/**
 * 2D point.
 */
public class Point {

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
     * @param x x component
     * @param y y component
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Translate the point.
     * @param x x translation
     * @param y y translation
     */
    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Give x component.
     * @return x component
     */
    public double getX() {
        return x;
    }

    /**
     * Give y component.
     * @return y component
     */
    public double getY() {
        return y;
    }

    /**
     * Make readable the object in console.
     * @return human-readable coordinates
     */
    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }

}
