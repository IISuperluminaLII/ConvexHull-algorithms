import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.*;

public class QuickSortPointsTest {

    int numPts = 1000;
    Point[] pts = new Point[numPts];
    Point[] ptsExpected = new Point[numPts];
    Comparator<Point> comp;

    @Before
    public void setUp() {
        Random rand = new Random();

        for (int i = 0; i < numPts; i++) {
            pts[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
            ptsExpected[i] = new Point(pts[i]);
        }

        comp = new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.compareTo(o2);
            }
        };
    }

    @Test
    public void getSortedPoints() throws Exception {

    }

    @Test
    public void quickSort() throws Exception {


        QuickSortPoints q = new QuickSortPoints(pts);
        q.quickSort(comp);
        q.getSortedPoints(pts);
        
        
        
        Arrays.sort(ptsExpected, comp);
        assertArrayEquals(ptsExpected, pts);
    }

}
