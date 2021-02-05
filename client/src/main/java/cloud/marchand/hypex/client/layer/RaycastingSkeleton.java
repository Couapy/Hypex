package cloud.marchand.hypex.client.layer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import cloud.marchand.hypex.client.Canvas;
import cloud.marchand.hypex.client.interfaces.Layer;
import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.core.models.geom.Segment;

public class RaycastingSkeleton extends Layer {

    private class Projection {
        public Point pos;
        public double angle;
        public double distance;
    }

    private Canvas canvas;

    public RaycastingSkeleton(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void draw(Graphics graphics, Map map) {
        Point mousePosition = canvas.getMousePosition();
        if (mousePosition != null) {
            graphics.setColor(Color.GRAY);
            for (Segment segment : map.getSegments()) {
                // int[] xPoints = new int[3], yPoints = new int[3];
                // xPoints[0] = (int) edge.getA().getX() * Renderer.WIDTH_SQUARE;
                // xPoints[1] = (int) edge.getB().getX() * Renderer.WIDTH_SQUARE;
                // xPoints[2] = (int) mousePosition.getX();
                // yPoints[0] = (int) edge.getA().getY() * Renderer.WIDTH_SQUARE;
                // yPoints[1] = (int) edge.getB().getY() * Renderer.WIDTH_SQUARE;
                // yPoints[2] = (int) mousePosition.getY();
                // graphics.drawPolygon(xPoints, yPoints, 3);
            }

            graphics.setColor(Color.MAGENTA);
            graphics.fillOval((int) mousePosition.getX() - Renderer.POINT_WIDTH / 2,
                    (int) mousePosition.getY() - Renderer.POINT_WIDTH / 2, Renderer.POINT_WIDTH, Renderer.POINT_WIDTH);
        }

    }

}
