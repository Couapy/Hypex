package cloud.marchand.hypex.core.models.geom;

public class PointOfView extends Point {

    public double angle;
    public double angleOfView;

    public PointOfView() {
        super();
        angle = 0d;
        angleOfView = Math.PI / 2;
    }

    public PointOfView(double x, double y, double angle, double angleOfView) {
        super(x, y);
        this.angle = angle;
        this.angleOfView = angleOfView;
    }
    
}
