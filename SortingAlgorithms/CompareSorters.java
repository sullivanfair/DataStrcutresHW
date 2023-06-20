package edu.iastate.cs228.hw2;

/**
 *  
 * @author Sullivan Fair
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 

public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		PointScanner[] scanners = new PointScanner[4]; 
		int numTrials = 1;
		int key;
		int numPoints;
		Point[] points;
		String fileName;
		
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning\n");
		System.out.print("keys: 1 (random integers)  2 (file input)  3 (exit) ");
		
		while(true)
		{
			Scanner scanner = new Scanner(System.in);
			System.out.print("\nTrial " + numTrials + ": ");
			key = scanner.nextInt();
			
			if(key == 1)
			{
				System.out.print("Enter number of random points: ");
				numPoints = scanner.nextInt();
				points = generateRandomPoints(numPoints, new Random());
				
				for(int i = 0; i < 4; i++)
				{
					switch(i)
					{
						case 0:
							scanners[i] = new PointScanner(points, Algorithm.SelectionSort);
							break;
						case 1:
							scanners[i] = new PointScanner(points, Algorithm.InsertionSort);
							break;
						case 2:
							scanners[i] = new PointScanner(points, Algorithm.MergeSort);
							break;
						case 3:
							scanners[i] = new PointScanner(points, Algorithm.QuickSort);
							break;
					}
				}
				
				System.out.println("\nalgorithm      size  time (ns)");
				System.out.println("----------------------------------");
				
				for(int i = 0; i < 4; i++)
				{
					scanners[i].scan();
					System.out.println(scanners[i].stats());
				}
				
				System.out.println("----------------------------------");
			}
			else if(key == 2)
			{
				System.out.println("Points from a file");
				System.out.print("File name: ");
				fileName = scanner.next();
				
				for(int i = 0; i < 4; i++)
				{
					switch(i)
					{
						case 0:
							scanners[i] = new PointScanner(fileName, Algorithm.SelectionSort);
							break;
						case 1:
							scanners[i] = new PointScanner(fileName, Algorithm.InsertionSort);
							break;
						case 2:
							scanners[i] = new PointScanner(fileName, Algorithm.MergeSort);
							break;
						case 3:
							scanners[i] = new PointScanner(fileName, Algorithm.QuickSort);
							break;
					}
				}
				
				System.out.println("\nalgorithm      size  time (ns)");
				System.out.println("----------------------------------");
				
				for(int i = 0; i < 4; i++)
				{
					scanners[i].scan();
					System.out.println(scanners[i].stats());
				}
				
				System.out.println("----------------------------------");
			}
			else
			{
				return;
			}
			
			numTrials++;
		} 
	}
	
	/**
	 * This method generates a given number of random points.
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
		Point[] points = new Point[numPts];
		rand = new Random();
		
		for(int i = 0; i < numPts; i++)
		{
			Point p = new Point((rand.nextInt(101) - 50), (rand.nextInt(101) - 50));
			points[i] = p;
		}
		
		return points; 
	}
}
