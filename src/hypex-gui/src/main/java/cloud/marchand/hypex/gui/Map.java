package cloud.marchand.hypex.gui; 

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Map {

    public static void main(String[] args) throws IOException {
        loadFromFile("./testMap.txt");
    }

    public static Map loadFromFile(String file) throws IOException {
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
        boolean[][] array = new boolean[maxCols][rows];
        for (int x = 0; x < maxCols; x++) {
            for (int y = 0; y < rows; y++) {
                array[x][y] = content.charAt(y * (maxCols + 1) + x) == '*';
            }
        }

        return new Map(array);
    }

    private Point[] points;
    private Edge[] edges;
    private boolean[][] map;

    public Map() {
        map = new boolean[0][0];
        points = new Point[0];
    }
    
    public Map(boolean[][] map) {
        this();
        this.map = map;
        convertTilledMap(map);
    }

    private void convertTilledMap(boolean[][] map) {
        List<Edge> edges = new ArrayList<>();

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {

                if (map[x][y]) {
                    if (x-1 >= 0 && !map[x-1][y]) {
                        edges.add(new Edge(new Point(x, y), new Point(x, y+1)));
                    }
                    if (y-1 >= 0 && !map[x][y-1]) {
                        edges.add(new Edge(new Point(x, y), new Point(x+1, y)));
                    }
                    if (x+1 < map.length && !map[x+1][y]) {
                        edges.add(new Edge(new Point(x+1, y), new Point(x+1, y+1)));
                    }
                    if (y+1 < map[0].length && !map[x][y+1]) {
                        edges.add(new Edge(new Point(x, y+1), new Point(x+1, y+1)));
                    }
                }
                
            }
        }

        this.edges = edges.toArray(new Edge[0]);
    }


    public Point[] getPoints() {
        return points;
    }

	public int getWidth() {
		return map.length;
	}

	public int getHeight() {
		return map[0].length;
    }

	public boolean[][] getMap() {
		return map;
	}

	public Edge[] getEdges() {
		return edges;
	}

}
