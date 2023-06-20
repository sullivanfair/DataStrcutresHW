package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 *  
 * @author Sullivan Fair
 *
 */

/**
 * 
 * The Wildlife class performs a simulation of a grid plain with
 * squares inhabited by badgers, foxes, rabbits, grass, or none. 
 *
 */
public class Wildlife 
{
	/**
	 * Update the new plain from the old plain in one cycle. 
	 * @param pOld  old plain
	 * @param pNew  new plain 
	 */
	public static void updatePlain(Plain pOld, Plain pNew) throws FileNotFoundException
	{
		for(int row = 0; row < pOld.getWidth(); row++)
		{
			for(int col = 0; col < pOld.getWidth(); col++)
			{
				pNew.grid[row][col] = null;
				pNew.grid[row][col] = pOld.grid[row][col].next(pNew);
			}
		}
		
		// For every life form (i.e., a Living object) in the grid pOld, generate  
		// a Living object in the grid pNew at the corresponding location such that 
		// the former life form changes into the latter life form. 
		// 
		// Employ the method next() of the Living class. 
	}
	
	/**
	 * Repeatedly generates plains either randomly or from reading files. 
	 * Over each plain, carries out an input number of cycles of evolution. 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{	
		Plain even;   				 // the plain after an even number of cycles 
		Plain odd;                  // the plain after an odd number of cycles
		int numCycles;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Simulation of Wildlife of the Plain");
		System.out.print("keys: 1 (random plain) 2 (file input) 3 (exit) -- ");
		int key = scanner.nextInt();
		
		if(key == 1)
		{
			System.out.println("\nTrial: " + key);
			System.out.println("Random plain");
			
			System.out.print("Enter grid width: ");
			int width = scanner.nextInt();
			
			even = new Plain(width);
			even.randomInit();
			odd = new Plain(width);
			odd.randomInit();
			
			System.out.print("Enter the number of cycles: ");
			numCycles = scanner.nextInt();
			
			System.out.println("\nInitial plain: \n");
			if(numCycles % 2 == 0)
			{
				System.out.println(even.toString());
			}
			else
			{
				System.out.println(odd.toString());
			}
			
			for(int i = numCycles; i > 0; i--)
			{
				if(i % 2 == 0)
				{
					updatePlain(even, odd);
				}
				else
				{
					updatePlain(odd, even);
				}
			}
			
			System.out.println("Final plain: \n");
			System.out.println(even.toString());
		}
		else if(key == 2)
		{
			System.out.println("\nTrial: " + key);
			System.out.println("Plain input from a file");
			
			System.out.print("File name: ");
			String fileName = scanner.next();
			
			even = new Plain(fileName);
			odd = new Plain(fileName);
			
			System.out.print("Enter the number of cycles: ");
			numCycles = scanner.nextInt();
			
			System.out.println("\nInitial plain: \n");
			System.out.println(even.toString());
			
			for(int i = numCycles; i > 0; i--)
			{
				if(i % 2 == 0)
				{
					updatePlain(even, odd);
				}
				else
				{
					updatePlain(odd, even);
				}
			}
			
			System.out.println("Final plain: \n");
			System.out.println(even.toString());
		}
		else
		{
			scanner.close();
			return;
		}
		
		scanner.close();
		
		// Generate wildlife simulations repeatedly like shown in the 
		// sample run in the project description. 
		// 
		// 1. Enter 1 to generate a random plain, 2 to read a plain from an input
		//    file, and 3 to end the simulation. (An input file always ends with 
		//    the suffix .txt.)
		// 
		// 2. Print out standard messages as given in the project description. 
		// 
		// 3. For convenience, you may define two plains even and odd as below. 
		//    In an even numbered cycle (starting at zero), generate the plain 
		//    odd from the plain even; in an odd numbered cycle, generate even 
		//    from odd. 
		//
		// 4. Print out initial and final plains only.  No intermediate plains should
		//    appear in the standard output.  (When debugging your program, you can 
		//    print intermediate plains.)
		// 
		// 5. You may save some randomly generated plains as your own test cases. 
		// 
		// 6. It is not necessary to handle file input & output exceptions for this 
		//    project. Assume data in an input file to be correctly formated. 
	}
}
