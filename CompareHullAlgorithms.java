import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random; 


public class CompareHullAlgorithms 
{
	/**
	 * Repeatedly take points either randomly generated or read from files. Perform Graham's scan and 
	 * Jarvis' march over the input set of points, comparing their performances.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) 
	{		
		// TODO 
		// 
		// Conducts multiple rounds of convex hull construction. Within each round, performs the following: 
		// 
		//    1) If the input are random points, calls generateRandomPoints() to initialize an array 
		//       pts[] of random points. Use pts[] to create two objects of GrahamScan and JarvisMarch, 
		//       respectively.
		//
		//    2) If the input is from a file, construct two objects of the classes GrahamScan and  
		//       JarvisMarch, respectively, using the file.     
		//
		//    3) Have each object call constructHull() to build the convex hull of the input points.
		//
		//    4) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 4 of the project description. 
		// 	
		ConvexHull[] algorithms = new ConvexHull[2];
		
		
//		boolean fileInput = false;
//		int choice = 0;
//		Random ran = new Random();
//		int rans = ran.nextInt(1000);
//		int random = rans - 1000;
//		Point[] genPoints = generateRandomPoints(20,new Random());
//		
//		
//		
//		algorithms[0] = new GrahamScan(genPoints);  
//		algorithms[1] = new JarvisMarch(genPoints);
		
		//System.out.println(algorithms[1].stats());
//		algorithms[0].constructHull();
//		algorithms[1].constructHull();
//		algorithms[0].draw();
//		algorithms[1].draw();
//		System.out.println(algorithms[0].stats());
//		System.out.println(algorithms[1].stats());
		
		
//		Point p1 = new Point(0,0);
//		Point p2 = new Point(2,2);
		
//		PolarAngleComparator pcomp = new PolarAngleComparator(p1,true);
		
//		System.out.println(pcomp.compare(new Point(3,3), new Point(2,2)));
		Random samRand = new Random();

		
		
		Scanner scanIn = new Scanner(System.in);
		String greeting = "\nEnter Choice:\n1)Rand Points input\n2)Read Points From File\n3)Exit\n";
		String inputFileName = "Please Enter File Name\n";
		String inputNumPoints = "\nPlease Enter Number of Points\n";
		
		String seperator = "-------------------------------------------";
		String StatMessege = "Algorithm\t\tPoints\t\tTime";
		
		
		System.out.println(greeting);
		int scanInt = scanIn.nextInt();
		
		while(scanInt < 3){
			
			if(scanInt == 2){
				
				System.out.println(inputFileName);
				String fileName = scanIn.next();
				
				try {
					algorithms[0] = new GrahamScan(fileName);
					algorithms[1] = new JarvisMarch(fileName);
				}catch (InputMismatchException | FileNotFoundException e) {
					
					//TODO Auto-generated catch block
					
					e.printStackTrace();
					
				}
				
				algorithms[0].constructHull();
				algorithms[1].constructHull();
				
				System.out.println(seperator);
				System.out.println("\n"+StatMessege+"\n");
				
				algorithms[0].draw();
				algorithms[1].draw();
				
				
				
				System.out.println(algorithms[0].stats());
				System.out.println(algorithms[1].stats());
				
				
			}
			else{
				System.out.println(inputNumPoints);
				int numPoints = scanIn.nextInt();
				Point[] Rangen = generateRandomPoints(numPoints,samRand);
				algorithms[0] = new GrahamScan(Rangen);
				algorithms[1] = new JarvisMarch(Rangen);
				algorithms[0].constructHull();
				algorithms[0].draw();
				algorithms[1].constructHull();
				algorithms[1].draw();
				System.out.println(algorithms[0].stats());
				System.out.println(algorithms[1].stats());
			}
			
			
			System.out.println(greeting);
			scanInt=scanIn.nextInt();
			System.out.println();
			if(scanInt == 3)
				break;
			
		}
		
		scanIn.close();
		
		
		// Within a hull construction round, have each algorithm call the constructHull() and draw()
		// methods in the ConvexHull class.  You can visually check the result. (Windows 
		// have to be closed manually before rerun.)  Also, print out the statistics table 
		// (see Section 4). 
		
	}
	
	
	/**
	 * This method generates a given number of random points to initialize randomPoints[].
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		
		if(numPts < 1)
			throw new IllegalArgumentException("Num Points < 1");
		Point[] points = new Point[numPts];
		
		for(int i = 0; i < numPts;i++){
			
			points[i] = new Point(new Point(50-rand.nextInt(100),50-rand.nextInt(100)));
			
		}
		
		return points; 
		// TODO 
	}
}
