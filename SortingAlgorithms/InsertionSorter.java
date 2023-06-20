package edu.iastate.cs228.hw2;

//import java.io.FileNotFoundException;
//import java.lang.NumberFormatException; 
//import java.lang.IllegalArgumentException; 
//import java.util.InputMismatchException;

/**
 *  
 * @author Sullivan Fair
 *
 */

/**
 * 
 * This class implements insertion sort.   
 *
 */
public class InsertionSorter extends AbstractSorter 
{
	/**
	 * Point to make reference to while sorting
	 */
	private Point refPoint;
	
	/**
	 * Used as a second index in the while loop of sort()
	 */
	private int j;
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 * 
	 * @param pts  
	 */
	public InsertionSorter(Point[] pts) 
	{
		super(pts); 
	}	

	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 */
	@Override 
	public void sort()
	{
		for(int i = 0; i < points.length; i++)
		{
			refPoint = points[i];
			j = i - 1;
			
			while(j >= 0 && points[j].compareTo(refPoint) > 0)
			{
				points[j + 1] = points[j];
				j--;
			}
			
			points[j + 1] = refPoint;
		}
	}		
}
