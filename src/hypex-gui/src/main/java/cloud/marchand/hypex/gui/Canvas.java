package cloud.marchand.hypex.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel {

    private static final int POINT_WIDTH = 10;
    public static final int WIDTH_SQUARE = 100;
    private Map map;

    public Canvas(Map map) {
        this.map = map;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setBackground(Color.BLACK);

        // Paint walls
        graphics.setColor(Color.BLUE);
        boolean[][] walls = map.getMap();
        for (int x = 0; x < walls.length; x++) {
            for (int y = 0; y < walls[0].length; y++) {
                if (walls[x][y]) {
                    graphics.fillRect(x * WIDTH_SQUARE, y * WIDTH_SQUARE, WIDTH_SQUARE, WIDTH_SQUARE);
                }
            }
        }

        // Paint edges and nodes
        Edge[] edges = map.getEdges();
        for (int i = 0; i < edges.length; i++) {
            graphics.setColor(Color.GREEN);
            graphics.drawLine((int) edges[i].getA().getX() * WIDTH_SQUARE, (int) edges[i].getA().getY() * WIDTH_SQUARE,
                    (int) edges[i].getB().getX() * WIDTH_SQUARE, (int) edges[i].getB().getY() * WIDTH_SQUARE);
            graphics.setColor(Color.RED);
            graphics.fillOval((int) (edges[i].getA().getX() * WIDTH_SQUARE - POINT_WIDTH / 2),
                    (int) (edges[i].getA().getY() * WIDTH_SQUARE - POINT_WIDTH / 2), POINT_WIDTH, POINT_WIDTH);
            graphics.fillOval((int) (edges[i].getB().getX() * WIDTH_SQUARE - POINT_WIDTH / 2),
                    (int) (edges[i].getB().getY() * WIDTH_SQUARE - POINT_WIDTH / 2), POINT_WIDTH, POINT_WIDTH);
        }

    }

}
