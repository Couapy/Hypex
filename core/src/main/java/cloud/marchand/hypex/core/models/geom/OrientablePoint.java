package cloud.marchand.hypex.core.models.geom;

/**
 * Represent a point with an orientation.
 * 
 * @see cloud.marchand.hypex.core.models.geom.Point
 */
public class OrientablePoint extends Point {

    /**
     * Radian angle between orientation and x axis.
     */
    public double angle;

    /**
     * Create an orientation by default.
     */
    public OrientablePoint() {
        super();
        angle = 0d;
    }

    /**
     * Create a parameterized oriented point.
     * 
     * @param x     x coordinate
     * @param y     y coordinate
     * @param angle orientation of the point
     */
    public OrientablePoint(double x, double y, double angle) {
        super(x, y);
        this.angle = angle;
    }

    /**
     * Defines a new orientation.
     * 
     * @param angle new angle
     * @see #angle
     */
    public void setOrientation(double angle) {
        this.angle = angle;
    }

    /**
     * Give the point orientation.
     * 
     * @return angle in radians
     * @see #angle
     */
    public double getOrientation() {
        return angle;
    }

}
