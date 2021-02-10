package cloud.marchand.hypex.core.models.geom;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class LinearFunctionTest {

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
    
}
