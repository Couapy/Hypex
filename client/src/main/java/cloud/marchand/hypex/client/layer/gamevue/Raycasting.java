package cloud.marchand.hypex.client.layer.gamevue;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.client.vues.GameVue;
import cloud.marchand.hypex.core.models.geom.Point;
import cloud.marchand.hypex.core.models.geom.PointOfView;

public class Raycasting extends RaycastingSkeleton {

    private static final Color SKY_COLOR = Color.BLUE;
    private static final Color GROUND_COLOR = Color.BLACK;
    private static final Color WALL_COLOR = Color.GRAY;

    private static final double SCALE_FACTOR = 50d;
    private static final double NEAR = 1d;
    private static final double FAR = 10;

    public Raycasting(GameVue vue) {
        super(vue);
    }

    @Override
    public void draw(Graphics graphics, Map map) {
        int width = vue.getWidth();
        int halfHeight = vue.getHeight() / 2;
        PointOfView pov = vue.pov;

        graphics.setColor(SKY_COLOR);
        graphics.fillRect(0, 0, width, halfHeight);
        graphics.setColor(GROUND_COLOR);
        graphics.fillRect(0, halfHeight, width, halfHeight);

        List<Projection> projections = getProjections(map, pov);
        for (int i = 0; i < projections.size() - 1; i++) {
            drawWall(graphics, pov, projections.get(i), projections.get(i+1));
        }
    }

    private void drawWall(Graphics graphics, PointOfView pov, Projection p1, Projection p2) {
        int width = vue.getWidth();
        int halfHeight = vue.getHeight() / 2;
        double povAngle1 = pov.angle + pov.angleOfView / 2;
        double povAngle2 = pov.angle - pov.angleOfView / 2;
        int p1Width = (int)(width * (1 - ((p1.angle - povAngle2) / (povAngle1 - povAngle2))));
        int p2Width = (int)(width * (1 - ((p2.angle - povAngle2) / (povAngle1 - povAngle2))));
        int p1Height = (int)(5d * p1.distance / (NEAR / FAR));
        int p2Height = (int)(5d * p2.distance / (NEAR / FAR));
        
        int[] xPoints = new int[]{
            p1Width,
            p1Width,
            p2Width,
            p2Width
        };
        int[] yPoints = new int[]{
            (int)(halfHeight - p1Height / 2),
            (int)(halfHeight + p1Height / 2),
            (int)(halfHeight + p2Height / 2),
            (int)(halfHeight - p2Height / 2)
        };
        graphics.setColor(WALL_COLOR);
        graphics.fillPolygon(xPoints, yPoints, 4);
    }
    
}
