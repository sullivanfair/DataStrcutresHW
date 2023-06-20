package edu.iastate.cs228.hw1;

import org.junit.Test;  
import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertNotEquals;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 
 * @author Sullivan Fair
 *
 */

public class PlainTest 
{
	Plain plain;
	
	@Test
	public void TestConstructorFromFileANDtoString() throws FileNotFoundException
	{
		plain = new Plain("TestPlain.txt");
		File file = new File("TestPlain.txt");
		Scanner plainScan = new Scanner(plain.toString()); //Calls toString()
		Scanner fileScan = new Scanner(file);
		
		while(plainScan.hasNextLine() && fileScan.hasNextLine())
		{
			String plainLine = plainScan.nextLine();
			String fileLine = fileScan.nextLine();
			
			assertEquals(0, plainLine.compareTo(fileLine));
		}
		
		plainScan.close();
		fileScan.close();	
	}
	
	@Test
	public void TestNonIntializingConstructorANDgetWidth()
	{
		plain = new Plain(10);
		assertEquals(10, plain.getWidth());
	}
	
	@Test
	public void TestRandomInitialize()
	{
		plain = new Plain(10);
		plain.randomInit();
		
		for(int row = 0; row < plain.getWidth(); row++)
		{
			for(int col = 0; col < plain.getWidth(); col++)
			{
				assertNotEquals(null, plain.grid[row][col]);
			}
		}
	}
	
	@Test
	public void TestWrite() throws FileNotFoundException
	{
		plain = new Plain("TestPlain.txt");
		File file = new File("WriteTest.txt");
		PrintWriter writer = new PrintWriter(file);
		Scanner scanner = new Scanner(plain.toString());
		
		while(scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			writer.println(line);
		}
		
		Scanner plainScan = new Scanner(plain.toString());
		Scanner fileScan = new Scanner(file);
		
		while(plainScan.hasNextLine() && fileScan.hasNextLine())
		{
			String plainLine = plainScan.nextLine();
			String fileLine = fileScan.nextLine();
			
			assertEquals(0, plainLine.compareTo(fileLine));
		}
		
		scanner.close();
		writer.close();
		plainScan.close();
		fileScan.close();
	}
}
