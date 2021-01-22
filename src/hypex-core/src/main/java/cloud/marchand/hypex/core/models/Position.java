package cloud.marchand.hypex.core.models;

/**
 * Represent a position inside the game
 */
public class Position {

    /**
     * 2D coordinate x
     */
    private double x;

    /**
     * 2D coordinate y
     */
    private double y;

    /**
     * Create a position at the origin
     */
    public Position() {
        this.setX(0);
        this.setY(0);
    }

    /**
     * Create a position at the coordinates
     * @param x 2D coordinate x
     * @param y 2D coordinate y
     */
    public Position(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * Give the x coordinate
     * @return x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Defines the x coordinate
     * @param x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Give the y coordinate
     * @return y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Defines the y coordinate
     * @param y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

}
