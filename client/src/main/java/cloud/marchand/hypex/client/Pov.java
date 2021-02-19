package cloud.marchand.hypex.client;

import cloud.marchand.hypex.core.models.geom.OrientablePoint;
import cloud.marchand.hypex.core.models.geom.Point;

/**
 * Represent a point of view.
 */
public class Pov extends OrientablePoint {

    /**
     * Field of view. Angle in radians
     */
    public double fov;

    /**
     * Create a default point of view.
     */
    public Pov() {
        this(new Point());
    }

    /**
     * Create a point of view from a position.
     */
    public Pov(Point point) {
        super(point.x, point.y, 0d);
        fov = Math.PI / 2;
    }

    /**
     * Create a parameterized point of view.
     * 
     * @param x     x component
     * @param y     y component
     * @param angle angle of view in radians
     * @param fov   field of view in radians
     */
    public Pov(double x, double y, double angle, double fov) {
        super(x, y, angle);
        this.setFov(fov);
    }

    /**
     * Get the field of view.
     * 
     * @return field of view
     */
    public double getFov() {
        return fov;
    }

    /**
     * Defines the field of view.
     * 
     * @param fov new field of view
     */
    public void setFov(double fov) {
        this.fov = fov;
    }

}
