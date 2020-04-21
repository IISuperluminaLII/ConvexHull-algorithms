import java.util.Arrays;
import java.util.Comparator;

public class QuickSortPoints
{
	public Point[] points;  	// Array of points to be sorted.
	

	/**
	 * Constructor takes an array of Point objects. 
	 * 
	 * @param pts
	 */
	QuickSortPoints(Point[] pts)
	{
		// ToDo 
		points = new Point[pts.length];
		int counter = 0;
		for(Point pt : pts){
			
			points[counter] = pt;
			counter = counter +1;
		}
		
		
	}
	
	
	/**
	 * Copy the sorted array to pts[]. 
	 * 
	 * @param pts  array to copy onto
	 */
	void getSortedPoints(Point[] pts)
	{
		int counter = 0;
		for(Point pt : points){
			
			pts[counter] = pt;
			counter = counter +1;
		}
		
	}

	
	/**
	 * Perform quicksort on the array points[] with a supplied comparator. 
	 *
	 * @param comp
	 */
	public void quickSort(Comparator<Point> comp)
	{
		// TODO
		quickSortRec(0,points.length-1,comp);
		
//		System.out.println(comp.toString());
//		for(Point pt : points){
//			System.out.print(pt.toString());
//		}
//		System.out.println("\n\n");
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last, Comparator<Point> comp)
	{
		if(first >= last) return;
		
		int pivot = partition(first, last, comp);

		// if(first < pivot -1)
		quickSortRec(first, pivot - 1,comp);
		// if(pivot < last)
		quickSortRec(pivot, last,comp);
	
	}
	

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last, Comparator<Point> comp)
	{
		// TODO 
		Point pivot = points[(first + last) / 2];

		while (first <= last) {
			// System.out.println("fewfwe");
			while (comp.compare(points[first], pivot) < 0) {
				first++;
			}
			while (last > 0 && comp.compare(points[last], pivot) > 0) {
				last--;
			}
			if (first <= last) {
				exchangePoints(first, last);
				first++;
				last--;
			}

		}
		return first;
		
		
		
		
		
	}
	
	private void exchangePoints(int i, int j){
        Point temp = points[i];
        points[i] = points[j];
        points[j] = temp;
	}
}


