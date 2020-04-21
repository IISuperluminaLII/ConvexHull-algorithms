import static org.junit.Assert.*;

import org.junit.Test;

public class PolarAngleComparatorTest {

    private Point lowestPoint = new Point(10, 12);
    private Point p1 = new Point(10, 12);
    private Point p2 = new Point(12, 12);
    private Point p3 = new Point(14, 12);
    private Point p4 = new Point(11, 13);
    private Point p5 = new Point(13, 15);
    private Point p6 = new Point(9, 17);
    private Point p7 = new Point(8, 13);
    private Point p8 = new Point(10, 12);
    private Point p9 = new Point(11, 13);

    //test flag=true
    //expected: 1=8<2<3<4=9<5<6<7
    @Test
    public void compareTrueTest() {
        PolarAngleComparator comp = new PolarAngleComparator(lowestPoint, true);
        assertTrue(comp.compare(p1, p8) == 0);
        assertTrue(comp.compare(p8, p2) < 0);
        assertTrue(comp.compare(p2, p3) < 0);
        assertTrue(comp.compare(p3, p4) < 0);
        assertTrue(comp.compare(p4, p9) == 0);
        assertTrue(comp.compare(p5, p9) > 0);
        assertTrue(comp.compare(p6, p5) > 0);
        assertTrue(comp.compare(p7, p6) > 0);
    }


    //test flag=flase
    //expected: 3<2<5<4=9<6<7<1=8
    @Test
    public void compareFalseTest() {
        PolarAngleComparator comp = new PolarAngleComparator(lowestPoint, false);
        assertTrue(comp.compare(p3, p2) < 0);
        assertTrue(comp.compare(p2, p5) < 0);
        assertTrue(comp.compare(p5, p4) < 0);
        assertTrue(comp.compare(p4, p9) == 0);
        assertTrue(comp.compare(p6, p9) > 0);
        assertTrue(comp.compare(p7, p6) > 0);
        assertTrue(comp.compare(p1, p7) > 0);
        assertTrue(comp.compare(p8, p1) == 0);
    }

}
