import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.InputMismatchException;
import java.util.Iterator;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Random; 
import java.util.Scanner;
import java.util.Set;

public abstract class ConvexHull
{
	// ---------------
	// Data Structures 
	// ---------------
	protected String algorithm;  // has value either "Graham's scan" or "Jarvis' march". Initialized by a subclass.
	
	protected long time;         // execution time in nanoseconds
	
	private boolean running = false;
	private long elapsedTime = 0L;
	private long mostRecentStartTime = -1;
	private boolean started = false;
	
	/**
	 * The array points[] holds an input set of Points, which may be randomly generated or 
	 * input from a file.  Duplicates are possible. 
	 */
	public Point[] points;    
	

	/**
	 * Lowest point from points[]; and in case of a tie, the leftmost one of all such points. 
	 * To be set by a constructor. 
	 */
	protected Point lowestPoint; 

	
	/**
	 * This array stores the same set of points from points[] with all duplicates removed. 
	 * These are the points on which Graham's scan and Jarvis' march will be performed. 
	 */
	protected Point[] pointsNoDuplicate; 
	
	
	/**
	 * Vertices of the convex hull in counterclockwise order are stored in the array 
	 * hullVertices[], with hullVertices[0] storing lowestPoint. 
	 */
	protected Point[] hullVertices;
	
	
	protected QuickSortPoints quicksorter;  // used (and reset) by this class and its subclass GrahamScan
	
	protected int lowestPointIndex = 0;

	
	
	// ------------
	// Constructors
	// ------------
	
	
	/**
	 * Constructor over an array of points.  
	 * 
	 *    1) Store the points in the private array points[].
	 *    
	 *    2) Initialize quicksorter. 
	 *    
	 *    3) Call removeDuplicates() to store distinct points from the input in pointsNoDuplicate[].
	 *    
	 *    4) Set lowestPoint to pointsNoDuplicate[0]. 
	 * 
	 * @param pts
	 * @throws IllegalArgumentException  if pts.length == 0
	 */
	public ConvexHull(Point[] pts) throws IllegalArgumentException 
	{
		
		
		
		if(pts.length == 0)
			throw new IllegalArgumentException("pts.length == 0");
		points = new Point[pts.length];
		
		int counter = 0;
		for(Point pt : pts){
			this.points[counter] = new Point(pts[counter]);
			counter = counter+1;
		}
		
//									FIND DUPLICATES
		
		this.removeDuplicates();
		
		pointsNoDuplicate = new Point[points.length];
		
		int counters = 0;
		for(Point pt : points){
			pointsNoDuplicate[counters] = pt;
			counters = counters+1;
			
		}

        Point lowest = points[0];

        for(int i = 1; i < points.length; i++) {

            Point temp = points[i];

            if(temp.getY() < lowest.getY() || (temp.getY() == lowest.getY() && temp.getX() < lowest.getX())) {
                lowest = temp;
                lowestPointIndex = i;
            }
        }

        lowestPoint = lowest;
		
		
			
		}
	
	/**
	 * Read integers from an input file.  Every pair of integers represent the x- and y-coordinates 
	 * of a point.  Generate the points and store them in the private array points[]. The total 
	 * number of integers in the file must be even.
	 * 
	 * You may declare a Scanner object and call its methods such as hasNext(), hasNextInt() 
	 * and nextInt(). An ArrayList may be used to store the input integers as they are read in 
	 * from the file.  
	 * 
	 * Perform the operations 1)-4) for the first constructor. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public ConvexHull(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		// TODO 
		
		File toRead = new File(inputFileName);
		// Call scanner class to read the file

		if (!toRead.exists())
			throw new FileNotFoundException("Null File");

		Scanner scanFile = new Scanner(toRead);

		// Set up a dynamic ArrayList to store data with dynamic lengths
		ArrayList<Point> toadd = new ArrayList<Point>();

		// While has next checks if there exists a ASCII line

		// I was initili
		while (scanFile.hasNext()) {
			String line = scanFile.nextLine();
			Scanner scanLine = new Scanner(line);

			toadd.add(new Point(scanLine.nextInt(), scanLine.nextInt()));
			scanLine.close();
		}
		scanFile.close();
		
//		int size = toadd.size();
//		if (toadd.size() % 2 != 0)
//			throw new InputMismatchException("Odd number of points");

		//System.out.println(toadd.size());
		this.points = toadd.toArray(new Point[toadd.size()]);
		
		removeDuplicates();
		
		//LEFTMOST POINT
		
		//Some algorithym to serch for the lowest point that includes check for X val. O(n) complexity
        Point lowest = points[0];

        for(int i = 1; i < points.length; i++) {

            Point temp = points[i];

            if(temp.getY() < lowest.getY() || (temp.getY() == lowest.getY() && temp.getX() < lowest.getX())) {
                lowest = temp;
                lowestPointIndex = i;
            }
        }

        lowestPoint = lowest;
		
		
		

	}

	
	/**
	 * Construct the convex hull of the points in the array pointsNoDuplicate[]. 
	 */
	public abstract void constructHull(); 

	
		
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <convex hull algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * Graham's scan   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 4 of the project description. 
	 */
	public String stats()
	{
		
		StringBuilder sb = new StringBuilder();
		
		int numPoints = points.length;
		long timeToConstructHull = time;
		
		sb.append(this.algorithm);
		sb.append("\t\t");
		sb.append(numPoints);
		sb.append("\t\t");
		sb.append(getElapsedTime());
		
		
		return sb.toString(); 
		// TODO 
	}
	
	
	public void start() {
		if (running) {
			return;
		}
		elapsedTime = 0;
		mostRecentStartTime = System.nanoTime();
		running = true;
		started = true;
	}
	
