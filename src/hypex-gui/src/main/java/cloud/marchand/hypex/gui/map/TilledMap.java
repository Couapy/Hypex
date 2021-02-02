package cloud.marchand.hypex.gui.map;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cloud.marchand.hypex.gui.Edge;

/**
 * Represent a tilled Map.
 */
public class TilledMap extends Map {

    /**
     * Cell structure.
     */
    private class Cell {
        public Cell(boolean wall) {
            this.wall = wall;
		}
		public boolean wall;
        public Edge topEdge;
        public Edge bottomEdge;
        public Edge leftEdge;
        public Edge rightEdge;
    }

    /**
     * Load TilledMap from file
     * @param file file path
     * @throws IOException the file doesn't exists
     */
    public TilledMap(String file) throws IOException {
        super();
        Cell[][] map = readFile(file);
        generateEdges(map);
        readData(map);
    }

    /**
     * Read the file
     * @param file file path
     * @return tilled map
     * @throws IOException the file doesn't exists
     */
    private Cell[][] readFile(String file) throws IOException {
        InputStream in = Map.class.getClassLoader().getResourceAsStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String content = "", line;
        int minCols = -1, maxCols = -1, rows = 0;

        // Read the file
        while ((line = reader.readLine()) != null) {
            int cols = line.length();
            if (cols < minCols || minCols == -1) {
                minCols = cols;
            }
            if (cols > maxCols || maxCols == -1) {
                maxCols = cols;
            }
            if (line.length() == 0) {
                continue;
            }
            content = content.concat(line + "\n");
            rows++;
        }
        content = content.substring(0, content.length() - 1);

        if (minCols != maxCols) {
            throw new IOException("The map isn't rectangular.");
        }

        // Parse the string
        Cell[][] map = new Cell[maxCols][rows];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < maxCols; x++) {
                Cell cell = new Cell(content.charAt(y * (maxCols + 1) + x) == '*');
                System.out.print(cell.wall ? "* " : "  ");
                map[x][y] = cell;
            }
            System.out.println();
        }
        return map;
    }

    /**
     * Merge together edges that need
     * @param map tilled map
     */
    private void generateEdges(Cell[][] map) {
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (map[x][y].wall) {
                    // Left edges
                    if (x - 1 >= 0 && !map[x - 1][y].wall) {
                        if (y - 1 >= 0 && map[x][y - 1].leftEdge != null) {
                            map[x][y - 1].leftEdge.setB(new Point(x, y + 1));
                            map[x][y].leftEdge = map[x][y - 1].leftEdge;
                        } else {
                            map[x][y].leftEdge = new Edge(new Point(x, y), new Point(x, y + 1));
                        }
                    }

                    // Right edges
                    if (x + 1 < map.length && !map[x + 1][y].wall) {
                        if (y - 1 >= 0 && map[x][y - 1].rightEdge != null) {
                            map[x][y - 1].rightEdge.setB(new Point(x + 1, y + 1));
                            map[x][y].rightEdge = map[x][y - 1].rightEdge;
                        } else {
                            map[x][y].rightEdge = new Edge(new Point(x + 1, y), new Point(x + 1, y + 1));
                        }
                    }

                    // Top edges
                    if (y - 1 >= 0 && !map[x][y - 1].wall) {
                        if (x - 1 >= 0 && map[x - 1][y].topEdge != null) {
                            map[x - 1][y].topEdge.setB(new Point(x + 1, y));
                            map[x][y].topEdge = map[x - 1][y].topEdge;
                        } else {
                            map[x][y].topEdge = new Edge(new Point(x, y), new Point(x + 1, y));
                        }
                    }

                    // Bottom edges
                    if (y + 1 < map[0].length && !map[x][y + 1].wall) {
                        if (x - 1 >= 0 && map[x - 1][y].bottomEdge != null) {
                            map[x - 1][y].bottomEdge.setB(new Point(x + 1, y + 1));
                            map[x][y].bottomEdge = map[x - 1][y].bottomEdge;
                        } else {
                            map[x][y].bottomEdge = new Edge(new Point(x, y + 1), new Point(x + 1, y + 1));
                        }
                    }
                }
            }
        }
    }

    /**
     * Save edges and nodes in the TilledMap object.
     * @param map tilled map
     */
    private void readData(Cell[][] map) {
        // Get edges
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (map[x][y].leftEdge != null) {
                    edges.add(map[x][y].leftEdge);
                }
                if (map[x][y].rightEdge != null) {
                    edges.add(map[x][y].rightEdge);
                }
                if (map[x][y].topEdge != null) {
                    edges.add(map[x][y].topEdge);
                }
                if (map[x][y].bottomEdge != null) {
                    edges.add(map[x][y].bottomEdge);
                }
            }
        }
        // Get nodes
        for (Edge edge : edges) {
            points.add(edge.getA());
            points.add(edge.getB());
        }

        // Add default frame
        Point a, b, c, d;
        a = new Point(0, 0);
        b = new Point(0, map[0].length);
        c = new Point(map.length, map[0].length);
        d = new Point(map.length, 0);
        edges.add(new Edge(a, b));
        edges.add(new Edge(b, c));
        edges.add(new Edge(c, d));
        edges.add(new Edge(d, a));
    }

}
