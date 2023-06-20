package edu.iastate.cs228.hw1;

import org.junit.Test; 
import static org.junit.Assert.assertEquals; 
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * 
 * @author Sullivan Fair
 *
 */

public class WildlifeTest 
{
	/**
	 * This tests the public3-10x10 test case that is shown in the Homework 1 pdf
	 */
	@Test
	public void TestUpdate() throws FileNotFoundException
	{
		Plain plain = new Plain("TestPlain.txt");
		Plain otherPlain = new Plain("TestPlain.txt");
		Plain finalPlain = new Plain("UpdateTest.txt");
		
		for(int i = 6; i > 0; i--)
		{
			if(i % 2 == 0)
			{
				Wildlife.updatePlain(plain, otherPlain);
			}
			else
			{
				Wildlife.updatePlain(otherPlain, plain);
			}
		}
		
		System.out.println(plain.toString());
		
		
		Scanner plainScan = new Scanner(plain.toString());
		Scanner finalScan = new Scanner(finalPlain.toString());
		
		while(plainScan.hasNextLine() && finalScan.hasNextLine())
		{
			String plainLine = plainScan.nextLine();
			String fileLine = finalScan.nextLine();
			
			assertEquals(0, plainLine.compareTo(fileLine));
		}
		
		plainScan.close();
		finalScan.close();
	}
}
