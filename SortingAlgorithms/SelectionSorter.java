package edu.iastate.cs228.hw2;

//import java.io.FileNotFoundException;
//import java.lang.NumberFormatException; 
//import java.lang.IllegalArgumentException; 
//import java.util.InputMismatchException;
//import javax.imageio.plugins.tiff.ExifParentTIFFTagSet;

/**
 *  
 * @author Sullivan Fair
 *
 */

/**
 * 
 * This class implements selection sort.   
 *
 */
public class SelectionSorter extends AbstractSorter
{
	/**
	 * Index with smallest Point
	 */
	private int minIndex;
	
	/**
	 * Temporary Point that is used to swap minIndex
	 */
	private Point tempPoint;
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts); 
	}	
	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 */
	@Override 
	public void sort()
	{
		for(int i = 0; i < points.length - 1; i++)
		{
			minIndex = i;
			
			for(int j = i + 1; j < points.length; j++)
			{
				if(points[j].compareTo(points[minIndex]) < 0)
				{
					minIndex = j;
				}
			}
			
			tempPoint = points[minIndex];
			points[minIndex] = points[i];
			points[i] = tempPoint;
		}
	}	
}
