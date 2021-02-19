package cloud.marchand.hypex.client.map;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cloud.marchand.hypex.core.models.geom.Point;
import cloud.marchand.hypex.core.models.geom.Segment;

public class MapTest {

    private Map map;

    /**
     * Create a map with a single square.
     */
    @Before
    public void setup() {
        map = new Map(){};
        Point[] points = new Point[4];
        Set<Segment> segments = new HashSet<>();

        points[0] = new Point(0d, 0d);
        points[1] = new Point(1d, 0d);
        points[2] = new Point(1d, 1d);
        points[3] = new Point(0d, 1d);

        segments.add(new Segment(points[0], points[1]));
        segments.add(new Segment(points[1], points[2]));
        segments.add(new Segment(points[2], points[3]));
        segments.add(new Segment(points[3], points[0]));

        map.setPoints(new HashSet<>(Arrays.asList(points)));
        map.setSegments(segments);
    }

    /**
     * Verify the projection from the inside of the square.
     */
    @Ignore
    @Test
    public void getSquareProjections() {
        Point pov = new Point(0.5d, 0.5d);
        Point res;
        System.out.println(map.getProjection(pov, Math.PI / 2));
        for (double angle = -Math.PI; angle < Math.PI; angle += Math.PI / 6) {
            res = map.getProjection(pov, angle);
            assertNotNull("the intersection should exists (angle = " + angle + ")", res);
        }
    }
    
}
