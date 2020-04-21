import java.util.Comparator;

public class PolarAngleComparator implements Comparator<Point>
{
	private Point referencePoint; 
	private boolean flag = true;
	
	/**
	 * 
	 * @param p reference point
	 */
	public PolarAngleComparator(Point p, boolean flag)
	{
		referencePoint = p; 
		this.flag = flag;
	}
	
	/**
	 * Use cross product and dot product to implement this method.  Do not take square roots 
	 * or use trigonometric functions. See earlier PowerPoint notes for Project 2 on how to 
	 * carry out cross and dot products. Calls private methods crossProduct() and dotProduct(). 
	 * 
	 * Precondition: both p1 and p2 are different from referencePoint. 
	 * 
	 * @param p1
	 * @param p2
	 * @return  0 if p1 and p2 are the same point
	 *         -1 if one of the following three conditions holds: 
	 *                a) the cross product between p1 - referencePoint and p2 - referencePoint 
	 *                   is greater than zero. 
	 *                b) the above cross product equals zero, flag == true, and p1 is closer to 
	 *                   referencePoint than p2 is. 
	 *                c) the above cross product equals zero, flag == false, and p1 is further 
	 *                   from referencePoint than p2 is.   
	 *          1  otherwise. 
	 * 
	 */
	public int compare(Point p1, Point p2)
	{
		// TODO
		if(flag){
		   if(equals(p1,p2)) 
			   return 0;
		   //closer
		   else if ((comparePolarAngle(p1, p2) == -1) || (comparePolarAngle(p1, p2) == 0) && (compareDistance(p2, p1) == 1)) 
			   
			   return -1;
		   else	
			   return 1;
		}else{
			   if(equals(p1,p2)) 
				   return 0;
			   //Further
			   else if (comparePolarAngle(p1, p2) == -1 || (comparePolarAngle(p1, p2) == 0) && (compareDistance(p2, p1) == -1))
				   
				   return -1;
			   else	
				   return 1;
			
		}
	}
	
	
	/**
	 * Compare the polar angles of two points p1 and p2 with respect to referencePoint.  Use 
	 * cross products.  Do not use trigonometric functions. 
	 * 
	 * Ought to be private but made public for testing purpose. 
	 * 
	 * @param p1
	 * @param p2
	 * @return    0  if p1 and p2 have the same polar angle.
	 * 			 -1  if p1 equals referencePoint or its polar angle with respect to referencePoint
	 *               is less than that of p2. 
	 *            1  otherwise. 
	 */
    public int comparePolarAngle(Point p1, Point p2) 
    {
    	// TODO 
//    	int p1Angle = crossProduct(this.referencePoint,p1);
//    	int p2Angle = crossProduct(this.referencePoint,p2);
//    	
//    	int angp1Cp2C = p1Angle * p2Angle;
//    	
//        if      (angp1Cp2C < 0) return -1;
//        else if (angp1Cp2C > 0) return +1;
//        else              		 return  0;
    	
    	   if(crossProduct(p1, p2) == 0)									  	
    		   return 0;
    	   else if(p1.equals(referencePoint) || crossProduct(p1, p2) > 0) 	
    		   return -1;
    	   else 															
    		   return 1;
    }
    
    private boolean equals(Point p1, Point p2){
    	
    	return (p1.getX() == p2.getX()) && (p1.getY()==p2.getY());
    	
    }
    
    /**
     * Compare the distances of two points p1 and p2 to referencePoint.  Use dot products. 
     * 
     * Ought to be private but made public for testing purpose.
     * 
     * @param p1
     * @param p2
     * @return    0   if p1 and p2 are equidistant to referencePoint
     * 			 -1   if p1 is closer to referencePoint 
     *            1   otherwise (i.e., if p2 is closer to referencePoint)
     */
    public int compareDistance(Point p1, Point p2)
    {
    	
    	   if(dotProduct(p1, p1) == dotProduct(p2, p2))		return 0;
    	   else if(dotProduct(p1, p1) < dotProduct(p2, p2)) return -1;
    	   else												return 1;
    	
    }
    

    /**
     * 
     * @param p1
     * @param p2
     * @return cross product of two vectors p1 - referencePoint and p2 - referencePoint
     */
    private int crossProduct(Point p1, Point p2)
    {
    	// TODO 
    	   int p1dx = p1.getX() - referencePoint.getX();
    	   int p1dy = p1.getY() - referencePoint.getY();
    	   int p2dx = p2.getX() - referencePoint.getX();
    	   int p2dy = p2.getY() - referencePoint.getY();

    	   return (p1dx * p2dy) - (p2dx * p1dy);  //formula for cross product
    }

    /**
     * 
     * @param p1
     * @param p2  
     * @return dot product of two vectors p1 - referencePoint and p2 - referencePoint
     */
    private int dotProduct(Point p1, Point p2)
    {
    	// TODO 
    	
    	
        //double delta = (p1.getX()*p1.getX() + p1.getY()*p1.getY());// - (p2.getX()*p2.getX() + p2.getY()*p2.getY());
    	int p1dx = p1.getX() - referencePoint.getX();
    	int p1dy = p1.getY() - referencePoint.getY();
    	int p2dx = p2.getX() - referencePoint.getX();
    	int p2dy = p2.getY() - referencePoint.getY();

    	return (p1dx * p2dx) + (p1dy * p2dy);  //formula for dot product
        
        
    	//int vectorDotProduct = (p1.getX()*p2.getX()) - (p1.getY()*p2.getY());
    	
    	//return vectorDotProduct; 
    }


}
