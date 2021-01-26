package cloud.marchand.hypex.gui;

import java.awt.Point;

public class Edge {

    private Point a;
    private Point b;

    public Edge(Point a, Point b) {
        this.setA(a);
        this.setB(b);
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }

}
