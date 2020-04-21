import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class PointTest {

    Point p, p1, p2, p3, p4;

    @Before
    public void setUp() {
        p = new Point(2, 3);
        p1 = new Point(2, 3);
        p2 = new Point(4, 5);
        p3 = new Point(3, 3);
        p4 = new Point(2, 0);
    }

    @Test
    public void equals() throws Exception {
        assertEquals(p, p1);
        assertTrue(!p2.equals(p));
    }

    @Test
    public void compareTo() throws Exception {
        assertTrue(p.compareTo(p1) == 0);
        assertTrue(p.compareTo(p2) < 0);
        assertTrue(p.compareTo(p3) < 0);
        assertTrue(p.compareTo(p4) > 0);
    }

    @Test
    public void toStringTest() {
        assertEquals("(2, 3)", p1.toString());
    }

}
