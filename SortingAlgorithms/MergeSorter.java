package edu.iastate.cs228.hw2;

/**
 *  
 * @author Sullivan Fair
 *
 */

//import java.io.FileNotFoundException;
//import java.lang.NumberFormatException; 
//import java.lang.IllegalArgumentException; 
//import java.util.InputMismatchException;

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts); 
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		 mergeSortRec(points);
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts point array 
	 */
	private void mergeSortRec(Point[] pts) 
	{
		int len = pts.length;
		if (len <= 1)
		{
			return;
		}
		
		int midPoint = len / 2;
		Point[] pointsLeft = new Point[midPoint];
		Point[] pointsRight = new Point[len - midPoint];
		
		for(int i = 0, j = 0; i < len; i++) 
		{
			if(i < midPoint) 
			{
				pointsLeft[i] = pts[i];
			}
			else 
			{
				pointsRight[j] = pts[i];
				j++;
			}
		}
		
		mergeSortRec(pointsLeft);
		mergeSortRec(pointsRight);
		
		merge(pointsLeft, pointsRight, pts);
	}
	
	/**
	 * Merges the left and right halves of pts() from mergeSortRec()
	 * 
	 * @param pts1 left half of array
	 * @param pts2 right half array
	 * @return sorted array
	 */
	private void merge(Point[] ptsLeft, Point[] ptsRight, Point[] pts)
	{
		int ptsLeftLen = pts.length / 2;
		int ptsRightLen = pts.length - ptsLeftLen;
		int leftInd = 0;
		int rightInd = 0;
		int sortInd = 0;
			
		while(leftInd < ptsLeftLen && rightInd < ptsRightLen)
		{
			if(ptsLeft[leftInd].compareTo(ptsRight[rightInd]) < 0)
			{
				pts[sortInd] = ptsLeft[leftInd];
				sortInd++;
				leftInd++;
			}
			else
			{
				pts[sortInd] = ptsRight[rightInd];
				sortInd++;
				rightInd++;
			}
		}
		
		while(leftInd < ptsLeftLen)
		{
				pts[sortInd] = ptsLeft[leftInd];
				leftInd++;
				sortInd++;
		}
		while(rightInd < ptsRightLen)
		{
			pts[sortInd] = ptsRight[rightInd];
			rightInd++;
			sortInd++;
		}
	}
}