        /***
         * Stops timer, storing elapsed time.
         */
	public void stop() {
		if (!running) {
			return;
		}
		elapsedTime += System.nanoTime() - mostRecentStartTime;
		running = false;
	}
	public double getElapsedTime() {
		//if (!started) {
		//	throw new IllegalStateException();
		//}
		if (running) {
			return System.nanoTime() - mostRecentStartTime;
		}
		double timeDiv = (double)elapsedTime;
		return timeDiv;
	}
	
	
	/**
	 * The string displays the convex hull with vertices in counterclockwise order starting at  
	 * lowestPoint.  When printed out, it will list five points per line with three blanks in 
	 * between. Every point appears in the format "(x, y)".  
	 * 
	 * For illustration, the convex hull example in the project description will have its 
	 * toString() generate the output below: 
	 * 
	 * (-7, -10)   (0, -10)   (10, 5)   (0, 8)   (-10, 0)   
	 * 
	 * lowestPoint is listed only ONCE.  
	 */
	public String toString()
	{
		// TODO 
		StringBuilder br = new StringBuilder();
		
		for(int i =0; i < points.length;i++){
			
			br.append(points[i].toString());
			br.append("\t ");
			
			if(!(i==0)&&(i+1)%5 == 0)
				br.append("\n");
			
			
		}
		return br.toString();
	}
	
	
	/** 
	 * 
	 * Writes to the file "hull.txt" the vertices of the constructed convex hull in counterclockwise 
	 * order.  These vertices are in the array hullVertices[], starting with lowestPoint.  Every line
	 * in the file displays the x and y coordinates of only one point.  
	 * 
	 * For instance, the file "hull.txt" generated for the convex hull example in the project 
	 * description will have the following content: 
	 * 
     *  -7 -10 
     *  0 -10
     *  10 5
     *  0  8
     *  -10 0
	 * 
	 * The generated file is useful for debugging as well as grading. 
	 * 
	 * Called only after constructHull().  
	 * 
	 * 
	 * @throws IllegalStateException  if hullVertices[] has not been populated (i.e., the convex 
	 *                                   hull has not been constructed)
	 */
	public void writeHullToFile() throws IllegalStateException,FileNotFoundException
	{
		 String outputFileName = algorithm+".txt";
		 File fOpen = new File(outputFileName);
		 PrintWriter fWrite = new PrintWriter(fOpen);
		 fWrite.print(this.toString());
		 fWrite.flush();
		 System.out.println("___________________________________File Writed_________________________________");
		 
		 fWrite.close();
	}
	

	/**s
	 * Draw the points and their convex hull.  This method is called after construction of the 
	 * convex hull.  You just need to make use of hullVertices[] to generate a list of segments 
	 * as the edges. Then create a Plot object to call the method myFrame().  
	 */
	public void draw()
	{		
		int numSegs = hullVertices.length;  // number of segments to draw is half as segment consists of two points

		// Based on Section 4.1, generate the line segments to draw for display of the convex hull.
		// Assign their number to numSegs, and store them in segments[] in the order. 
		Segment[] segments = new Segment[numSegs];
		
		//System.out.println("\nCONVEX_HULL>>>DRAW<<<FUNCT\n");
		// TODO 
		
		//quicksorter = new QuickSortPoints(points);
		
		//quicksorter.quickSort(new PolarAngleComparator(lowestPoint));
		
		for(int i = 0; i < segments.length;i++){
			
			if(i < hullVertices.length-1)
				segments[i] = new Segment(this.hullVertices[i],this.hullVertices[i+1]);
			else
				segments[i] = new Segment(this.hullVertices[0],this.hullVertices[i]);
		}
		// The following statement creates a window to display the convex hull.
		Plot.myFrame(points, segments, getClass().getName());
		
	}
	
	/**
	 * Sort the array points[] by y-coordinate in the non-decreasing order.  Have quicksorter 
	 * invoke quicksort() with a comparator object which uses the compareTo() method of the Point 
	 * class. Copy the sorted sequence onto the array pointsNoDuplicate[] with duplicates removed.
	 *     
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void removeDuplicates()
	{
		// TODO 
		
		Set<Point> remDup = new HashSet<Point>();
		
		pointsNoDuplicate = new Point[points.length];
		
		for(Point rem : points){
			remDup.add(rem);
		}
		Iterator<Point> it = remDup.iterator();
		int counter = 0;
		points = new Point[remDup.size()];
		while(it.hasNext()){
			
			points[counter] = it.next();
			counter = counter +1;
			
		}
		
		
		
		Comparator<Point> comp = (a, b) -> a.compareTo(b);

		//System.out.println(points.length);
		quicksorter = new QuickSortPoints(points);
		
		quicksorter.quickSort(comp);
		
		quicksorter.getSortedPoints(pointsNoDuplicate);
		
		
	}
}
