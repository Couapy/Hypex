package cloud.marchand.hypex.core.models.geom;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LinearFunctionTest {

    public static final double TRESHOLD = 0.00001;

    private LinearFunction lineA;
    private LinearFunction lineB;
    private Segment segment;

    @Before
    public void setup() {
        lineA = new LinearFunction(0d, 1d);
        lineB = new LinearFunction(1d, 0d);
        segment = new Segment(new Point(2d, 1.5d), new Point(2d, 0d));
    }

    @Test
    public void getIntersectionPointLines() {
        Point res;
        res = lineA.getIntersectionPoint(lineB);
        assertNotNull(res);
        res = lineB.getIntersectionPoint(lineA);
        assertNotNull(res);
    }

    @Test
    public void getIntersectionPointSegment() {
        Point res;
        res = lineA.getIntersectionPoint(segment);
        assertNotNull(res);
        res = lineB.getIntersectionPoint(segment);
        assertNull(res);
    }

    @Test
    public void getIntersectionPointSegmentHorizontal() {
        LinearFunction line = new LinearFunction(0d, 3.5d);
        Segment segment = new Segment(new Point(4, 3), new Point(4, 4));
        Point res = line.getIntersectionPoint(segment);
        assertNotNull(res);
        assertTrue("Intersection should be (4, 3.5)", Math.abs(res.x - 4d) <= TRESHOLD && Math.abs(res.y - 3.5d) <= TRESHOLD);
    }

    @Test
    public void getIntersectionPointSegmentVertical() {
        LinearFunction line = new LinearFunction(0d, 2.5d);
        Segment segment = new Segment(new Point(8d, 0d), new Point(8d, 9d));
        Point res = line.getIntersectionPoint(segment);
        assertNotNull("It should exist an intersection", res);
        assertTrue("Intersection should be (8, 2.5)", Math.abs(res.x - 8d) <= TRESHOLD && Math.abs(res.y - 2.5d) <= TRESHOLD);
    }
    
}
