package edu.iastate.cs228.hw2;

/**
 * 
 * @author Sullivan Fair
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	
	/**
	 * Median X-coord of from the first sort() call
	 */
	private int medianX;
		   			
	/**
	 * Median Y-coord of from the first sort() call
	 */
	private int medianY;
	
	private Algorithm sortingAlgorithm;    
		
	/**
	 * System time before sort()
	 */
	private long timeBefore;
	
	/**
	 * System time after sort()
	 */
	private long timeAfter;
	
	/**
	 * Time of first sort
	 */
	private long timeFirst;
	
	/**
	 * Time of second sort
	 */
	private long timeSecond;
	
	/**
	 * Amount of numbers in the file in the "from file" constructor
	 */
	private int numCount = 0;
	
	/**
	 * X-coord of a point from a file in the "from file" constructor
	 */
	private int xCoord;
	
	/**
	 * Y-coord of a point from a file in the "from file" constructor
	 */
	private int yCoord;
	
	/**
	 * Holds the proper index of points[] in the "from file" constrcutor
	 */
	private int index = 0;
	
	/**
	 * Used to read each line of the file in the "from file" constructor
	 */
	private String line;
	
	protected long scanTime; 	       // execution time in nanoseconds.
		
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if(pts == null || pts.length  == 0)
		{
			throw new IllegalArgumentException();
		}
		
		points = pts;
		sortingAlgorithm = algo;
	}

	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		File file = new File(inputFileName);
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNextLine())
		{
			line = scanner.nextLine();
			Scanner lineScan = new Scanner(line);
			
			while(lineScan.hasNextInt())
			{
				numCount++;
				lineScan.next();
			}
			
			lineScan.close();
		}
		
		scanner.close();
		
		if(numCount % 2 != 0)
		{
			throw new InputMismatchException();
		}
		else
		{
			points = new Point[numCount / 2];
		}
		
		scanner = new Scanner(file);
		
		while(scanner.hasNextLine())
		{
			line = scanner.nextLine();
			Scanner lineScan = new Scanner(line);
			
			while(lineScan.hasNextInt())
			{
				xCoord = lineScan.nextInt();
				
				if(!lineScan.hasNextInt())
				{
					line = scanner.nextLine();
					lineScan = new Scanner(line);
					yCoord = lineScan.nextInt();
				}
				else
				{
					yCoord = lineScan.nextInt();
				}
				
				points[index] = new Point(xCoord, yCoord);
				index++;
			}
			
			lineScan.close();
		}
		
		sortingAlgorithm = algo;
		scanner.close();
	}

	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{ 
		AbstractSorter aSorter = null;
		
		switch(sortingAlgorithm)
		{
			case SelectionSort:
				aSorter = new SelectionSorter(points);
				break;
			case InsertionSort:
				aSorter = new InsertionSorter(points);
				break;
			case MergeSort:
				aSorter = new MergeSorter(points);
				break;
			case QuickSort:
				aSorter = new QuickSorter(points);
				break;
		}
		
		/**
		 * Sort by x-coordinates
		 */
		aSorter.setComparator(0);
		Point.setXorY(true);
		
		timeBefore = System.nanoTime();
		aSorter.sort();
		timeAfter = System.nanoTime();
		
		timeFirst = timeAfter - timeBefore;
		medianX = aSorter.getMedian().getX();
		
		/**
		 * Sort by y-coordinates
		 */
		aSorter.setComparator(1);
		Point.setXorY(false);
		
		timeBefore = System.nanoTime();
		aSorter.sort();
		timeAfter = System.nanoTime();
		
		timeSecond = timeAfter - timeBefore;
		medianY = aSorter.getMedian().getY();
		
		scanTime = timeFirst + timeSecond;
		medianCoordinatePoint = new Point(medianX, medianY);
		
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0 or 1. 
		//
		//     b) call sort(). 		
		// 
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		//
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime. 
	}
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		switch(sortingAlgorithm)
		{
			case SelectionSort:
			{
				if(points.length < 10)
				{
					return sortingAlgorithm.toString() + "  " + points.length + "     " + scanTime;
				}
				else if(points.length >= 10 && points.length < 100)
				{
					return sortingAlgorithm.toString() + "  " + points.length + "    " + scanTime;
				}
				else if(points.length >= 100 && points.length < 1000)
				{
					return sortingAlgorithm.toString() + "  " + points.length + "   " + scanTime;
				}
				else if(points.length >= 1000 && points.length < 10000)
				{
					return sortingAlgorithm.toString() + "  " + points.length + "  " + scanTime;
				}
				else
				{
					return sortingAlgorithm.toString() + "  " + points.length + " " + scanTime;
				}
			}	
			case InsertionSort:
			{
				if(points.length < 10)
				{
					return sortingAlgorithm.toString() + "  " + points.length + "     " + scanTime;
				}
				else if(points.length >= 10 && points.length < 100)
				{
					return sortingAlgorithm.toString() + "  " + points.length + "    " + scanTime;
				}
				else if(points.length >= 100 && points.length < 1000)
				{
					return sortingAlgorithm.toString() + "  " + points.length + "   " + scanTime;
				}
				else if(points.length >= 1000 && points.length < 10000)
				{
					return sortingAlgorithm.toString() + "  " + points.length + "  " + scanTime;
				}
				else
				{
					return sortingAlgorithm.toString() + "  " + points.length + " " + scanTime;
				}
			}
			case MergeSort:
			{
				if(points.length < 10)
				{
					return sortingAlgorithm.toString() + "      " + points.length + "     " + scanTime;
				}
				else if(points.length >= 10 && points.length < 100)
				{
					return sortingAlgorithm.toString() + "      " + points.length + "    " + scanTime;
				}
				else if(points.length >= 100 && points.length < 1000)
				{
					return sortingAlgorithm.toString() + "      " + points.length + "   " + scanTime;
				}
				else if(points.length >= 1000 && points.length < 10000)
				{
					return sortingAlgorithm.toString() + "      " + points.length + "  " + scanTime;
				}
				else
				{
					return sortingAlgorithm.toString() + "      " + points.length + " " + scanTime;
				}
			}
			case QuickSort:
			{
				if(points.length < 10)
				{
					return sortingAlgorithm.toString() + "      " + points.length + "     " + scanTime;
				}
				else if(points.length >= 10 && points.length < 100)
				{
					return sortingAlgorithm.toString() + "      " + points.length + "    " + scanTime;
				}
				else if(points.length >= 100 && points.length < 1000)
				{
					return sortingAlgorithm.toString() + "      " + points.length + "   " + scanTime;
				}
				else if(points.length >= 1000 && points.length < 10000)
				{
					return sortingAlgorithm.toString() + "      " + points.length + "  " + scanTime;
				}
				else
				{
					return sortingAlgorithm.toString() + "      " + points.length + " " + scanTime;
				}
			}
		}
	
		return "";
	}
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: " + medianCoordinatePoint.toString(); 
	}

	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		PrintWriter writer = new PrintWriter("output.txt");
		writer.println(medianCoordinatePoint.toString());
		writer.close();
	}		
}
