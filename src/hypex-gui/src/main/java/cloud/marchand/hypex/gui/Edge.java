package cloud.marchand.hypex.gui;

import java.awt.Point;

/**
 * Represent edges in {@link cloud.marchand.hypex.gui.map.Map}
 */
public class Edge {

    /**
     * First node.
     */
    private Point a;

    /**
     * Last node.
     */
    private Point b;

    /**
     * Instanciate an edge object.
     */
    public Edge(Point a, Point b) {
        this.setA(a);
        this.setB(b);
    }

    /**
     * Get first point.
     */
    public Point getA() {
        return a;
    }

    /**
     * Defines first point.
     * @param a first point
     */
    public void setA(Point a) {
        this.a = a;
    }

    /**
     * Get last point.
     */
    public Point getB() {
        return b;
    }

    /**
     * Defines last point.
     * @param a last point
     */
    public void setB(Point b) {
        this.b = b;
    }

}
