import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ConvexHullTest {

    //Expected outputs are constructed by scipy.spatial.ConvexHull in Python.

    ConvexHull g; //Graham Scan
    ConvexHull j; //Jarvis March

    String testExpectedHull = "expect/0.txt";
    Point[] hullExpected;

    private Point[] getPointsFromFile(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner in = new Scanner(f);
        int ptsNum = 0;
        while (in.hasNextLine()) {
            String ln = in.nextLine();
            if (ln.length() > 2)
                ptsNum++;
        }
        in.close();
        in = new Scanner(f);
        Point[] opt = new Point[ptsNum];

        for (int i = 0; i < ptsNum; i++) {
            opt[i] = new Point(in.nextInt(), in.nextInt());
        }
        in.close();
        return opt;
    }


    @Test
    public void toStringTest() throws Exception {
        //just to test the format is correct
        g = new GrahamScan("ipt/0.txt");
        g.constructHull();
        j = new JarvisMarch("ipt/0.txt");
        j.constructHull();

        System.out.println("Check toString() format.");
        System.out.println("Graham: ");
        System.out.println(g);

        System.out.println("Jarvis: ");
        System.out.println(j);
        assertTrue(true);
    }

    @Test
    public void hullTest() throws Exception {
        File iptDir = new File("ipt/");
        File eptDir = new File("expect/");
        String[] ipts = iptDir.list();
        String[] epts = eptDir.list();
        for (int i = 0; i < ipts.length; i++) {
            String ipt = "ipt/" + ipts[i];
            String expect = "expect/" + epts[i];

            g = new GrahamScan(ipt);
            g.constructHull();
            j = new JarvisMarch(ipt);
            j.constructHull();
            hullExpected = getPointsFromFile(expect);

            String outName = "hull.txt";
            g.writeHullToFile();
            assertArrayEquals(hullExpected, getPointsFromFile(outName));

            j.constructHull();
            j.writeHullToFile();
            assertArrayEquals(hullExpected, getPointsFromFile(outName));

        }


    }


    @Test
    public void removeDuplicates() throws Exception {
        String testFile = "ipt/0.txt";

        //add some duplicate manually
        int dupNum = 20;
        Point[] pts = getPointsFromFile(testFile);
        Point[] ptsDup = Arrays.copyOf(pts, pts.length + dupNum);
        Random rand = new Random();
        for (int i = 0; i < dupNum; i++) {
            int ind = rand.nextInt(pts.length);
            ptsDup[i + pts.length] = pts[ind];
        }

        ConvexHull noDup = new GrahamScan(pts);
        ConvexHull dup = new GrahamScan(ptsDup);
        assertArrayEquals(noDup.pointsNoDuplicate, dup.pointsNoDuplicate);

    }

}
