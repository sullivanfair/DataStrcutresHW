package edu.iastate.cs228.hw1;

/**
 *  
 * @author Sullivan Fair
 *
 */

import java.io.File; 
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner; 
import java.util.Random;

/**
 * 
 * The plain is represented as a square grid of size width x width. 
 *
 */
public class Plain 
{
	private int width; // grid size: width X width 
	
	public Living[][] grid;
	
	/**
	 * Used to parse each line of the text file in Plain(String inputFileName)
	 */
	private String line;
	
	/**
	 * Used to store row index in Plain(String inputFileName)
	 */
	private int rowCount;
	
	/**
	 * Used to store column index in Plain(String inputFileName)
	 */
	private int colCount;
	
	/**
	 * Variable for the return type String in toString()
	 */
	private String plain;
	
	/**
	 *  Default constructor reads from a file 
	 */
	public Plain(String inputFileName) throws FileNotFoundException
	{		
		File file = new File(inputFileName);
        Scanner scanner = new Scanner(file);
        
        while(scanner.hasNextLine())
        {
        	width++;
        	scanner.nextLine();
        }
        
        scanner.close();
        grid = new Living[width][width];
        scanner = new Scanner(file);
        
        rowCount = 0;
        
        while(scanner.hasNextLine())
        {
        	line = scanner.nextLine();
        	colCount = 0;
        	
        	for(int i = 0; i < line.length(); i++)
        	{
        		switch(line.charAt(i)) 
        		{
        			case 'B':
        				grid[rowCount][colCount] = new 
    					Badger(this, rowCount, colCount, Character.getNumericValue(line.charAt(i + 1)));
        				colCount++;
        				break;
        				
        			case 'E':
        				grid[rowCount][colCount] = new Empty(this, rowCount, colCount);
            			colCount++;
            			break;
            			
        			case 'F':
        				grid[rowCount][colCount] = new 
    					Fox(this, rowCount, colCount, Character.getNumericValue(line.charAt(i + 1)));
        				colCount++;
        				break;
        				
        			case 'G':
        				grid[rowCount][colCount] = new Grass(this, rowCount, colCount);
            			colCount++;
            			break;
            			
        			case 'R':
        				grid[rowCount][colCount] = new 
    					Rabbit(this, rowCount, colCount, Character.getNumericValue(line.charAt(i + 1)));
        				colCount++;
        				break;
        				
        		}
        	}
        	
        	rowCount++;
        }
        
        scanner.close();
        
		// Assumption: The input file is in correct format. 
		// 
		// You may create the grid plain in the following steps: 
		// 
		// 1) Reads the first line to determine the width of the grid.
		// 
		// 2) Creates a grid object. 
		// 
		// 3) Fills in the grid according to the input file. 
		// 
		// Be sure to close the input file when you are done. 
	}
	
	/**
	 * Constructor that builds a w x w grid without initializing it. 
	 * @param width the grid 
	 */
	public Plain(int w)
	{
		width = w;
		grid = new Living[width][width];
	}
	
	public int getWidth()
	{ 
		return width; 
	}
	
	/**
	 * Initialize the plain by randomly assigning to every square of the grid  
	 * one of BADGER, FOX, RABBIT, GRASS, or EMPTY.  
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit()
	{
		Random generator = new Random();
		int rand;
		 
		for(int row = 0; row < getWidth(); row++)
		{
			for(int col = 0; col < getWidth(); col++)
			{
				rand = generator.nextInt(5);
				switch(rand)
				{
					case 0:
						grid[row][col] = new Badger(this, row, col, 0);
						break;
						
					case 1:
						grid[row][col] = new Empty(this, row, col);
						break;
						
					case 2:
						grid[row][col] = new Fox(this, row, col, 0);
						break;
						
					case 3:
						grid[row][col] = new Grass(this, row, col);
						break;
						
					case 4:
						grid[row][col] = new Rabbit(this, row, col, 0);
						break;
				}
			}
		}
	}
	
	
	/**
	 * Output the plain grid. For each square, output the first letter of the living form
	 * occupying the square. If the living form is an animal, then output the age of the animal 
	 * followed by a blank space; otherwise, output two blanks.  
	 */
	public String toString()
	{
		plain = "";
		
		for(int row = 0; row < getWidth(); row++)
		{
			for(int col = 0; col < getWidth(); col++)
			{
				switch(grid[row][col].who())
				{
					case BADGER:
						plain += "B" + ((Animal) grid[row][col]).myAge() + " ";
						break;
						
					case EMPTY:
						plain += "E  ";
						break;
						
					case FOX:
						plain += "F" + ((Animal) grid[row][col]).myAge() + " ";
						break;
						
					case GRASS:
						plain += "G  ";
						break;
						
					case RABBIT:
						plain += "R" + ((Animal) grid[row][col]).myAge() + " " ;
						break;
				}
			}
			
			plain += "\n";
		}
		
		return plain; 
	}
	

	/**
	 * Write the plain grid to an output file.  Also useful for saving a randomly 
	 * generated plain for debugging purpose. 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException
	{
			File file = new File(outputFileName);
			PrintWriter writer = new PrintWriter(file);
			
			Scanner scanner = new Scanner(plain.toString());
			
			while(scanner.hasNextLine())
			{
				line = scanner.nextLine();
				writer.println(line);
			}
			
			writer.close();
			scanner.close();
		 
		// 1. Open the file. 
		// 
		// 2. Write to the file. The five life forms are represented by characters 
		//    B, E, F, G, R. Leave one blank space in between. Examples are given in
		//    the project description. 
		// 
		// 3. Close the file. 
	}			
}
